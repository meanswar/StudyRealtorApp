package com.nikitosii.studyrealtorapp.core.domain.useCase.agent

import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import javax.inject.Inject

class RemoveAgentUseCase @Inject constructor(private val repo: AgentsRepo) :
    UseCaseParams<Unit, RemoveAgentUseCase.Params>() {
    class Params private constructor(val id: String) {
        companion object {
            fun create(id: String): Params = Params(id)
        }
    }

    override suspend fun execute(data: Params) {
        repo.remove(data.id)
        repo.refreshAgents()
        repo.refreshRecentFavoriteAgents()
    }
}