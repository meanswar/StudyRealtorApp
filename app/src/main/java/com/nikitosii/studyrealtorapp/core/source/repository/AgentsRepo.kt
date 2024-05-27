package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.util.Flow

interface AgentsRepo {

    suspend fun getAgents(data: AgentRequestApi): List<Agent>

    suspend fun getLocalAgents(id: List<String>): List<Agent>

    suspend fun getLocalAgents(): List<Agent>

    suspend fun getAgentDetails(id: String): AgentDetails

    suspend fun getFavoriteAgents(): List<Agent>

    suspend fun getFavoriteAgentsFromList(ids: List<String>): List<Agent>

    fun getRecentFavoriteAgents(): Flow<List<Agent>>

    suspend fun refreshRecentFavoriteAgents()

    suspend fun updateAgent(agent: Agent)

    suspend fun saveAgents(agents: List<Agent>)
}