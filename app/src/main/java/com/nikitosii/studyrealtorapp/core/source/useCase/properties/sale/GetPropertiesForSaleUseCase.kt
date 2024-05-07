package com.nikitosii.studyrealtorapp.core.source.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.source.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.source.useCase.base.UseCaseParams
import javax.inject.Inject

class GetPropertiesForSaleUseCase @Inject constructor(
    private val searchRequestRepo: SearchRequestRepo,
    private val propertiesRepo: PropertiesRepo,
    private val requestDataRepo: RequestDataRepo,
    private val imageRepo: ImageRepo
) : UseCaseParams<Pair<SearchRequest, List<Property>>, GetPropertiesForSaleUseCase.Params>() {

    class Params private constructor(val request: SearchRequest) {
        companion object {
            fun create(request: SearchRequest) = Params(request)
        }
    }

    override suspend fun execute(data: Params): Pair<SearchRequest, List<Property>> {
        val properties = propertiesRepo.getPropertiesForSale(data.request)
        val requestImageUrl = imageRepo.getImage(data.request.address)

        val favoriteProperties =
            propertiesRepo.getFavoritePropertiesIds(properties.map { it.propertyId })
        val updatedProperties = properties
            .map { it.copy(favorite = favoriteProperties.contains(it.propertyId)) }
        propertiesRepo.saveProperties(updatedProperties)
        val request = searchRequestRepo.saveSearchRequest(data.request.copy(imageUrl = requestImageUrl))

        requestDataRepo.saveData(RequestDataEntity(request.id!!, updatedProperties.map { it.propertyId }))

        return Pair(request, updatedProperties)
    }
}