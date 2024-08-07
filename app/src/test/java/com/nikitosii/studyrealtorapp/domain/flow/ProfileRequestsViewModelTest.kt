package com.nikitosii.studyrealtorapp.domain.flow

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.GetLocalRequestsUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.RemoveSearchRequestUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.request.UpdateRequestUseCase
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.flow.profile.requests.ProfileRequestsViewModel
import com.nikitosii.studyrealtorapp.util.SearchRequestTestUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProfileRequestsViewModelTest : BaseViewModelTest<ProfileRequestsViewModel>() {

    @Mock
    private lateinit var getLocalRequestsUseCase: GetLocalRequestsUseCase

    @Mock
    private lateinit var updateRequestUseCase: UpdateRequestUseCase

    @Mock
    private lateinit var removeRequestUseCase: RemoveSearchRequestUseCase

    private val observer = Observer<List<SearchRequest>> {}
    private val flow = MutableSharedFlow<Status<List<SearchRequest>>>()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        `when`(getLocalRequestsUseCase.execute()).thenReturn(flow)

        viewModel = ProfileRequestsViewModel(
            getLocalRequestsUseCase,
            updateRequestUseCase,
            removeRequestUseCase,
            testDispatcher
        )

        viewModel.requests.observeForever(observer)
    }

    @Test
    fun `update property test`() = runBlockingTest {
        val request = SearchRequestTestUtils.getExpectedSearchRequest()
        val updatedRequest = request.copy(favorite = !request.favorite)
        val params = UpdateRequestUseCase.Params.create(updatedRequest)

        `when`(updateRequestUseCase.execute(params)).thenReturn(Unit)

        viewModel.updateRequest(request)

        verify(updateRequestUseCase).execute(params)
        verify(getLocalRequestsUseCase).execute()
    }

    @Test
    fun `remove request test`() = runBlockingTest {
        val request = SearchRequestTestUtils.getExpectedSearchRequest()
        val params = RemoveSearchRequestUseCase.Params.create(request.id ?: return@runBlockingTest)

        `when`(removeRequestUseCase.execute(params)).thenReturn(Unit)

        viewModel.removeRequest(request.id ?: return@runBlockingTest)

        verify(removeRequestUseCase).execute(params)
        verify(getLocalRequestsUseCase).execute()
    }
}