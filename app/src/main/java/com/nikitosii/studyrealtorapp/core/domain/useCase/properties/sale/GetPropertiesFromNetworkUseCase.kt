package com.nikitosii.studyrealtorapp.core.domain.useCase.properties.sale

import com.nikitosii.studyrealtorapp.core.domain.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.RequestDataRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.SearchRequestRepo
import com.nikitosii.studyrealtorapp.core.domain.useCase.base.UseCaseParams
import com.nikitosii.studyrealtorapp.core.source.db.entity.RequestDataEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.image.ImageData
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import javax.inject.Inject

class GetPropertiesFromNetworkUseCase @Inject constructor(
    private val searchRequestRepo: SearchRequestRepo,
    private val propertiesRepo: PropertiesRepo,
    private val requestDataRepo: RequestDataRepo,
    private val imageRepo: ImageRepo
) : UseCaseParams<Pair<SearchRequest, List<Property>>, GetPropertiesFromNetworkUseCase.Params>() {

    class Params private constructor(val request: SearchRequest, val page: Int) {
        companion object {
            fun create(request: SearchRequest, page: Int) = Params(request, page)
        }
    }

    override suspend fun execute(data: Params): Pair<SearchRequest, List<Property>> {
        val properties =
            if (data.request.requestType == RequestType.SALE)
                propertiesRepo.getPropertiesForSale(data.request, data.page)
            else
                propertiesRepo.getPropertiesForRent(data.request, data.page)
        val requestImage = getImageData(data.request)

        val favoriteProperties =
            propertiesRepo.getFavoritePropertiesIds(properties.map { it.propertyId })
        val updatedProperties = properties
            .map { it.copy(favorite = favoriteProperties.contains(it.propertyId)) }
        propertiesRepo.saveProperties(updatedProperties)
        val request =
            searchRequestRepo.saveSearchRequest(data.request.copy(imageUrl = requestImage.url))
        searchRequestRepo.refreshRecentSearchRequests(RequestType.SALE)
        searchRequestRepo.refreshSearchRequests()
        requestDataRepo.saveData(
            RequestDataEntity(
                request.id!!,
                updatedProperties.map { it.propertyId })
        )

        return Pair(request, updatedProperties)
    }

    private suspend fun getImageData(request: SearchRequest): ImageData {
        val localImage = imageRepo.getLocalImage(request.address)
        val requestImageUrl = if (localImage?.url == null) {
            val data = imageRepo.getImage(request.address)
            imageRepo.insertImageData(data)
            data
        } else localImage
        return requestImageUrl
    }
}