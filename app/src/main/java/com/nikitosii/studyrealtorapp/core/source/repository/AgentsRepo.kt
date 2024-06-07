package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.util.Flow

interface AgentsRepo {

    /**
     * Retrieves a list of agents based on the provided search criteria.
     *
     * @param data The [AgentRequestApi] model containing search criteria for agents.
     * @return A list of [Agent] matching the search criteria.
     */
    suspend fun getAgents(data: AgentRequestApi): List<Agent>

    /**
     * Retrieves a list of local agents based on the provided IDs.
     *
     * @param id A list of agent IDs to retrieve.
     * @return A list of [Agent] with the specified IDs from the local storage.
     */
    suspend fun getLocalAgents(id: List<String>): List<Agent>

    /**
     * Retrieves all local agents from the local storage.
     *
     * @return A flow of lists containing all local agents.
     */
    fun getLocalAgents(): Flow<List<Agent>>

    /**
     * Refreshes the list of agents in the local storage.
     */
    suspend fun refreshAgents()

    /**
     * Removes all data from the local storage.
     */
    suspend fun removeData()

    /**
     * Retrieves detailed information of an agent based on the provided ID.
     *
     * @param id The ID of the agent to retrieve details for.
     * @return The [AgentDetails] of the specified agent.
     */
    suspend fun getAgentDetails(id: String): AgentDetails

    /**
     * Retrieves a list of favorite agents from the local storage.
     *
     * @return A list of favorite [Agent].
     */
    suspend fun getFavoriteAgents(): List<Agent>

    /**
     * Retrieves a list of favorite agents from the local storage based on the provided IDs.
     *
     * @param ids A list of favorite agent IDs to retrieve.
     * @return A list of favorite [Agent] with the specified IDs.
     */
    suspend fun getFavoriteAgentsFromList(ids: List<String>): List<Agent>

    /**
     * Retrieves the most recent favorite agents from the local storage.
     *
     * @return A flow of lists containing the most recent favorite agents.
     */
    fun getRecentFavoriteAgents(): Flow<List<Agent>>

    /**
     * Refreshes the flow of recent favorite agents from the local storage.
     */
    suspend fun refreshRecentFavoriteAgents()

    /**
     * Updates an agent in the local storage.
     *
     * @param agent The [Agent] model to be updated.
     */
    suspend fun updateAgent(agent: Agent)

    /**
     * Saves a list of agents into the local storage.
     *
     * @param agents The list of [Agent] models to be saved.
     */
    suspend fun saveAgents(agents: List<Agent>)

    /**
     * Removes an agent from the local storage based on the provided ID.
     *
     * @param id The ID of the agent to be removed.
     */
    suspend fun remove(id: String)
}