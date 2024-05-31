package com.nikitosii.studyrealtorapp.flow.profile.agents

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkLiveData
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.GetLocalAgentsUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.flow.base.BaseViewModel
import javax.inject.Inject


class ProfileAgentsViewModel @Inject constructor(
    getAgentsUseCase: GetLocalAgentsUseCase,
    private val updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase,
) : BaseViewModel() {

    val agents = getAgentsUseCase.execute().toWorkLiveData()

    fun updateAgentFavoriteStatus(agent: Agent) {
        val params = UpdateAgentFavoriteStatusUseCase.Params.create(agent.copy(favorite = !agent.favorite))
        ioToUnit { updateAgentFavoriteStatusUseCase.execute(params) }
    }
}