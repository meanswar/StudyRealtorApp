package com.nikitosii.studyrealtorapp.core.domain.useCase.agent

import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.FlowUseCase
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.Agent
import com.nikitosii.studyrealtorapp.util.Flow
import javax.inject.Inject

class GetLocalAgentsUseCase @Inject constructor(private val repo: AgentsRepo) :
    FlowUseCase<List<Agent>>() {

    override fun execute(): Flow<List<Agent>> = repo.getLocalAgents()
}