package com.nikitosii.studyrealtorapp.domain.repository

import android.annotation.SuppressLint
import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.AgentsRepoImpl
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.AgentsApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseAgentsSearch
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.BOOLEAN_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PAGE_VALID
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class AgentsRepoTest {

    private lateinit var repo: AgentsRepo

    @Mock
    private lateinit var dao: AgentDao

    @Mock
    private lateinit var api: AgentsApi

    @Mock
    private lateinit var networkErrorHandler: NetworkErrorHandler

    @Mock
    private lateinit var io: CoroutineDispatcher

    @Mock
    private lateinit var connectivityProvider: ConnectivityProvider

    @Mock
    private lateinit var recreateObserver: ChannelRecreateObserver

    @Before
    fun setup() {
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
    fun `get agents test`() {
        val request = AgentTestUtils.getAgentRequest()
        val mockedData = Mockito.mock(BaseAgentsSearch::class.java)
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
        } returns mockedData
        runBlocking { repo.getAgents(request, PAGE_VALID) }
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
}