package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetLocalPropertyUseCase @Inject constructor(private val repo: PropertiesRepo): UseCaseParams<Property, GetLocalPropertyUseCase.Params>() {
    class Params private constructor(val propertyId: String) {
        companion object {
            fun create(propertyId: String) = Params(propertyId)
        }
    }

    override suspend fun execute(data: Params): Property = repo.getLocalProperty(data.propertyId)
}