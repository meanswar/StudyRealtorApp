package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import javax.inject.Inject

class GetAllLocalPropertiesUseCase @Inject constructor(private val repo: PropertiesRepo): UseCase<List<Property>>() {

    override suspend fun execute(): List<Property> = repo.getAllLocalProperties()
}