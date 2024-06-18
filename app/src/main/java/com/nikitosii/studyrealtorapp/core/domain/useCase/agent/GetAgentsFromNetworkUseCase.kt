package com.nikitosii.studyrealtorapp.core.domain.useCase.agent

import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import javax.inject.Inject

class GetAgentsFromNetworkUseCase @Inject constructor(
    private val agentsRepo: AgentsRepo,

    ) : UseCaseParams<List<Agent>, GetAgentsFromNetworkUseCase.Params>() {

    class Params private constructor(val params: AgentRequestApi, val page: Int) {
        companion object {
            fun from(params: AgentRequestApi, page: Int) = Params(params, page)
        }
    }

    override suspend fun execute(data: Params): List<Agent> {
        val networkAgents = agentsRepo.getAgents(data.params, data.page)
        val localFavoriteAgentIds = agentsRepo
            .getFavoriteAgentsFromList(networkAgents.map { it.id })
            .map { it.id }
        val updatedNetworkAgents = networkAgents.map { it.copy(favorite = localFavoriteAgentIds.contains(it.id)) }
        agentsRepo.saveAgents(updatedNetworkAgents)
        return updatedNetworkAgents
    }
}