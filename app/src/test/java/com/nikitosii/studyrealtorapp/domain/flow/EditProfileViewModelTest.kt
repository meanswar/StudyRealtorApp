package com.nikitosii.studyrealtorapp.domain.flow

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.UpdateProfileUseCase
import com.nikitosii.studyrealtorapp.flow.profile.edit.EditProfileViewModel
import com.nikitosii.studyrealtorapp.util.ProfileTestUtils
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
class EditProfileViewModelTest : BaseViewModelTest<EditProfileViewModel>() {

    @Mock
    private lateinit var updateProfileUseCase: UpdateProfileUseCase

    private val observer = Observer<WorkResult<Unit>> {}

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        viewModel.updateProfileStatus.observeForever(observer)
        viewModel = EditProfileViewModel(updateProfileUseCase, testDispatcher)
    }

    @After
    fun teardown() {
        Dispatchers.resetMain()
        viewModel.updateProfileStatus.removeObserver(observer)
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `update profile test`() = runBlockingTest {
        val profile = ProfileTestUtils.getLocalProfile()
        val expected = WorkResult.success(Unit)
        val params = UpdateProfileUseCase.Params.create(profile)

        viewModel.profileName.postValue(profile.name)
        viewModel.profileLastName.postValue(profile.surname)
        viewModel.profilePhoto.postValue(profile.photo)
        viewModel.profilePhone.postValue(profile.phone)
        viewModel.profileEmail.postValue(profile.email)

        `when`(updateProfileUseCase.execute(params)).thenReturn(Unit)
        viewModel.updateProfile()

        verify(updateProfileUseCase).execute(params)
        assertEquals(expected, viewModel.updateProfileStatus.value)
    }
}