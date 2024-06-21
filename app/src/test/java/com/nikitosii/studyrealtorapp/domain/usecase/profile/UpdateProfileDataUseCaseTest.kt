package com.nikitosii.studyrealtorapp.domain.usecase.profile

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_TEXT
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.UpdateProfileUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.ProfileEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
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
    }

    @Test
    fun updateProfile() = runTest {
        val oldProfile = ProfileEntity(ID_VALID, ANY_TEXT, ANY_TEXT, ANY_TEXT, ANY_TEXT, Uri.EMPTY)
        assert(dao.getProfile()?.equals(oldProfile) == true)

        val updatedProfile = oldProfile.copy(name = ID_VALID_TEXT)
        val params = UpdateProfileUseCase.Params.create(Profile.from(updatedProfile))
        useCase.execute(params)

        assert(dao.getProfile()?.equals(updatedProfile) == true)
    }
}