package com.nikitosii.studyrealtorapp.core.source.repository.impl

import android.content.Context
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentRequestApi
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AgentDetailsResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentDetailsResponse
import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.source.repository.base.repoChannel
import com.nikitosii.studyrealtorapp.util.Flow
import com.nikitosii.studyrealtorapp.util.JsonReader
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class AgentsRepoImpl @Inject constructor(
    private val api: AgentsApi,
    private val dao: AgentDao,
    private val context: Context,
    networkErrorHandler: NetworkErrorHandler,
    io: CoroutineDispatcher,
    connectivityProvider: ConnectivityProvider,
    recreateObserver: ChannelRecreateObserver
) : BaseRepo(networkErrorHandler), AgentsRepo {

    private val channelAgents = repoChannel(io, connectivityProvider, recreateObserver) {
        storageConfig { get = { dao.getRecentFavoriteAgents().map { Agent.from(it) } } }
    }

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

    override suspend fun getAgentDetails(id: String): AgentDetails = runWithErrorHandler {
//        AgentDetails.from(api.getAgentDetails(id).agentDetails)
        val data = JsonReader.readJson<BaseAgentDetailsResponse>(context, R.raw.agent_details_json)
        AgentDetails.from(data!!.agentDetails)
    }

    override suspend fun getFavoriteAgents(): List<Agent> =
        dao.getFavoriteAgents().map { Agent.from(it) }

    override suspend fun getFavoriteAgentsFromList(ids: List<String>): List<Agent> =
        dao.getFavoriteAgentsListFromList(ids).map { Agent.from(it) }

    override fun getRecentFavoriteAgents(): Flow<List<Agent>> = channelAgents.value.flow

    override suspend fun refreshRecentFavoriteAgents() = channelAgents.value.refreshOnlyLocal()

    override suspend fun updateAgent(agent: Agent) {
        dao.insertAgent(AgentEntity.from(agent))
    }

    override suspend fun saveAgents(agents: List<Agent>) {
        dao.insertAgents(agents.map { AgentEntity.from(it) })
    }
}