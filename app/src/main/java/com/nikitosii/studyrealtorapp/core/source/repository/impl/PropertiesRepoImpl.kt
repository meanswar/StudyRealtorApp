package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.db.dao.PropertyDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.PropertyEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.nikitosii.studyrealtorapp.core.source.repository.PropertiesRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import javax.inject.Inject

class PropertiesRepoImpl @Inject constructor(
    private val api: PropertiesApi,
    private val dao: PropertyDao,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), PropertiesRepo {

    override suspend fun getPropertiesForSale(
        data: SearchRequest,
        page: Int?,
        sort: String?
    ): List<Property> = runWithErrorHandler {
        val houses = data.houses.map { it.apiType }.joinToString(",") { it }
        api.getPropertiesForSale(
            data.address,
            houses.ifEmpty { null },
            data.priceMin,
            data.priceMax,
            data.bedsMin,
            data.bedsMax,
            data.bathsMin,
            data.bathsMax,
            data.sqftMin,
            data.sqftMax,
            page,
            sort
        )
            .homeSearch
            .results
            .map { Property.from(it) }
    }

    override suspend fun saveProperties(properties: List<Property>) {
        dao.insertProperties(properties.map { PropertyEntity.from(it) })
    }

    override suspend fun updateProperty(property: Property) {
        val updated = PropertyEntity.from(property)
        dao.insertProperty(updated)
    }

    override suspend fun getLocalFavoriteProperties(ids: List<String>): List<Property> =
        dao.getFavoriteProperties(ids).map { Property.from(it) }

    override suspend fun getFavoritePropertiesIds(ids: List<String>): List<String> =
        dao.getFavoriteProperties(ids).map { Property.from(it) }.map { it.propertyId }


    override suspend fun getLocalProperties(ids: List<String>): List<Property> =
        dao.getLocalProperties(ids).map { Property.from(it) }

    override suspend fun getLocalProperty(id: String): Property =
        Property.from(dao.getLocalProperty(id))

    override suspend fun getAllLocalProperties(): List<Property> = dao.getProperties().map { Property.from(it) }

    override suspend fun getPropertyDetails(id: String): PropertyDetails = runWithErrorHandler {
        PropertyDetails.from(api.getPropertyDetails(id).result)
    }
}