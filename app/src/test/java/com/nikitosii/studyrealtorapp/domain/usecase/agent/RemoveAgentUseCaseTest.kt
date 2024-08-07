package com.nikitosii.studyrealtorapp.domain.usecase.agent

import android.annotation.SuppressLint
import com.nikitosii.studyrealtorapp.util.TestConstants
import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.RemoveAgentUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import com.nikitosii.studyrealtorapp.util.AgentTestUtils
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.internal.runners.JUnit4ClassRunner
import org.junit.runner.RunWith
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(JUnit4ClassRunner::class)
class RemoveAgentUseCaseTest : BaseUseCaseTest<RemoveAgentUseCase>() {

    @Inject
    lateinit var dao: AgentDao

    @Before
    override fun setup() {
        DaggerTestAppComponent.builder()
            .build()
            .inject(this)
        dao.insertAgents(AgentTestUtils.getExpectedAgentsList())
    }

    @SuppressLint("CheckResult")
    @Test
    fun `remove agent valid test`() = runTest {
        val params = RemoveAgentUseCase.Params.create(TestConstants.ID_VALID_FOR_REMOVING)
        try {
            val result = useCase.execute(params)
            assertEquals(Unit, result)
        }
        catch (e: Exception) { assert(false) }
    }

    @SuppressLint("CheckResult")
    @Test
    fun `remove agent invalid test`() = runTest {
        val params = RemoveAgentUseCase.Params.create(TestConstants.ID_INVALID_TEXT)
        try {
            val result = useCase.execute(params)
            assertEquals(Unit, result)
        }
        catch (e: Exception) { assert(false) }
    }
}