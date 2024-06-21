package com.nikitosii.studyrealtorapp.domain.usecase.agent

import com.nikitosii.studyrealtorapp.core.domain.useCase.agent.UpdateAgentFavoriteStatusUseCase
import com.nikitosii.studyrealtorapp.core.source.db.dao.AgentDao
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.di.DaggerTestAppComponent
import com.nikitosii.studyrealtorapp.domain.usecase.base.BaseUseCaseTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
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
    }

    @Test
    fun `update agent favorite status test`() = runTest {
        val oldAgents = dao.getLocalAgents()
        val notFavoriteAgent = Agent.from(oldAgents.first { !it.favorite })
        val params =
            UpdateAgentFavoriteStatusUseCase.Params.create(notFavoriteAgent.copy(favorite = true))
        assertThat(dao.getFavoriteAgents().size).isEqualTo(FAVORITE_AGENTS_AMOUNT)
        useCase.execute(params)
        val updatedAgents = dao.getLocalAgents()
        assertThat(dao.getFavoriteAgents().size).isEqualTo(UPDATED_FAVORITE_AGENTS_AMOUNT)
        assertThat(oldAgents.size).isEqualTo(updatedAgents.size)
    }

    companion object {
        private const val FAVORITE_AGENTS_AMOUNT = 2
        private const val UPDATED_FAVORITE_AGENTS_AMOUNT = 3
    }
}