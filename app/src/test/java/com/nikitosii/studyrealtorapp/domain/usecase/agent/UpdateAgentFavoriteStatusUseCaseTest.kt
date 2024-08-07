package com.nikitosii.studyrealtorapp.domain.usecase.agent

import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class UpdateAgentFavoriteStatusUseCaseTest : BaseUseCaseTest<UpdateAgentFavoriteStatusUseCase>() {

    @Inject
    lateinit var dao: AgentDao

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
        dao.deleteAllAgents()
        dao.insertAgents(AgentTestUtils.getExpectedAgentsList())
    }

    @After
    fun tearDown() {
        dao.deleteAllAgents()
    }

    @Test
    fun `update agent favorite status test`() = runTest {
        val agent = Agent.from(dao.getLocalAgents().first())
        val expected = agent.copy(favorite = !agent.favorite)

        val params = UpdateAgentFavoriteStatusUseCase.Params.create(expected)
        useCase.execute(params)

        val result = dao.getLocalAgents()

        assertEquals(AGENTS_EXPECTED_SIZE, result.size)
        assertEquals(expected, Agent.from(result.first()))
    }

    companion object {
        private const val AGENTS_EXPECTED_SIZE = 1
    }
}