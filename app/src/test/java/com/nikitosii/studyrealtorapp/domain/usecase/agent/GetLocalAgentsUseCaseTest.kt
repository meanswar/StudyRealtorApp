package com.nikitosii.studyrealtorapp.domain.usecase.agent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.MainCoroutineScopeRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.GetLocalAgentsUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.local.model.Address
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Office
import com.nikitosii.studyrealtorapp.core.source.local.model.Phone
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.SalePrice
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import com.nikitosii.studyrealtorapp.util.TestConstants
import dev.olog.flow.test.observer.test
import io.mockk.MockKAnnotations
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetLocalAgentsUseCaseTest : BaseUseCaseTest<GetLocalAgentsUseCase>() {

    @Inject
    lateinit var dao: AgentDao

    @get:Rule
    var scopeRule = MainCoroutineScopeRule()

    @Before
    override fun setup() {
        MockKAnnotations.init(this)
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
        dao.deleteAllAgents()
        dao.insertAgents(AgentTestUtils.getExpectedAgentsList())
    }

    @After
    fun tearDown() {
        dao.deleteAllAgents()
        Dispatchers.resetMain()
    }

    @Test
    fun `get local agents test`() = runTest {
        useCase.execute().test(this) {
            assertValue { data ->
                val expected = AgentTestUtils.getExpectedLocalAgents()
                assertEquals(expected, data.obj)
                true
            }
        }
    }
}