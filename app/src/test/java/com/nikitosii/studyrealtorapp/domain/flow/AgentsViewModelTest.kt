package com.nikitosii.studyrealtorapp.domain.flow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetAgentsFromNetworkUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetRecentFavoriteAgentsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.flow.agent.homepage.AgentsViewModel
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.ProfileTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.util.TestConstants.EXCEPTION_WRONG_PARAMS
import com.nikitosii.studyrealtorapp.util.TestConstants.PAGE_INVALID
import com.nikitosii.studyrealtorapp.util.TestConstants.PAGE_VALID
import com.nikitosii.studyrealtorapp.util.ext.asUpToDate
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AgentsViewModelTest {
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    @Mock
    private lateinit var getRecentFavoriteAgentsUseCase: GetRecentFavoriteAgentsUseCase

    @Mock
    private lateinit var getProfileFlowUseCase: GetProfileFlowUseCase

    @Mock
    private lateinit var updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase

    @Mock
    private lateinit var getAgentsFromNetworkUseCase: GetAgentsFromNetworkUseCase

    private lateinit var viewModel: AgentsViewModel
    private lateinit var favoriteAgentsFlow: SharedFlow<com.nikitosii.studyrealtorapp.core.source.channel.Status<List<Agent>>>
    private lateinit var profileFlow: SharedFlow<com.nikitosii.studyrealtorapp.core.source.channel.Status<Profile>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        favoriteAgentsFlow =
            MutableSharedFlow<com.nikitosii.studyrealtorapp.core.source.channel.Status<List<Agent>>>(
                replay = 1,
                onBufferOverflow = BufferOverflow.DROP_LATEST,
                extraBufferCapacity = 1
            ).asSharedFlow()

        AgentTestUtils.getExpectedLocalAgents()
            .filter { it.favorite == TestConstants.BOOLEAN_VALID }
            .asUpToDate()
            .run { favoriteAgentsFlow = MutableStateFlow(this) }

        profileFlow =
            MutableSharedFlow<com.nikitosii.studyrealtorapp.core.source.channel.Status<Profile>>()
                .asSharedFlow()

        ProfileTestUtils.getLocalProfile()
            .asUpToDate()
            .run { profileFlow = MutableStateFlow(this) }

        `when`(getRecentFavoriteAgentsUseCase.execute()).thenReturn(
            favoriteAgentsFlow
        )

        `when`(getProfileFlowUseCase.execute()).thenReturn(
            profileFlow
        )
        viewModel = AgentsViewModel(
            getRecentFavoriteAgentsUseCase,
            getProfileFlowUseCase,
            updateAgentFavoriteStatusUseCase,
            getAgentsFromNetworkUseCase,
            Dispatchers.IO,
            testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `get agents from network`() = runTest {
        val request = AgentTestUtils.getAgentRequest()
        val params = GetAgentsFromNetworkUseCase.Params.from(request, PAGE_VALID)
        val expectedAgents = listOf(AgentTestUtils.getLocalAgent())
        val expectedStatus = Status.SUCCESS
        viewModel.setOldRequest(request)

        `when`(getAgentsFromNetworkUseCase.execute(params)).thenReturn(expectedAgents)

        val observer = Observer<WorkResult<List<Agent>>> {}
        try {
            viewModel.agentsNetwork.observeForever(observer)

            viewModel.getAgentsFromNetwork()

            verify(getAgentsFromNetworkUseCase).execute(params)
            assertEquals(expectedStatus, viewModel.agentsNetwork.value?.status)
            assertEquals(expectedAgents, viewModel.agentsNetwork.value?.data)
        } finally {
            viewModel.agentsNetwork.removeObserver(observer)
        }
    }

    @Test
    fun `update agent favorite status`() = runTest {
        val agent = AgentTestUtils.getLocalAgent()
        val updatedAgent = agent.copy(favorite = !agent.favorite)
        val params = UpdateAgentFavoriteStatusUseCase.Params.create(updatedAgent)

        `when`(updateAgentFavoriteStatusUseCase.execute(params)).thenReturn(Unit)

        viewModel.updateAgentFavoriteStatus(agent)

        verify(updateAgentFavoriteStatusUseCase).execute(params)
    }
}