package com.nikitosii.studyrealtorapp.domain.flow

import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetLocalAgentsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.RemoveAgentUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.flow.profile.agents.ProfileAgentsViewModel
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.THREAD_SLEEP_TIME
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ProfileAgentsViewModelTest : BaseViewModelTest<ProfileAgentsViewModel>() {

    @Mock
    private lateinit var getLocalAgentsUseCase: GetLocalAgentsUseCase

    @Mock
    private lateinit var removeAgentUseCase: RemoveAgentUseCase

    @Mock
    private lateinit var updateAgentFavoriteStatusUseCase: UpdateAgentFavoriteStatusUseCase

    private val agentsFlow = MutableSharedFlow<Status<List<Agent>>>()

    lateinit var observer: Observer<List<Agent>>

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(getLocalAgentsUseCase.execute()).thenReturn(agentsFlow)

        viewModel = ProfileAgentsViewModel(
            getLocalAgentsUseCase,
            removeAgentUseCase,
            updateAgentFavoriteStatusUseCase,
            Dispatchers.IO,
            testDispatcher
        )

        observer = Observer {}
        viewModel.agents.postValue(AgentTestUtils.getExpectedLocalAgents())
        viewModel.agents.observeForever(observer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        viewModel.agents.removeObserver(observer)
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `remove agent calls`() = runTest {
        val expected = AgentTestUtils.getLocalAgent()
        val params = RemoveAgentUseCase.Params.create(expected.id)

        `when`(removeAgentUseCase.execute(params)).thenReturn(Unit)

        viewModel.removeAgent(expected.id)

        verify(removeAgentUseCase).execute(params)
    }

    // TODO (check what is wrong with this code, problem with parallel work of coroutines)
    @Test
    fun `update agent favorite status`() = runTest {
        val agent = AgentTestUtils.getLocalAgent()
        val updatedAgent = agent.copy(favorite = !agent.favorite)
        val params = UpdateAgentFavoriteStatusUseCase.Params.create(updatedAgent)

        `when`(updateAgentFavoriteStatusUseCase.execute(params)).thenReturn(Unit)

        assertTrue(viewModel.agents.value?.first { it.id == agent.id }?.favorite == agent.favorite)

        viewModel.updateAgentFavoriteStatus(agent)
        Thread.sleep(THREAD_SLEEP_TIME)
        assertTrue(viewModel.agents.value?.first { it.id == agent.id }?.favorite == updatedAgent.favorite)
        verify(updateAgentFavoriteStatusUseCase).execute(params)
    }
}