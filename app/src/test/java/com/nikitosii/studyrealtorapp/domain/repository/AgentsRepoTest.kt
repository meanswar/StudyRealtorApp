package com.nikitosii.studyrealtorapp.domain.repository

import android.annotation.SuppressLint
import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.AgentsRepoImpl
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentDetailsResponse
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentsSearch
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.BOOLEAN_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PAGE_VALID
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AgentsRepoTest {

    private lateinit var repo: AgentsRepo

    private lateinit var dao: AgentDao
    private lateinit var api: AgentsApi
    private lateinit var networkErrorHandler: NetworkErrorHandler
    private lateinit var io: CoroutineDispatcher
    private lateinit var connectivityProvider: ConnectivityProvider
    private lateinit var recreateObserver: ChannelRecreateObserver

    @Before
    fun setup() {
        dao = mockk()
        api = mockk(relaxed = true)
        networkErrorHandler = NetworkErrorHandler(mockk(relaxed = true))
        io = mockk(relaxed = true)
        connectivityProvider = mockk(relaxed = true)
        recreateObserver = mockk(relaxed = true)

        repo = AgentsRepoImpl(
            api,
            dao,
            networkErrorHandler,
            io,
            connectivityProvider,
            recreateObserver
        )
    }

    @SuppressLint("CheckResult")
    @Test
    fun `get agents test`() = runTest {
        val request = AgentTestUtils.getAgentRequest()
        val data = mockk<BaseAgentsSearch>(relaxed = true)

        coEvery {
            api.getAgents(
                ANY_TEXT,
                ANY_TEXT,
                ANY_DIGITS,
                BOOLEAN_VALID,
                ANY_TEXT,
                ANY_TEXT,
                PAGE_VALID
            )
        } returns data
        repo.getAgents(request, PAGE_VALID)
        coVerify {
            api.getAgents(
                address = request.location,
                name = request.agentName,
                rating = request.rating,
                isPhotoSet = request.photo,
                language = request.lang,
                price = request.price,
                page = PAGE_VALID
            )
        }
    }
// TODO find a way to make tests for flow
//    @SuppressLint("CheckResult")
//    @Test
//    fun `get local agents test`() = runTest {
//        val expected = AgentTestUtils.getExpectedAgentsList()
//
//        coEvery { dao.getLocalAgents() } returns expected
//        val actual = repo.getLocalAgents()
//
//        actual.collectLatest { assertEquals(expected, it.obj) }
//        coVerify { dao.getLocalAgents() }
//    }

    @Test
    fun `remove agents test`() = runTest {
        coEvery { dao.deleteAllAgents() } just Runs
        repo.removeData()

        coVerify { dao.deleteAllAgents() }
    }

    @Test
    fun `get agent details test`() = runTest {
        coEvery { api.getAgentDetails(any()) } returns mockk<BaseAgentDetailsResponse>(relaxed = true)
        repo.getAgentDetails(ANY_TEXT)

        coVerify { api.getAgentDetails(ANY_TEXT) }
    }

    @Test
    fun `get favorite agents test`() = runTest {
        val expected = mockk<List<AgentEntity>>(relaxed = true)

        coEvery { dao.getFavoriteAgents() } returns expected
        repo.getFavoriteAgents()

        coVerify { dao.getFavoriteAgents() }
    }

    @Test
    fun `get favorite agents from list test`() = runTest {
        val expected = mockk<List<AgentEntity>>(relaxed = true)
        val requestData = listOf(ANY_TEXT)

        coEvery { dao.getFavoriteAgentsListFromList(any()) } returns expected
        repo.getFavoriteAgentsFromList(requestData)

        coVerify { dao.getFavoriteAgentsListFromList(requestData) }
    }

    @Test
    fun `update agent test`() = runTest {
        val requestData = AgentTestUtils.getAgent()

        coEvery { dao.insertAgent(requestData) } just Runs
        repo.updateAgent(Agent.from(requestData))

        coVerify { dao.insertAgent(requestData) }
    }

    @Test
    fun `save agents test`() = runTest {
        val requestData = AgentTestUtils.getExpectedAgentsList()

        coEvery { dao.insertAgents(requestData) } just Runs
        repo.saveAgents(requestData.map { Agent.from(it) })

        coVerify { dao.insertAgents(requestData) }
    }

    @Test
    fun `remove agent test`() = runTest {
        coEvery { dao.deleteAgent(any()) } just Runs
        repo.remove(ANY_TEXT)

        coVerify { dao.deleteAgent(ANY_TEXT) }
    }
}