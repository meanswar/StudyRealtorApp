package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.repository.SalePropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetPropertiesForSaleUseCase @Inject constructor(private val repo: SalePropertiesRepo) :
    UseCaseParams<List<Property>, GetPropertiesForSaleUseCase.Params>() {
    class Params private constructor(val request: SalesRequest) {
        companion object {
            fun create(request: SalesRequest): Params = Params(request)
        }
    }

    override suspend fun execute(data: Params): List<Property> {
        val result = repo.getPropertiesForSale(data.request)
        repo.updateRequestHistory()

        return result
    }
}