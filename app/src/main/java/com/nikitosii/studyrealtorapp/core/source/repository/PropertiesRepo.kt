package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest

interface PropertiesRepo {

    /**
     * Retrieves properties available for sale based on the provided [SearchRequest] model.
     *
     * Saves results and requests to local storage.
     *
     * @param data The [SearchRequest] containing criteria for property search.
     * @return A list of [Property] objects representing properties available for sale.
     */
    suspend fun getPropertiesForSale(
        data: SearchRequest,
        page: Int? = null,
        sort: String? = null
    ): List<Property>

    suspend fun saveProperties(properties: List<Property>)

    suspend fun updateProperty(property: Property)

    suspend fun getLocalFavoriteProperties(ids: List<String>): List<Property>

    suspend fun getFavoritePropertiesIds(ids: List<String>): List<String>

    suspend fun getLocalProperties(ids: List<String>): List<Property>

    suspend fun getPropertyDetails(id: String): PropertyDetails

}