package com.nikitosii.studyrealtorapp.flow.profile.agents

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetLocalAgentsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.RemoveAgentUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.di.modules.AppModule
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Named


class ProfileAgentsViewModel @Inject constructor(
    getAgentsUseCase: GetLocalAgentsUseCase,
    private val removeAgentUseCase: RemoveAgentUseCase,
    private val updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase,
    @Named(AppModule.IO_DISPATCHER) ioDispatcher: CoroutineDispatcher,
    @Named(AppModule.MAIN_DISPATCHER) uiDispatcher: CoroutineDispatcher
) : BaseViewModel(ioDispatcher, uiDispatcher) {

    val agentsNetwork = getAgentsUseCase.execute().toWorkLiveData()
    val agents = MutableLiveData<List<Agent>>()

    fun updateAgentFavoriteStatus(agent: Agent) {
        val updatedAgent = agent.copy(favorite = !agent.favorite)
        val list = agents.value?.toMutableList() ?: return
        list.replaceAll { if (it == agent) updatedAgent else it }
        agents.postValue(list)
        val params =
            UpdateAgentFavoriteStatusUseCase.Params.create(updatedAgent)
        ioToUnit { updateAgentFavoriteStatusUseCase.execute(params) }
    }

    fun removeAgent(id: String) = ioToUnit {
        val params = RemoveAgentUseCase.Params.create(id)
        removeAgentUseCase.execute(params)
    }
}