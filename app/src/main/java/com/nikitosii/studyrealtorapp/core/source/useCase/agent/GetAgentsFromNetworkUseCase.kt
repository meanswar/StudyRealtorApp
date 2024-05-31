package com.nikitosii.studyrealtorapp.core.source.useCase.agent

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetAgentsFromNetworkUseCase @Inject constructor(
    private val agentsRepo: AgentsRepo,

) : UseCaseParams<List<Agent>, GetAgentsFromNetworkUseCase.Params>() {

    class Params private constructor(val params: AgentRequestApi) {
        companion object {
            fun from(params: AgentRequestApi) = Params(params)
        }
    }

    override suspend fun execute(data: Params): List<Agent> {
        val networkAgents = agentsRepo.getAgents(data.params)
        val localFavoriteAgentIds = agentsRepo
            .getFavoriteAgentsFromList(networkAgents.map { it.id })
            .map { it.id }
        val updatedNetworkAgents = networkAgents.map { it.copy(favorite = localFavoriteAgentIds.contains(it.id)) }
        agentsRepo.saveAgents(updatedNetworkAgents)
        return updatedNetworkAgents
    }
}