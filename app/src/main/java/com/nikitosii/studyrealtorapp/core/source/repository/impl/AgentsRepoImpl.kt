package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import javax.inject.Inject

class AgentsRepoImpl @Inject constructor(
    private val api: AgentsApi,
    private val dao: AgentDao,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), AgentsRepo {

    override suspend fun getAgents(data: AgentRequestApi): List<Agent> = runWithErrorHandler {
        val result = api.getAgents(
            data.location,
            data.agentName,
            data.rating,
            data.photo,
            data.lang,
            data.price,
            data.page
        ).agents
        result.map { Agent.from(it) }
    }

    override suspend fun getLocalAgents(id: List<String>): List<Agent> =
        dao.getLocalAgents(id).map { Agent.from(it) }

    override suspend fun getAgentDetails(id: String): Agent = runWithErrorHandler {
        Agent.from(api.getAgentDetails(id).agentDetails)
    }

    override suspend fun getFavoriteAgents(): List<Agent> =
        dao.getFavoriteAgents().map { Agent.from(it) }

    override suspend fun getFavoriteAgentsFromList(ids: List<String>): List<Agent> =
        dao.getFavoriteAgentsListFromList(ids).map { Agent.from(it) }

    override suspend fun getRecentFavoriteAgents(): List<Agent> =
        dao.getRecentFavoriteAgents().map { Agent.from(it) }


    override suspend fun updateAgent(agent: Agent) {
        dao.insertAgent(AgentEntity.from(agent))
    }

    override suspend fun saveAgents(agents: List<Agent>) {
        dao.insertAgents(agents.map { AgentEntity.from(it) })
    }
}