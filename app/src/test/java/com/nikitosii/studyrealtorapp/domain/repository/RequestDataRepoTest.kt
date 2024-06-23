package com.nikitosii.studyrealtorapp.domain.repository

import com.nikitosii.studyrealtorapp.core.domain.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.impl.RequestDataRepoImpl
import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.util.RequestDataTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ANY_DIGITS
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RequestDataRepoTest {

    private lateinit var repo: RequestDataRepo
    private lateinit var dao: RequestDataDao

    @Before
    fun setup() {
        dao = mockk()
        repo = RequestDataRepoImpl(dao)
    }

    @Test
    fun `get data test`() = runTest {
        val request = RequestDataTestUtils.getExpectedRequestData()

        coEvery { dao.getData(any()) } returns request
        repo.getData(ANY_DIGITS)

        coVerify { dao.getData(ANY_DIGITS) }
    }

    @Test
    fun `save data test`() = runTest {
        val request = RequestDataTestUtils.getExpectedRequestData()
        val initialData = request.copy(propertiesIds = request.propertiesIds + request.propertiesIds)

        coEvery { dao.insert(any()) } just Runs
        coEvery { dao.getData(any()) } returns request
        repo.saveData(request)

        coVerify { dao.insert(initialData) }
        coVerify { dao.getData(ID_VALID) }
    }

    @Test
    fun `delete data test`() = runTest {
        coEvery { dao.remove(any()) } returns Unit
        repo.delete(ANY_DIGITS)

        coVerify { dao.remove(ANY_DIGITS) }
    }

    @Test
    fun `remove all data test`() = runTest {
        coEvery { dao.removeAll() } returns Unit
        repo.removeAll()

        coVerify { dao.removeAll() }
    }
}