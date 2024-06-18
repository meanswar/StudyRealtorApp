package com.nikitosii.studyrealtorapp.core.domain.repository.impl

import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.BaseRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.domain.repository.base.repoChannel
import com.nikitosii.studyrealtorapp.util.Flow
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class AgentsRepoImpl @Inject constructor(
    private val api: AgentsApi,
    private val dao: AgentDao,
    networkErrorHandler: NetworkErrorHandler,
    io: CoroutineDispatcher,
    connectivityProvider: ConnectivityProvider,
    recreateObserver: ChannelRecreateObserver
) : BaseRepo(networkErrorHandler), AgentsRepo {

    private val channelFavoriteAgents = repoChannel(io, connectivityProvider, recreateObserver) {
        storageConfig { get = { dao.getRecentFavoriteAgents().map { Agent.from(it) } } }
    }

    private val channelAgents = repoChannel(io, connectivityProvider, recreateObserver) {
        storageConfig { get = { dao.getLocalAgents().map { Agent.from(it) }.sortedBy { it.name } } }
    }

    override suspend fun getAgents(data: AgentRequestApi, page: Int): List<Agent> = runWithErrorHandler {
        val result = api.getAgents(
            data.location,
            data.agentName,
            data.rating,
            data.photo,
            data.lang,
            data.price,
            page
        ).agents
        result.map { Agent.from(it) }
    }

    override suspend fun getLocalAgents(id: List<String>): List<Agent> =
        dao.getLocalAgents(id).map { Agent.from(it) }.sortedBy { it.name }

    override fun getLocalAgents(): Flow<List<Agent>> = channelAgents.value.flow

    override suspend fun refreshAgents() {
        channelAgents.value.refresh()
    }

    override suspend fun removeData() {
        dao.deleteAllAgents()
    }

    override suspend fun getAgentDetails(id: String): AgentDetails = runWithErrorHandler {
        AgentDetails.from(api.getAgentDetails(id).agentDetails)
    }

    override suspend fun getFavoriteAgents(): List<Agent> =
        dao.getFavoriteAgents().map { Agent.from(it) }

    override suspend fun getFavoriteAgentsFromList(ids: List<String>): List<Agent> =
        dao.getFavoriteAgentsListFromList(ids).map { Agent.from(it) }

    override fun getRecentFavoriteAgents(): Flow<List<Agent>> = channelFavoriteAgents.value.flow

    override suspend fun refreshRecentFavoriteAgents() =
        channelFavoriteAgents.value.refreshOnlyLocal()

    override suspend fun updateAgent(agent: Agent) = dao.insertAgent(AgentEntity.from(agent))


    override suspend fun saveAgents(agents: List<Agent>) =
        dao.insertAgents(agents.map { AgentEntity.from(it) })


    override suspend fun remove(id: String) = dao.deleteAgent(id)
}