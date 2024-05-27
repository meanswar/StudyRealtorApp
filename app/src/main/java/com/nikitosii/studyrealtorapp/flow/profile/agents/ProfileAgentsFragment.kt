package com.nikitosii.studyrealtorapp.flow.profile.agents

import android.view.View
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.databinding.FragmentHistoryBinding
import com.nikitosii.studyrealtorapp.flow.agent.homepage.adapter.AgentAdapter
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.profile.ProfileViewPagerFragmentDirections
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.callIntent
import com.nikitosii.studyrealtorapp.util.ext.emailIntent
import com.nikitosii.studyrealtorapp.util.ext.show
import timber.log.Timber

@RequiresViewModel(ProfileAgentsViewModel::class)
class ProfileAgentsFragment : BaseFragment<FragmentHistoryBinding, ProfileAgentsViewModel>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
) {

    private val adapter = AgentAdapter { view, data -> onItemClick(view, data) }
    override fun initViews() {
        with(binding) {
            rvContent.adapter = adapter
        }

        viewModel.getLocalAgents()
    }

    private val onItemClick: (view: View, agent: Agent) -> Unit = { view: View, agent: Agent ->
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
        ProfileViewPagerFragmentDirections.openAgentDetails(agent).navigate()
    }

    override fun subscribe() {
        viewModel.agents.observe(viewLifecycleOwner, agentsObserver)
    }

    private val agentsObserver: Observer<WorkResult<List<Agent>>> = Observer {
        onLoading(it.status == Status.LOADING)
        when (it.status) {
            Status.SUCCESS -> adapter.submitList(it.data)
            Status.ERROR -> handleException(it.exception)
            Status.LOADING -> Timber.i("loading agents")
        }
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            lavLoading.show(isLoading)
        }
    }

    companion object {
        const val SCREEN_TITLE = "Agents"
    }
}