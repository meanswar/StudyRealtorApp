package com.nikitosii.studyrealtorapp.domain.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.SearchRequestEntity
import com.nikitosii.studyrealtorapp.util.SearchRequestTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID
import com.nikitosii.studyrealtorapp.util.TestConstants.SEARCH_TYPE_RENT
import com.nikitosii.studyrealtorapp.util.TestConstants.SEARCH_TYPE_SALE
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchRequestDaoTest {

    private lateinit var database: RealtorDataBase
    private lateinit var dao: SearchRequestDao

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealtorDataBase::class.java
        ).build()
        dao = database.searchRequestDao()
    }

    @After
    fun teardown() = runBlocking {
        withContext(IO) {
            dao.removeAll()
        }
        database.close()
    }

    @Test
    fun `get search requests test`() = runTest {
        withContext(Dispatchers.IO) {
            val expected = SearchRequestTestUtils.getExpectedEntity()
            dao.insert(expected)

            val actual = dao.getAllSearchRequests()

            assertEquals(expected, actual.first())
            assertEquals(1, actual.size)
        }
    }

    @Test
    fun `get recent rent search requests test`() = runTest {
        withContext(IO) {
            val expected = SearchRequestTestUtils.getExpectedEntity()
            dao.insert(expected)
            val actual = dao.getRecentSearchRequests(SEARCH_TYPE_RENT)
            assertEquals(expected, actual.first())
            assertEquals(1, actual.size)
        }
    }

    @Test
    fun `get recent sale search requests test`() = runTest {
        withContext(IO) {
            val initialData = SearchRequestTestUtils.getExpectedEntity()
            val expected = 0
            dao.insert(initialData)

            val actual = dao.getRecentSearchRequests(SEARCH_TYPE_SALE)

            assertEquals(expected, actual.size)
        }
    }

    @Test
    fun `delete all test`() = runTest {
        withContext(IO) {
            dao.insert(SearchRequestTestUtils.getExpectedEntity())
            dao.removeAll()
            assertEquals(emptyList<SearchRequestEntity>(), dao.getAllSearchRequests())
        }
    }

    @Test
    fun `delete request test`() = runTest {
        withContext(IO) {
            dao.insert(SearchRequestTestUtils.getExpectedEntity())
            dao.remove(ID_VALID)

            assertEquals(emptyList<SearchRequestEntity>(), dao.getAllSearchRequests())
        }
    }
}