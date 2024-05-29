package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.FlowUseCase
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCase
import com.nikitosii.studyrealtorapp.util.Flow
import javax.inject.Inject

class GetAllLocalPropertiesUseCase @Inject constructor(private val repo: PropertiesRepo) :
    FlowUseCase<List<Property>>() {

    override fun execute(): Flow<List<Property>> = repo.getAllLocalProperties()
}