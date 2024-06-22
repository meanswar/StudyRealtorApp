package com.nikitosii.studyrealtorapp.domain.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.nikitosii.studyrealtorapp.core.source.db.RealtorDataBase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.AgentEntity
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
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
class AgentsDaoTest {
    private lateinit var database: RealtorDataBase
    private lateinit var dao: AgentDao

    @Before
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealtorDataBase::class.java
        ).build()
        dao = database.agentsDao()
        withContext(IO) { dao.insertAgents(AgentTestUtils.getExpectedAgentsList()) }
    }

    @After
    fun teardown() = runBlocking {
        withContext(IO) {
            dao.deleteAllAgents()
        }
        database.close()
    }

    @Test
    fun `get agents test`() = runTest {
        withContext(IO) {
            val expected = AgentTestUtils.getExpectedAgentsList()

            assertEquals(expected, dao.getLocalAgents())
        }
    }

    @Test
    fun `get agents which are in list test`() = runTest {
        withContext(IO) {
            val expected = AgentTestUtils.getExpectedAgentsList().first()

            val retrievedData = dao.getLocalAgents(listOf(ID_VALID_TEXT))
            assertEquals(expected, retrievedData.first())
        }
    }

    @Test
    fun `insert agents test`() = runTest {
        withContext(IO) {
            val initialData = AgentTestUtils.getExpectedAgentsList()
            val expected = initialData.map { it.copy(id = ID_VALID_TEXT + 1) } + initialData
            dao.insertAgents(expected)

            assertEquals(expected, dao.getLocalAgents())
        }
    }

    @Test
    fun `get favorite agents test`() = runTest {
        withContext(IO) {
            val initialData = AgentTestUtils.getExpectedAgentsList().first()
            val additionalData = listOf(
                initialData.copy(id = ID_VALID_TEXT + 1),
                initialData.copy(id = ID_VALID_TEXT + 2),
                initialData.copy(id = ID_VALID_TEXT + 3),
                initialData.copy(id = ID_VALID_TEXT + 4, favorite = false),
            )
            val expected = additionalData.filterNot { it.id == ID_VALID_TEXT + 4 }.toMutableList()
            expected.add(0, initialData)

            dao.insertAgents(additionalData)

            assertEquals(expected, dao.getFavoriteAgents())
        }
    }

    @Test
    fun `get favorite agents from id list test`() = runTest {
        withContext(IO) {
            val expected = AgentTestUtils.getExpectedAgentsList()
            val initialData = expected.first().copy(id = ID_VALID_TEXT + 1)

            dao.insertAgent(initialData)

            val retrievedData = dao.getFavoriteAgentsListFromList(listOf(ID_VALID_TEXT))
            assertEquals(expected, retrievedData)
        }
    }

    @Test
    fun `get recent favorite agents test`() = runTest {
        withContext(IO) {
            val initialData = AgentTestUtils.getExpectedAgentsList().first()
            val additionalData = listOf(
                initialData.copy(id = ID_VALID_TEXT + 1),
                initialData.copy(id = ID_VALID_TEXT + 2),
                initialData.copy(id = ID_VALID_TEXT + 3),
                initialData.copy(id = ID_VALID_TEXT + 4),
            )
            val expected = additionalData.takeLast(3).reversed()

            dao.insertAgents(additionalData)

            assertEquals(expected, dao.getRecentFavoriteAgents())
        }
    }

    @Test
    fun `delete all agents test`() = runTest {
        withContext(IO) {
            val initialData = AgentTestUtils.getExpectedAgentsList().first()
            val additionalData = listOf(
                initialData.copy(id = ID_VALID_TEXT + 1),
                initialData.copy(id = ID_VALID_TEXT + 2),
                initialData.copy(id = ID_VALID_TEXT + 3),
                initialData.copy(id = ID_VALID_TEXT + 4),
            )
            val expected = emptyList<AgentEntity>()

            dao.insertAgents(additionalData)

            dao.deleteAllAgents()

            assertEquals(expected, dao.getLocalAgents())
        }
    }

    @Test
    fun `delete agent test`() = runTest {
        withContext(IO) {
            val initialData = AgentTestUtils.getExpectedAgentsList().first()
            val additionalData = listOf(
                initialData.copy(id = ID_VALID_TEXT + 1),
                initialData.copy(id = ID_VALID_TEXT + 2),
                initialData.copy(id = ID_VALID_TEXT + 3),
                initialData.copy(id = ID_VALID_TEXT + 4),
            )
            val expected = additionalData.filterNot { it.id == ID_VALID_TEXT + 4 }.toMutableList()
            expected.add(0, initialData)

            dao.insertAgents(additionalData)

            dao.deleteAgent(id = ID_VALID_TEXT + 4)

            assertEquals(expected, dao.getLocalAgents())
        }
    }
}