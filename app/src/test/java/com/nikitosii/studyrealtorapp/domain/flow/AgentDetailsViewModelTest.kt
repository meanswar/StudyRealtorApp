package com.nikitosii.studyrealtorapp.domain.flow

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetAgentDetailsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.flow.agent.details.AgentDetailsViewModel
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class AgentDetailsViewModelTest : BaseViewModelTest<AgentDetailsViewModel>() {

    @Mock
    private lateinit var getAgentDetailsUseCase: GetAgentDetailsUseCase

    @Mock
    private lateinit var updateAgentStatusUseCase: UpdateAgentFavoriteStatusUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        viewModel = AgentDetailsViewModel(
            getAgentDetailsUseCase,
            updateAgentStatusUseCase,
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
    fun `get agent details live data valid`() = runBlockingTest {
        val params = GetAgentDetailsUseCase.Params.create(TestConstants.ID_VALID_TEXT)
        val expected = AgentTestUtils.getLocalAgentDetails()
        val expectedStatus = Status.SUCCESS
        val observer = Observer<WorkResult<AgentDetails>> {}

        `when`(getAgentDetailsUseCase.execute(params)).thenReturn(expected)

        viewModel.getAgentDetails(params.id)

        viewModel.agentDetails.observeForever(observer)

        verify(getAgentDetailsUseCase).execute(params)
        assertEquals(expectedStatus, viewModel.agentDetails.value?.status)
        assertEquals(expected, viewModel.agentDetails.value?.data)

        viewModel.agentDetails.removeObserver(observer)
    }

    @Test
    fun `get agent details live data invalid`() = runBlockingTest {
        val params = GetAgentDetailsUseCase.Params.create(TestConstants.ID_INVALID_TEXT)
        val expected = Exception(TestConstants.EXCEPTION_WRONG_PARAMS)
        val expectedStatus = Status.ERROR
        val observer = Observer<WorkResult<AgentDetails>> {}

        `when`(getAgentDetailsUseCase.execute(params)).thenAnswer { expected }
        try {
            viewModel.getAgentDetails(params.id)
        } catch (e: Exception) {
            verify(getAgentDetailsUseCase).execute(params)
            assertEquals(expected.message, e.message)
            assertEquals(expectedStatus, viewModel.agentDetails.value?.status)
        } finally {
            viewModel.agentDetails.removeObserver(observer)
        }
    }


    @Test
    fun `toggle agent's favorite status`() = runBlockingTest {
        val agent = AgentTestUtils.getLocalAgent()
        val updatedAgent = agent.copy(favorite = TestConstants.BOOLEAN_FALSE)
        val params = UpdateAgentFavoriteStatusUseCase.Params.create(updatedAgent)
        viewModel.agent.postValue(agent)

        `when`(updateAgentStatusUseCase.execute(params)).thenReturn(Unit)
        viewModel.updateAgentFavoriteStatus(updatedAgent)

        assertEquals(updatedAgent, viewModel.agent.value)
        verify(updateAgentStatusUseCase).execute(params)
    }
}