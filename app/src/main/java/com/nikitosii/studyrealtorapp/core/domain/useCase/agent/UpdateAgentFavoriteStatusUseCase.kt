package com.nikitosii.studyrealtorapp.core.domain.useCase.agent

import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import javax.inject.Inject

class UpdateAgentFavoriteStatusUseCase @Inject constructor(
    private val repo: AgentsRepo
) : UseCaseParams<Unit, UpdateAgentFavoriteStatusUseCase.Params>() {

    data class Params(val agent: Agent) {
        companion object {
            fun create(agent: Agent) = Params(agent)
        }
    }

    override suspend fun execute(data: Params) {
        repo.updateAgent(data.agent)
        repo.refreshAgents()
        repo.refreshRecentFavoriteAgents()
    }
}