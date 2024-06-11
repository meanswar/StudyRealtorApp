package com.nikitosii.studyrealtorapp.flow.agent.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.GetAgentDetailsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject

class AgentDetailsViewModel @Inject constructor(
    private val getAgentDetailsUseCase: GetAgentDetailsUseCase,
    private val updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase,
) : BaseViewModel() {

    private val _agentDetails = WorkLiveData<AgentDetails>()
    val agent = MutableLiveData<Agent>()
    val agentDetails: LiveData<WorkResult<AgentDetails>>
        get() = _agentDetails

    fun getAgentDetails(id: String) {
        val params = GetAgentDetailsUseCase.Params.create(id)
        ioToUiWorkData(
            io = { getAgentDetailsUseCase.execute(params) },
            ui = { _agentDetails.postValue(it) }
        )
    }

    fun updateAgentFavoriteStatus(agent: Agent) {
        this.agent.postValue(agent)
        val params = UpdateAgentFavoriteStatusUseCase.Params.create(agent)
        ioToUnit { updateAgentFavoriteStatusUseCase.execute(params) }
    }
}