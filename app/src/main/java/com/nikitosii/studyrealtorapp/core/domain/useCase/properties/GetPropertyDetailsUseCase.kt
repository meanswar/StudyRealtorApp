package com.nikitosii.studyrealtorapp.core.domain.useCase.properties

import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import javax.inject.Inject

class GetPropertyDetailsUseCase @Inject constructor(
    private val repo: PropertiesRepo
) : UseCaseParams<PropertyDetails, GetPropertyDetailsUseCase.Params>() {
    class Params private constructor(val id: String) {
        companion object {
            fun create(id: String) = Params(id)
        }
    }

    override suspend fun execute(data: Params): PropertyDetails = repo.getPropertyDetails(data.id)
}