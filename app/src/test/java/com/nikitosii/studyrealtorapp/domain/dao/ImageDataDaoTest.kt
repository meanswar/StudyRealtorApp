package com.nikitosii.studyrealtorapp.domain.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.ImageDataDao
import com.nikitosii.studyrealtorapp.util.ImageDataTestUtils
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
class ImageDataDaoTest {
    private lateinit var database: RealtorDataBase
    private lateinit var dao: ImageDataDao

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealtorDataBase::class.java
        ).build()
        dao = database.imageDataDao()
        withContext(IO) { dao.insertImageData(ImageDataTestUtils.getExpectedLocalImageData()) }
    }

    @After
    fun teardown() = runBlocking {
        withContext(IO) {
            dao.deleteAll()
        }
        database.close()
    }

    @Test
    fun `get image test`() = runBlocking {
        withContext(IO) {
            val expected = ImageDataTestUtils.getExpectedLocalImageData()

            assertEquals(expected, dao.getImage(expected.id))
        }
    }

    @Test
    fun `remove data test`() = runTest {
        withContext(IO) {
            dao.deleteAll()
            assertEquals(null, dao.getImage(ID_VALID_TEXT))
        }
    }
}