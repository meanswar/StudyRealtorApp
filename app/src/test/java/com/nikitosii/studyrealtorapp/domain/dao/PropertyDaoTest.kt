package com.nikitosii.studyrealtorapp.domain.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.util.PropertyTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_FOR_REMOVING
import com.nikitosii.studyrealtorapp.util.TestConstants.ID_VALID_TEXT
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PropertyDaoTest {

    private lateinit var database: RealtorDataBase
    private lateinit var dao: PropertyDao

    @Before
    fun setup() = runBlocking {
        withContext(IO) {
            database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                RealtorDataBase::class.java
            ).build()
            dao = database.propertyDao()
        }
    }

    @After
    fun teardown() = runBlocking {
        withContext(IO) {
            dao.deleteAllProperties()
        }
        database.close()
    }


    @Test
    fun `insert and get properties test`() = runTest {
        withContext(IO) {
            val expected = PropertyTestUtils.getExpectedProperties()

            dao.insertProperties(expected)

            assertEquals(expected, dao.getProperties())
        }
    }

    @Test
    fun `insert and get local properties test`() = runTest {
        withContext(IO) {
            val initialData = PropertyTestUtils.getExpectedProperties()
            dao.insertProperties(initialData)

            val expected =
                PropertyTestUtils.getExpectedProperties().filter { it.propertyId == ID_VALID_TEXT }
            assertEquals(expected, dao.getLocalProperties(listOf(ID_VALID_TEXT)))
        }
    }

    @Test
    fun `insert property and get property test`() = runTest {
        withContext(IO) {
            val initialData = PropertyTestUtils.getExpectedProperties()
            val expected = initialData.last().copy(propertyId = ID_VALID_FOR_REMOVING)

            dao.insertProperties(initialData + expected)

            assertEquals(expected, dao.getLocalProperty(ID_VALID_FOR_REMOVING))
        }
    }

    @Test
    fun `get favourite properties test`() = runTest {
        withContext(IO) {
            val initialData = PropertyTestUtils.getExpectedProperties()
            val expected = initialData.filter { it.favorite }
            val requestData = expected.map { it.propertyId }

            dao.insertProperties(initialData)

            assertEquals(expected, dao.getFavoriteProperties(requestData))
        }
    }

    @Test
    fun `remove all properties test`() = runTest {
        withContext(IO) {
            val initialData = PropertyTestUtils.getExpectedProperties()
            val expected = emptyList<PropertyEntity>()

            dao.insertProperties(initialData)
            assertEquals(initialData, dao.getProperties())

            dao.deleteAllProperties()
            assertEquals(expected, dao.getProperties())
        }
    }

    @Test
    fun `remove property test`() = runTest {
        withContext(IO) {
            val initialData = PropertyTestUtils.getExpectedProperties()
            val expected = initialData.filter { it.propertyId != ID_VALID_FOR_REMOVING }

            dao.insertProperties(initialData)
            assertEquals(initialData, dao.getProperties())

            dao.remove(ID_VALID_FOR_REMOVING)
            assertEquals(expected, dao.getProperties())
        }
    }
}