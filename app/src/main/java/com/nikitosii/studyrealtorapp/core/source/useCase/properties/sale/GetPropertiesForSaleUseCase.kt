package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.PropertyRequest
import com.nikitosii.studyrealtorapp.core.source.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetPropertiesForSaleUseCase @Inject constructor(
    private val repo: PropertiesRepo,
    private val imageRepo: ImageRepo
) :
    UseCaseParams<List<Property>, GetPropertiesForSaleUseCase.Params>() {
    class Params private constructor(val request: PropertyRequest) {
        companion object {
            fun create(request: PropertyRequest): Params = Params(request)
        }
    }

    override suspend fun execute(data: Params): List<Property> {
        val image = data.request.address?.let { imageRepo.getImage(it) }
        val salesRequest = data.request.copy(imageUrl = image)
        val result = repo.getPropertiesForSale(salesRequest)
        repo.updateRequestHistory()
        return result
    }
}