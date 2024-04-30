package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetLocalPropertiesForSaleUseCase @Inject constructor(
    private val repo: PropertiesRepo
): UseCaseParams<List<Property>, GetLocalPropertiesForSaleUseCase.Params>() {
    class Params private constructor(val query: SalesRequest) {
        companion object {
            fun create(query: SalesRequest) = Params(query)
        }
    }

    override suspend fun execute(data: Params): List<Property> = repo.getByQuery(data.query)
}