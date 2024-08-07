package com.nikitosii.studyrealtorapp.domain.usecase.profile

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.core.domain.useCase.profile.GetProfileFlowUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import dev.olog.flow.test.observer.test
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
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class GetProfileFlowUseCaseTest : BaseUseCaseTest<GetProfileFlowUseCase>() {

    private val testDispatcher = TestCoroutineDispatcher()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        MockitoAnnotations.initMocks(this)
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
    fun `get profile test`() = runTest {
        useCase.execute().test(this) {
            assertValue { data ->
                val obj = data.obj
                obj?.equals(
                    Profile(
                        TestConstants.ANY_TEXT,
                        TestConstants.ANY_TEXT,
                        TestConstants.ANY_TEXT,
                        TestConstants.ANY_TEXT,
                        Uri.EMPTY
                    )
                ) == true
            }
        }
    }
}