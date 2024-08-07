package com.nikitosii.studyrealtorapp.core.domain.useCase.properties

import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import javax.inject.Inject

class GetLocalPropertyUseCase @Inject constructor(private val repo: PropertiesRepo) :
    UseCaseParams<Property, GetLocalPropertyUseCase.Params>() {
    class Params private constructor(val propertyId: String) {
        companion object {
            fun create(propertyId: String) = Params(propertyId)
        }
    }

    override suspend fun execute(data: Params): Property = repo.getLocalProperty(data.propertyId)
}