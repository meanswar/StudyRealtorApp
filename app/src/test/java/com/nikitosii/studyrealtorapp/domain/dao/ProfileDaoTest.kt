package com.nikitosii.studyrealtorapp.domain.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.ProfileDao
import com.nikitosii.studyrealtorapp.util.ProfileTestUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProfileDaoTest {

    private lateinit var database: RealtorDataBase
    private lateinit var dao: ProfileDao

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealtorDataBase::class.java
        ).build()
        dao = database.profileDao()
        withContext(Dispatchers.IO) { dao.insertProfile(ProfileTestUtils.getExpectedProfile()) }
    }

    @After
    fun teardown() = runBlocking {
        withContext(Dispatchers.IO) {
            dao.deleteProfile()
        }
        database.close()
    }

    @Test
    fun `get profile test`() = runBlocking {
        withContext(Dispatchers.IO) {
            val expected = ProfileTestUtils.getExpectedProfile()
            assertEquals(expected, dao.getProfile())
        }
    }

    @Test
    fun `delete profile test`() = runBlocking {
        withContext(Dispatchers.IO) {
            dao.deleteProfile()
            assertEquals(null, dao.getProfile())
        }
    }
}