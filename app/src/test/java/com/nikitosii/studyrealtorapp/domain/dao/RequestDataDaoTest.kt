package com.nikitosii.studyrealtorapp.domain.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.util.RequestDataTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RequestDataDaoTest {
    private lateinit var database: RealtorDataBase
    private lateinit var dao: RequestDataDao

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealtorDataBase::class.java
        ).build()
        dao = database.requestDataDao()
        withContext(Dispatchers.IO) { dao.insert(RequestDataTestUtils.getExpectedRequestData()) }
    }

    @After
    fun teardown() = runBlocking {
        withContext(Dispatchers.IO) {
            dao.removeAll()
        }
        database.close()
    }

    @Test
    fun `get request data test`() = runTest {
        withContext(Dispatchers.IO) {
            val expected = RequestDataTestUtils.getExpectedRequestData()

            assertEquals(expected, dao.getData(ID_VALID))
        }
    }

    @Test
    fun `remove request data test`() = runTest {
        withContext(Dispatchers.IO) {
            dao.remove(ID_VALID)
            assertEquals(null, dao.getData(ID_VALID))
        }
    }

    @Test
    fun `remove all data test`() = runTest {
        withContext(Dispatchers.IO) {
            dao.removeAll()
            assertEquals(null, dao.getData(ID_VALID))
        }
    }
}