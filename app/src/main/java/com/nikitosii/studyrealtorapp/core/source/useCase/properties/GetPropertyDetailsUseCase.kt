package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetPropertyDetailsUseCase @Inject constructor(
    private val repo: PropertiesRepo
): UseCaseParams<PropertyDetails, GetPropertyDetailsUseCase.Params>() {
    class Params private constructor(val id: String) {
        companion object {
            fun create(id: String) = Params(id)
        }
    }

    override suspend fun execute(data: Params): PropertyDetails = repo.getPropertyDetails(data.id)
}