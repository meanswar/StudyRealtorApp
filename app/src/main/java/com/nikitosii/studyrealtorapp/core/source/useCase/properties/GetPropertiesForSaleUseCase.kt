package com.nikitosii.studyrealtorapp.core.source.useCase.properties

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetPropertiesForSaleUseCase @Inject constructor(private val repo: PropertiesRepo) :
    UseCaseParams<List<Property>, GetPropertiesForSaleUseCase.Params>() {
    class Params private constructor(val request: SalesRequest) {
        companion object {
            fun create(request: SalesRequest): Params = Params(request)
        }
    }

    override suspend fun execute(data: Params): List<Property> = repo.getPropertiesForSale(data.request)
}