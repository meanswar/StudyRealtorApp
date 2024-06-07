package com.nikitosii.studyrealtorapp.flow.profile.agents

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.GetLocalAgentsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.RemoveAgentUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject


class ProfileAgentsViewModel @Inject constructor(
    getAgentsUseCase: GetLocalAgentsUseCase,
    private val removeAgentUseCase: RemoveAgentUseCase,
    private val updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase,
) : BaseViewModel() {

    val agentsNetwork = getAgentsUseCase.execute().toWorkLiveData()
    val agents = MutableLiveData<List<Agent>>()

    fun updateAgentFavoriteStatus(agent: Agent) {
        val list = agents.value?.toMutableList() ?: return
        list.map { if (it.id == agent.id) it.copy(favorite = !agent.favorite) else it }
        agents.postValue(list)
        val params = UpdateAgentFavoriteStatusUseCase.Params.create(agent.copy(favorite = !agent.favorite))
        ioToUnit { updateAgentFavoriteStatusUseCase.execute(params) }
    }

    fun removeAgent(id: String) = ioToUnit {
        val params = RemoveAgentUseCase.Params.create(id)
        removeAgentUseCase.execute(params)
    }
}