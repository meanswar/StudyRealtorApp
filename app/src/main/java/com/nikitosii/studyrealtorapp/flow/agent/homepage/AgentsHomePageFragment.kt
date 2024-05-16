package com.nikitosii.studyrealtorapp.flow.agent.homepage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
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
import com.nikitosii.studyrealtorapp.view.PulseLayout
import timber.log.Timber

@RequiresViewModel(AgentsHomePageViewModel::class)
class AgentsHomePageFragment : BaseFragment<FragmentHomePageAgentsBinding, AgentsHomePageViewModel>(
    { FragmentHomePageAgentsBinding.bind(it) }, R.layout.fragment_home_page_agents
) {

    val adapter by lazy { AgentAdapter(onAgentClick) }

    private val onAgentClick: (view: View, agent: Agent) -> Unit = { view: View, agent: Agent ->
        when (view.id) {
            R.id.cvFavorite -> onFavorite(agent)
            R.id.cvEmail -> onEmail(agent)
            R.id.cvPhone -> onPhone(agent)
        }
    }

    private fun onFavorite(agent: Agent) {
        viewModel.updateLocalAgent(agent)
    }

    private fun onEmail(agent: Agent) {
        emailIntent(agent.office?.email)
    }

    private fun onPhone(agent: Agent) {
        callIntent(agent.phone)
    }

    override fun initViews() {
        viewModel.getFavoriteAgents()

        with(binding) {
            svSearch.initEndAnimation(clFilters)
            ivLanguage.initAnimation(lFilterAttributes.svLanguage)
            ivName.initAnimation(lFilterAttributes.svName)
            ivPrice.initAnimation(lFilterAttributes.rvRangePrice)
            ivRating.initAnimation(lFilterAttributes.clRating)
            ivPofilePhoto.initAnimation(lFilterAttributes.clRadioPhoto)
            plBtn.stopAnimation()
            toolbar.initEndBtnAnimation(clFilters)

            rvAgents.adapter = adapter
            grTopContent.show(viewModel.isLocalAgents.value == true)
            toolbar.show(viewModel.isLocalAgents.value == false)
            lFilterAttributes.grFilters.hide()
            clFilters.hide()
        }
    }

    override fun subscribe() {
        with(viewModel) {
            agents.observe(viewLifecycleOwner, agentsObserver)
            isFilterFilled.observe(viewLifecycleOwner, isFilterFilledObserver)
        }
    }

    private val agentsObserver: Observer<WorkResult<List<Agent>>> = Observer {
        onLoading(it.status == Status.LOADING)
        when (it.status) {
            Status.ERROR -> handleException(it.exception) { openError() }
            Status.LOADING -> Timber.i("loading agents")
            Status.SUCCESS -> processAgents(it.data)
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private val isFilterFilledObserver: Observer<Boolean> = Observer {
        binding.plBtn.startAnimation()
        binding.btnSearch.background = context?.getDrawable(R.drawable.bg_radio_btn_active)
    }

    private fun processAgents(data: List<Agent>?) {
        adapter.submitList(data)
        binding.rvAgents.notifyDataSetChanged()
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            if (viewModel.isLocalAgents.value == false) {
                lavLoading.show(isLoading)
            }
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
            svSearch.setOnTextChanged {
                viewModel.setLocationFilter(it)
                binding.toolbar.setSearchText(it)
            }
            toolbar.showEndButton()
            toolbar.onSearchClick { toolbar.showSearchContent() }
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
        with(binding) {
            grTopContent.hide()
            toolbar.show()
            viewModel.getAgents()
        }
    }
}