package com.nikitosii.studyrealtorapp.domain.repository

import com.nikitosii.studyrealtorapp.core.domain.repository.ProfileRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.ProfileRepoImpl
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.util.ProfileTestUtils
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
class ProfileRepoTest {

    private lateinit var repo: ProfileRepo

    private lateinit var dao: ProfileDao
    private lateinit var io: CoroutineDispatcher
    private lateinit var connectivityProvider: ConnectivityProvider
    private lateinit var recreateObserver: ChannelRecreateObserver

    @Before
    fun setup() {
        dao = mockk()
        io = mockk(relaxed = true)
        connectivityProvider = mockk(relaxed = true)
        recreateObserver = mockk(relaxed = true)

        repo = ProfileRepoImpl(
            dao,
            io,
            connectivityProvider,
            recreateObserver
        )
    }

    @Test
    fun `remove profile test`() = runTest {
        coEvery { dao.deleteProfile() } just Runs
        repo.removeProfileData()

        coVerify { dao.deleteProfile() }
    }

    @Test
    fun `update profile test`() = runTest {
        val initialData = ProfileTestUtils.getExpectedProfile()
        val request = Profile.from(initialData)

        coEvery { dao.insertProfile(initialData) } just Runs
        repo.updateProfile(request)

        coVerify { dao.insertProfile(initialData) }
    }
}