package com.nikitosii.studyrealtorapp.core.source.useCase.agent

import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import javax.inject.Inject

class GetRecentFavoriteAgentsUseCase @Inject constructor(private val repo: AgentsRepo) :
    UseCase<List<Agent>>() {

    override suspend fun execute(): List<Agent> = repo.getRecentFavoriteAgents()
}