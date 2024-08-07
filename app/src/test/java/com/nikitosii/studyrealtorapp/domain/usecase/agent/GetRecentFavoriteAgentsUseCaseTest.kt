package com.nikitosii.studyrealtorapp.domain.usecase.agent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetRecentFavoriteAgentsUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import dev.olog.flow.test.observer.test
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetRecentFavoriteAgentsUseCaseTest : BaseUseCaseTest<GetRecentFavoriteAgentsUseCase>() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var dao: AgentDao

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
        dao.insertAgents(AgentTestUtils.getExpectedAgentsList())
    }

    @After
    fun tearDown() {
        dao.deleteAllAgents()
    }

    @Test
    fun `get recent favorite agents test`() = runTest {
        useCase.execute().test(this) {
            assertValue { data ->
                val expected = AgentTestUtils.getExpectedLocalAgents().filter { it.favorite }
                assertEquals(expected, data.obj)
                true
            }
        }
    }
}