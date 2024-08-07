package com.nikitosii.studyrealtorapp.flow.agent.homepage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.cardview.widget.CardView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout.Tab
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.databinding.FragmentHomePageAgentsBinding
import com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter.AgentAdapter
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.callIntent
import com.nikitosii.studyrealtorapp.util.ext.emailIntent
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.onChange
import com.nikitosii.studyrealtorapp.util.ext.onCheck
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onTabClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import com.nikitosii.studyrealtorapp.util.view.PulseLayout
import com.nikitosii.studyrealtorapp.util.view.recyclerview.PaginatingScrollListener
import timber.log.Timber

@RequiresViewModel(AgentsViewModel::class)
class AgentsFragment : BaseFragment<FragmentHomePageAgentsBinding, AgentsViewModel>(
    { FragmentHomePageAgentsBinding.bind(it) }, R.layout.fragment_home_page_agents
) {

    private val agentsAdapter by lazy { AgentAdapter(onAgentClick) }
    private val isFirstItemsLoading = MutableLiveData(true)
    private val isLoadingData = MutableLiveData(true)

    private val onAgentClick: (view: View, agent: Agent) -> Unit = { view, agent ->
        when (view.id) {
            R.id.lavFavorite -> viewModel.updateAgentFavoriteStatus(agent)
            R.id.cvEmail -> emailIntent(agent.office?.email)
            R.id.cvPhone -> callIntent(agent.phone)
            R.id.cvAgentContent -> openAgentDetails(agent, view as CardView)
        }
    }

    override fun initViews() {
        initRecyclerViewSettings()
        with(binding) {
            svSearch.initEndAnimation(cvFilterButtons)
            toolbar.initEndBtnAnimation(clFilters)

            with(lFilterAttributes) {
                ivLanguage.initAnimation(svLanguage)
                ivName.initAnimation(svName)
                ivPrice.initAnimation(rvRangePrice)
                ivRating.initAnimation(clRating)
                ivPofilePhoto.initAnimation(clRadioPhoto)
                plBtn.stopAnimation()
            }

            clTopContent.showWithAnimation(R.anim.slide_in_anim_bottom)
            clBottomContent.showWithAnimation(R.anim.slide_in_anim_top)
        }
    }

    private fun initRecyclerViewSettings() {
        with(binding) {
            rvAgents.adapter = agentsAdapter
            rvAgents.addOnScrollListener(object :
                PaginatingScrollListener(rvAgents.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() = viewModel.getAgentsFromNetwork()

                override fun isLoading(): Boolean = isLoadingData.value == true

                override fun isLastPage(): Boolean = viewModel.isEmptyResponse()
            })
        }
    }

    override fun subscribe() {
        with(viewModel) {
            favoriteAgents.observe(viewLifecycleOwner, favoriteAgentsObserver)
            agentsNetwork.observe(viewLifecycleOwner, agentsFromNetworkObserver)
            agents.observe(viewLifecycleOwner) { processAgents(it) }
            isFilterFilled.observe(viewLifecycleOwner, isFilterFilledObserver)
            isNetworkRequesting.observe(viewLifecycleOwner, isNetworkRequestingObserver)
            profile.observe(viewLifecycleOwner, profileObserver)
        }
    }

    private val favoriteAgentsObserver: Observer<WorkResult<Status<List<Agent>>>> = Observer {
        when (it.status) {
            ERROR -> handleException(it.exception) { openError() }
            LOADING -> Timber.i("loading agents")
            SUCCESS -> viewModel.agents.postValue(it.data?.obj)
        }
    }

    private val agentsFromNetworkObserver: Observer<WorkResult<List<Agent>>> = Observer {
        onLoading(it.status == LOADING)
        when (it.status) {
            ERROR -> handleException(it.exception) { openError() }
            LOADING -> Timber.i("loading agents")
            SUCCESS -> setAgentsData(it.data)
        }
    }

    private fun setAgentsData(data: List<Agent>?) {
        if (data?.isEmpty() == true) viewModel.setIsEmptyResponse(true)
        else viewModel.incrementPage()

        val newData = viewModel.agents.value?.toMutableList()?.apply { addAll(data ?: emptyList()) }
        viewModel.agents.postValue(newData)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private val isFilterFilledObserver: Observer<Boolean> = Observer {
        binding.lFilterAttributes.plBtn.startAnimation()
        binding.lFilterAttributes.btnSearch.background =
            context?.getDrawable(R.drawable.bg_radio_btn_active)
    }

    private val isNetworkRequestingObserver: Observer<Boolean> = Observer {
        with(binding) {
            grTopContent.show(!it)
            clFilters.show(!it)
            toolbar.show(it)
        }
    }

    private val profileObserver: Observer<WorkResult<Status<Profile>>> =
        Observer {
            when (it.status) {
                SUCCESS -> setProfileData(it.data?.obj)
                ERROR -> handleException(it.exception) { openError() }
                LOADING -> Timber.i("loading rent requests")
            }
        }

    private fun setProfileData(data: Profile?) {
        glideImage(data?.photo, binding.ivProfile, R.drawable.ic_user_profile)
    }

    private fun processAgents(data: List<Agent>?) {
        with(binding) {
            val newList = mutableListOf<Agent>().apply {
                addAll(agentsAdapter.currentList)
                addAll(data ?: emptyList())
            }
            agentsAdapter.submitList(newList)
            if (isFirstItemsLoading.value == true) {
                rvAgents.notifyDataSetChanged()
                isFirstItemsLoading.value = false
            }
            grEmpty.show(data?.isEmpty() == true)
        }
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            isLoadingData.postValue(isLoading)
            grEmpty.hide()
            grTopContent.hide()
            lavLoading.show(isLoading)
            lFilterAttributes.plBtn.run { if (isLoading) stopAnimation() else startAnimation() }
            lFilterAttributes.btnSearch.isEnabled = !isLoading
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        with(binding.lFilterAttributes) {
            svName.setOnTextChanged { viewModel.setNameFilter(it) }
            svLanguage.setOnTextChanged { viewModel.setLangFilter(it) }
            rvRangePrice.onRangeChanged { i1, i2 -> onPriceRangeChanged(i1, i2) }
            rsRating.onChange { _, value -> onRatingRangeChanged(value) }
            btnNo.onCheck { onCheckCallback(it, btnYes, plNo, plYes, false) }
            btnYes.onCheck { onCheckCallback(it, btnNo, plYes, plNo, true) }
        }

        with(binding) {
            lFilterAttributes.btnSearch.onClick { searchAgents() }
            svSearch.setOnTextChanged { onLocationTextChanged(it) }
        }
    }

    private fun onLocationTextChanged(text: String) {
        with(binding) {
            viewModel.setLocationFilter(text)
            tvLocationError.show(text.isEmpty())
        }
    }

    private fun onRatingRangeChanged(value: Float) {
        binding.lFilterAttributes.tvRatingResult.text = value.toString()
        viewModel.setRatingFilter(value.toInt())
    }

    private fun onPriceRangeChanged(valueMin: Int, valueMax: Int) {
        viewModel.setPriceFilter("${valueMin}_$valueMax")
    }

    private fun onCheckCallback(
        isChecked: Boolean,
        scndBtn: RadioButton,
        pulseChecked: PulseLayout,
        pulseNotChecked: PulseLayout,
        isActive: Boolean
    ) {
        if (isChecked) {
            pulseChecked.startAnimation()
            pulseNotChecked.stopAnimation()
            scndBtn.isChecked = false
            viewModel.setPhotoFilter(isActive)
        }
    }

    private fun searchAgents() {
        isFirstItemsLoading.value = false
        agentsAdapter.submitList(listOf())
        if (viewModel.isLocationFilterSet())
            viewModel.getAgentsFromNetwork()
    }

    private fun openAgentDetails(data: Agent, view: CardView) {
        val extra = FragmentNavigatorExtras(view to "agent_details")
        AgentsFragmentDirections.openAgentDetails(data).navigate(extra)
    }
}