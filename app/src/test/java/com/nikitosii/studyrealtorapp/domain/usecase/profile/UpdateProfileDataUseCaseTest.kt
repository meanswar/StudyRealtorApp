package com.nikitosii.studyrealtorapp.domain.usecase.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.UpdateProfileUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.ProfileTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UpdateProfileDataUseCaseTest : BaseUseCaseTest<UpdateProfileUseCase>() {

    @Inject
    lateinit var dao: ProfileDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
        dao.deleteProfile()
        dao.insertProfile(ProfileTestUtils.getExpectedProfile())
    }

    @After
    fun tearDown() {
        dao.deleteProfile()
    }

    @Test
    fun updateProfile() = runTest {
        val oldProfile = dao.getProfile()
        val expected = oldProfile?.copy(name = ID_VALID_TEXT)

        val params = UpdateProfileUseCase.Params.create(Profile.from(expected))
        useCase.execute(params)

        assertEquals(expected, dao.getProfile())
    }
}