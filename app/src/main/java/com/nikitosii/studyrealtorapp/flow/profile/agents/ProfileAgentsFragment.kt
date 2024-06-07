package com.nikitosii.studyrealtorapp.flow.profile.agents

import android.view.View
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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
import com.nikitosii.studyrealtorapp.util.ext.callIntent
import com.nikitosii.studyrealtorapp.util.ext.emailIntent
import com.nikitosii.studyrealtorapp.util.ext.onItemSwipe
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
            rvContent.onItemSwipe(ItemTouchHelper.LEFT) {
                val list = adapter.currentList.toMutableList()
                val id = it.adapterPosition
                val removedAgent = adapter.currentList[id]
                list.removeAt(id)
                adapter.submitList(list)
                viewModel.removeAgent(removedAgent.id)
            }
        }
    }

    private val onItemClick: (View, Agent) -> Unit = { view, agent ->
        when (view.id) {
            R.id.lavFavorite -> onFavorite(agent)
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
            agents.observe(viewLifecycleOwner) { adapter.submitList(it) }
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
        }
    }

    companion object {
        const val SCREEN_TITLE = "Agents"
    }
}