package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi

interface AgentsRepo {

    suspend fun getAgents(data: AgentRequestApi): List<Agent>

    suspend fun getLocalAgents(id: List<String>): List<Agent>

    suspend fun getAgentDetails(id: String): Agent

    suspend fun getFavoriteAgents(): List<Agent>

    suspend fun getFavoriteAgentsFromList(ids: List<String>): List<Agent>

    suspend fun getRecentFavoriteAgents(): List<Agent>

    suspend fun updateAgent(agent: Agent)

    suspend fun saveAgents(agents: List<Agent>)
}