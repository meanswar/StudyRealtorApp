package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SalePropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetSearchHistoryByQueryUseCase @Inject constructor(
    private val repo: SalePropertiesRepo
): UseCaseParams<List<Property>, GetSearchHistoryByQueryUseCase.Params>() {
    class Params private constructor(val query: SalesRequest) {
        companion object {
            fun create(query: SalesRequest) = Params(query)
        }
    }

    override suspend fun execute(data: Params): List<Property> = repo.getByQuery(data.query)
}