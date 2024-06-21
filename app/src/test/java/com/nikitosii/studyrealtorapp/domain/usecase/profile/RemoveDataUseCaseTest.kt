package com.nikitosii.studyrealtorapp.domain.usecase.profile

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.RemoveDataUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.RequestDataDao
import com.nikitosii.studyrealtorapp.core.source.db.dao.SearchRequestDao
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
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
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4ClassRunner::class)
class RemoveDataUseCaseTest: BaseUseCaseTest<RemoveDataUseCase>() {

    @MockK
    lateinit var agentsDao: AgentDao

    @MockK
    lateinit var propertyDao: PropertyDao

    @MockK
    lateinit var searchRequestDao: SearchRequestDao


    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `remove data test`() = runTest {
        assert(agentsDao.getLocalAgents().isNotEmpty())
        assert(propertyDao.getProperties().isNotEmpty())
        assert(searchRequestDao.getAllSearchRequests().isNotEmpty())
        useCase.execute()
        assert(agentsDao.getLocalAgents().isEmpty())
        assert(propertyDao.getProperties().isEmpty())
        assert(searchRequestDao.getAllSearchRequests().isEmpty())
    }
}