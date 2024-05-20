package com.nikitosii.studyrealtorapp.core.source.useCase.agent

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.FlowUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import com.nikitosii.studyrealtorapp.util.Flow
import javax.inject.Inject

class GetRecentFavoriteAgentsUseCase @Inject constructor(private val repo: AgentsRepo) :
    FlowUseCase<List<Agent>>() {

    override fun execute(): Flow<List<Agent>> = repo.getRecentFavoriteAgents()
}