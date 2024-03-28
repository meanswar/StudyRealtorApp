package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetPropertiesUseCase @Inject constructor(
    private val repo: PropertiesRepo
): UseCaseParams<List<Property>, GetPropertiesUseCase.Params>() {
    class Params() {

    }

    // TODO add filters
    override suspend fun execute(data: Params): List<Property> = repo.getPropertiesForSale(data.toString())
}