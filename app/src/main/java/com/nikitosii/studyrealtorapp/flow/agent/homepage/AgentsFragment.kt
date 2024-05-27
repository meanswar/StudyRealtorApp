package com.nikitosii.studyrealtorapp.flow.agent.homepage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.FragmentHomePageAgentsBinding
import com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter.AgentAdapter
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.callIntent
import com.nikitosii.studyrealtorapp.util.ext.emailIntent
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.onChange
import com.nikitosii.studyrealtorapp.util.ext.onCheck
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.view.PulseLayout
import timber.log.Timber

@RequiresViewModel(AgentsViewModel::class)
class AgentsFragment : BaseFragment<FragmentHomePageAgentsBinding, AgentsViewModel>(
    { FragmentHomePageAgentsBinding.bind(it) }, R.layout.fragment_home_page_agents
) {

    private val agentsAdapter by lazy { AgentAdapter(onAgentClick) }

    private val onAgentClick: (view: View, agent: Agent) -> Unit = { view: View, agent: Agent ->
        when (view.id) {
            R.id.cvFavorite -> onFavorite(agent)
            R.id.cvEmail -> onEmail(agent)
            R.id.cvPhone -> onPhone(agent)
            R.id.clAgentContent -> onAgentClick(agent)
        }
    }

    private fun onFavorite(agent: Agent) {
        viewModel.updateAgentFavoriteStatus(agent)
    }

    private fun onEmail(agent: Agent) {
        emailIntent(agent.office?.email)
    }

    private fun onPhone(agent: Agent) {
        callIntent(agent.phone)
    }

    private fun onAgentClick(agent: Agent) {
        AgentsFragmentDirections.openAgentDetails(agent).navigate()
    }

    override fun initViews() {
        with(binding) {
            svSearch.initEndAnimation(clFilters)
            ivLanguage.initAnimation(lFilterAttributes.svLanguage)
            ivName.initAnimation(lFilterAttributes.svName)
            ivPrice.initAnimation(lFilterAttributes.rvRangePrice)
            ivRating.initAnimation(lFilterAttributes.clRating)
            ivPofilePhoto.initAnimation(lFilterAttributes.clRadioPhoto)
            plBtn.stopAnimation()

            rvAgents.adapter = agentsAdapter
            lFilterAttributes.grFilters.hide()
            plBtn.show()
            clFilters.hide()
        }
    }

    override fun subscribe() {
        with(viewModel) {
            favoriteAgents.observe(viewLifecycleOwner, favoriteAgentsObserver)
            agents.observe(viewLifecycleOwner, agentsFromNetworkObserver)
            isFilterFilled.observe(viewLifecycleOwner, isFilterFilledObserver)
            isNetworkRequesting.observe(viewLifecycleOwner, isNetworkRequestingObserver)
        }
    }

    private val favoriteAgentsObserver: Observer<WorkResult<Status<List<Agent>>>> = Observer {
        when (it.status) {
            ERROR -> handleException(it.exception) { openError() }
            LOADING -> Timber.i("loading agents")
            SUCCESS -> processAgents(it.data?.obj, !viewModel.isNetworkRequest())
        }
    }

    private val agentsFromNetworkObserver: Observer<WorkResult<List<Agent>>> = Observer {
        onLoading(it.status == LOADING)
        when (it.status) {
            ERROR -> handleException(it.exception) { openError() }
            LOADING -> Timber.i("loading agents")
            SUCCESS -> processAgents(it.data, viewModel.isNetworkRequest())
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private val isFilterFilledObserver: Observer<Boolean> = Observer {
        binding.plBtn.startAnimation()
        binding.btnSearch.background =
            context?.getDrawable(R.drawable.bg_radio_btn_active)
    }

    private val isNetworkRequestingObserver: Observer<Boolean> = Observer {
        with(binding) {
            grTopContent.show(!it)
            toolbar.show(!it)
        }
    }

    private fun processAgents(data: List<Agent>?, isNetworkRequest: Boolean) {
        with(binding) {
            if (isNetworkRequest) {
                agentsAdapter.submitList(data)
                rvAgents.notifyDataSetChanged()
            }
            grEmpty.show(data?.isEmpty() == true)
        }
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            grEmpty.hide()
            grTopContent.hide()
            lavLoading.show(isLoading)
            if (isLoading) plBtn.stopAnimation() else plBtn.startAnimation()
            btnSearch.isEnabled = !isLoading
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
            btnSearch.onClick { searchAgents() }
            svSearch.setOnTextChanged { viewModel.setLocationFilter(it) }
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
        viewModel.getAgentsFromNetwork()
    }
}