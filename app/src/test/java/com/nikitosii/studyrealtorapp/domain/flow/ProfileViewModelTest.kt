package com.nikitosii.studyrealtorapp.domain.flow

import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.RemoveDataUseCase
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.RemoveProfileDataUseCase
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.flow.profile.ProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProfileViewModelTest : BaseViewModelTest<ProfileViewModel>() {
    @Mock
    private lateinit var getProfileFlowUseCase: GetProfileFlowUseCase

    @Mock
    private lateinit var removeDataUseCase: RemoveDataUseCase

    @Mock
    private lateinit var removeProfileDataUseCase: RemoveProfileDataUseCase

    private val profileFlow = MutableSharedFlow<Status<Profile>>()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        `when`(getProfileFlowUseCase.execute()).thenReturn(profileFlow)

        viewModel = ProfileViewModel(
            getProfileFlowUseCase,
            removeProfileDataUseCase,
            removeDataUseCase,
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
    fun `remove profile data calls`() = runTest {
        `when`(removeProfileDataUseCase.execute()).thenReturn(Unit)

        viewModel.removeProfileData()

        verify(removeProfileDataUseCase).execute()
    }

    @Test
    fun `remove data calls`() = runTest {
        `when`(removeDataUseCase.execute()).thenReturn(Unit)

        viewModel.removeData()

        verify(removeDataUseCase).execute()
    }
}