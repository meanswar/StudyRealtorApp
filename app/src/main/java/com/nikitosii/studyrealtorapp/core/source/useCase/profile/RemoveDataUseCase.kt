package com.nikitosii.studyrealtorapp.core.source.useCase.profile

import com.nikitosii.studyrealtorapp.core.source.repository.AgentsRepo
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import javax.inject.Inject

class RemoveDataUseCase @Inject constructor(
    private val agentsRepo: AgentsRepo,
    private val requestDataRepo: RequestDataRepo,
    private val searchRequestRepo: SearchRequestRepo,
    private val propertiesRepo: PropertiesRepo
    ): UseCase<Unit>() {

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