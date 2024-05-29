package com.nikitosii.studyrealtorapp.core.source.useCase.agent

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class UpdateAgentFavoriteStatusUseCase @Inject constructor(
    private val repo: AgentsRepo
) : UseCaseParams<Unit, UpdateAgentFavoriteStatusUseCase.Params>() {

    class Params private constructor(val agent: Agent) {
        companion object {
            fun create(agent: Agent) = Params(agent)
        }
    }

    override suspend fun execute(data: Params) {
        repo.updateAgent(data.agent)
        repo.refreshRecentFavoriteAgents()
        repo.refreshAgents()
    }
}