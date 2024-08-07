package com.nikitosii.studyrealtorapp.domain.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchPropertiesDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchPropertiesDataEntity
import com.nikitosii.studyrealtorapp.util.SearchPropertiesTestUtils
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
class SearchPropertiesDaoTest {

    private lateinit var database: RealtorDataBase
    private lateinit var dao: SearchPropertiesDao

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealtorDataBase::class.java
        ).build()
        dao = database.searchPropertiesDao()
        withContext(Dispatchers.IO) { dao.insertAll(SearchPropertiesTestUtils.getExpectedData()) }
    }

    @After
    fun teardown() = runBlocking {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
        }
        database.close()
    }

    @Test
    fun `get search requests test`() = runTest {
        withContext(Dispatchers.IO) {
            val expected = SearchPropertiesTestUtils.getExpectedData().map { it.query }
            assertEquals(expected, dao.getSearchRequests())
        }
    }

    @Test
    fun `get by id test`() = runTest {
        withContext(Dispatchers.IO) {
            val expected = SearchPropertiesTestUtils.getExpectedData()[0]
            assertEquals(expected, dao.getById(expected.id))
        }
    }

    @Test
    fun `get by query test`() = runTest {
        withContext(Dispatchers.IO) {
            val expected = SearchPropertiesTestUtils.getExpectedData()[0]
            assertEquals(expected, dao.getByQuery(expected.query))
        }
    }

    @Test
    fun `delete by id test`() = runTest {
        withContext(Dispatchers.IO) {
            val expected = SearchPropertiesTestUtils.getExpectedData()[0]
            dao.deleteById(expected.id)
            assertEquals(null, dao.getById(expected.id))
        }
    }

    @Test
    fun `delete all test`() = runTest {
        withContext(Dispatchers.IO) {
            dao.deleteAll()
            assertEquals(emptyList<SearchPropertiesDataEntity>(), dao.getSearchRequests())
        }
    }

    @Test
    fun `insert and get data test`() = runTest {
        withContext(Dispatchers.IO) {
            val expected = SearchPropertiesTestUtils.getExpectedData().first()

            assertEquals(expected, dao.getById(expected.id))
        }
    }
}