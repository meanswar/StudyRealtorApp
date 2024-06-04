package com.nikitosii.studyrealtorapp.flow.profile.agents

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.cardview.widget.CardView
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.google.android.material.tabs.TabLayout
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.*
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.FragmentHistoryBinding
import com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter.AgentAdapter
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.profile.ProfileViewPagerFragmentDirections
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.addTabs
import com.nikitosii.studyrealtorapp.util.ext.callIntent
import com.nikitosii.studyrealtorapp.util.ext.emailIntent
import com.nikitosii.studyrealtorapp.util.ext.onTabClick
import com.nikitosii.studyrealtorapp.util.ext.show
import timber.log.Timber

@RequiresViewModel(ProfileAgentsViewModel::class)
class ProfileAgentsFragment : BaseFragment<FragmentHistoryBinding, ProfileAgentsViewModel>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
) {

    private val adapter = AgentAdapter { view, data -> onItemClick(view, data) }
    override fun initViews() {
        postponeEnterTransition()
        view?.doOnPreDraw { startPostponedEnterTransition() }

        with(binding) {
            rvContent.adapter = adapter
            tlSortingFilters.onTabClick({ onTabClick(it) }, { onTabReselectedClick() })
            tlSortingFilters.addTabs(TABS)
        }
    }

    private fun onTabClick(tab: TabLayout.Tab) {
        val agents = viewModel.agents.value ?: return
        viewModel.agents.postValue(when (tab.text.toString()) {
            TAB_NAME -> agents.sortedBy { it.name }
            TAB_RATING -> agents.sortedByDescending { it.reviewCount }
            TAB_PRICE -> agents.sortedBy { it.salePrice?.min }
            TAB_FAVORITE -> agents.sortedByDescending { it.favorite }
            else -> return
        })
    }

    private fun onTabReselectedClick() {
        val agents = viewModel.agents.value?.reversed() ?: return
        viewModel.agents.postValue(agents)
    }

    private val onItemClick: (view: View, agent: Agent) -> Unit = { view: View, agent: Agent ->
        when (view.id) {
            R.id.cvFavorite -> onFavorite(agent)
            R.id.cvEmail -> onEmail(agent)
            R.id.cvPhone -> onPhone(agent)
            R.id.cvAgentContent -> onAgentClick(agent, view)
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

    private fun onAgentClick(agent: Agent, view: View) {
        val extras = FragmentNavigatorExtras(view as CardView to "agent_details")
        ProfileViewPagerFragmentDirections.openAgentDetails(agent).navigate(extras)
    }



    override fun subscribe() {
        with(viewModel) {
            agentsNetwork.observe(viewLifecycleOwner, agentsObserver)
            agents.observe(viewLifecycleOwner) { onDataSet(it) }
        }
    }

    private val agentsObserver: Observer<WorkResult<Status<List<Agent>>>> = Observer {
        onLoading(it.status == LOADING)
        when (it.status) {
            SUCCESS -> viewModel.agents.postValue(it.data?.obj)
            ERROR -> handleException(it.exception)
            LOADING -> Timber.i("loading agents")
        }
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            lavLoading.show(isLoading)
            tlSortingFilters.show(!isLoading)
        }
    }

    private fun onDataSet(data: List<Agent>) {
        adapter.submitList(data)
        Handler(Looper.getMainLooper()).postDelayed({ binding.rvContent.scrollToPosition(0) }, 500)
    }

    companion object {
        const val SCREEN_TITLE = "Agents"
        private const val TAB_NAME = "Name"
        private const val TAB_RATING = "Rating"
        private const val TAB_PRICE = "Price"
        private const val TAB_FAVORITE = "Favorites"

        private val TABS = listOf(TAB_NAME, TAB_RATING, TAB_PRICE, TAB_FAVORITE)
    }
}