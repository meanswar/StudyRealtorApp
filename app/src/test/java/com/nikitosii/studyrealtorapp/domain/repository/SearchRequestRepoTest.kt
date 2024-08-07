package com.nikitosii.studyrealtorapp.domain.repository

import com.nikitosii.studyrealtorapp.core.domain.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.ChannelRecreateObserver
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.SearchRequestRepoImpl
import com.nikitosii.studyrealtorapp.core.source.connectivity.ConnectivityProvider
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.util.SearchRequestTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_LONG
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchRequestRepoTest {

    private lateinit var repo: SearchRequestRepo
    private lateinit var dao: SearchRequestDao

    @Before
    fun setup() {
        dao = mockk()
        val io = mockk<CoroutineDispatcher>(relaxed = true)
        val connectivityProvider = mockk<ConnectivityProvider>(relaxed = true)
        val recreateObserver = mockk<ChannelRecreateObserver>(relaxed = true)
        val errorHandler = mockk<NetworkErrorHandler>(relaxed = true)
        repo = SearchRequestRepoImpl(dao, io, connectivityProvider, recreateObserver, errorHandler)
    }

    @Test
    fun `save search request test`() = runTest {
        val initialData = SearchRequestTestUtils.getExpectedSearchRequest()
        val request = SearchRequestTestUtils.getExpectedEntity()

        coEvery { dao.insert(request) } returns ANY_LONG
        repo.saveSearchRequest(initialData)

        coVerify { dao.insert(request) }
    }

    @Test
    fun `remove search request test`() = runTest {
        coEvery { repo.removeSearchRequest(ANY_DIGITS) } just Runs
        repo.removeSearchRequest(ANY_DIGITS)

        coVerify { dao.remove(ANY_DIGITS) }
    }

    @Test
    fun `update search request test`() = runTest {
        val initialData = SearchRequestTestUtils.getExpectedSearchRequest()
        val request = SearchRequestTestUtils.getExpectedEntity()

        coEvery { dao.insert(request) } returns ANY_LONG
        repo.updateSearchRequest(initialData)

        coVerify { dao.insert(request) }
    }
}