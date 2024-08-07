package com.nikitosii.studyrealtorapp.core.domain.useCase.profile

import com.nikitosii.studyrealtorapp.core.domain.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCase
import javax.inject.Inject

class RemoveDataUseCase @Inject constructor(
    private val agentsRepo: AgentsRepo,
    private val requestDataRepo: RequestDataRepo,
    private val searchRequestRepo: SearchRequestRepo,
    private val propertiesRepo: PropertiesRepo
) : UseCase<Unit>() {

    override suspend fun execute() {
        agentsRepo.removeData()
        propertiesRepo.removeData()
        requestDataRepo.removeAll()
        searchRequestRepo.removeData()

        agentsRepo.refreshAgents()
        propertiesRepo.refreshProperties()
        searchRequestRepo.refreshSearchRequests()
    }
}