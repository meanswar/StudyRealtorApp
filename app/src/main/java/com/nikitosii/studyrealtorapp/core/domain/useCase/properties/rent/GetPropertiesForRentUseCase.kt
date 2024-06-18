package com.nikitosii.studyrealtorapp.core.domain.useCase.properties.rent

import com.nikitosii.studyrealtorapp.core.domain.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import javax.inject.Inject

class GetPropertiesForRentUseCase @Inject constructor(
    private val searchRequestRepo: SearchRequestRepo,
    private val propertiesRepo: PropertiesRepo,
    private val requestDataRepo: RequestDataRepo,
    private val imageRepo: ImageRepo
) : UseCaseParams<Pair<SearchRequest, List<Property>>, GetPropertiesForRentUseCase.Params>() {

    class Params private constructor(val request: SearchRequest) {
        companion object {
            fun create(request: SearchRequest) = Params(request)
        }
    }

    override suspend fun execute(data: Params): Pair<SearchRequest, List<Property>> {
        val properties = propertiesRepo.getPropertiesForRent(data.request)
        val requestImageUrl = imageRepo.getImage(data.request.address)

        val favoriteProperties =
            propertiesRepo.getFavoritePropertiesIds(properties.map { it.propertyId })
        val updatedProperties = properties
            .map { it.copy(favorite = favoriteProperties.contains(it.propertyId)) }
        propertiesRepo.saveProperties(updatedProperties)
        val request =
            searchRequestRepo.saveSearchRequest(data.request.copy(imageUrl = requestImageUrl.url))
        searchRequestRepo.refreshRecentSearchRequests(RequestType.RENT)
        requestDataRepo.saveData(
            RequestDataEntity(
                request.id!!,
                updatedProperties.map { it.propertyId })
        )

        return Pair(request, updatedProperties)
    }
}