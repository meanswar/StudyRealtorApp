package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.util.Flow

interface PropertiesRepo {

    /**
     * Retrieves properties available for sale based on the provided [SearchRequest] model.
     *
     * Saves results and requests to local storage.
     *
     * @param data The [SearchRequest] containing criteria for property search.
     * @param page The page number for pagination (optional).
     * @return A list of [Property] objects representing properties available for sale.
     */
    suspend fun getPropertiesForSale(
        data: SearchRequest,
        page: Int? = null
    ): List<Property>

    /**
     * Saves a list of properties into the local storage.
     *
     * @param properties The list of [Property] models to be saved.
     */
    suspend fun saveProperties(properties: List<Property>)

    /**
     * Updates a property in the local storage.
     *
     * @param property The [Property] model to be updated.
     */
    suspend fun updateProperty(property: Property)

    /**
     * Retrieves a list of favorite properties from the local storage based on the provided IDs.
     *
     * @param ids A list of favorite property IDs to retrieve.
     * @return A list of favorite [Property] with the specified IDs.
     */
    suspend fun getLocalFavoriteProperties(ids: List<String>): List<Property>

    /**
     * Retrieves a list of favorite property IDs from the local storage based on the provided IDs.
     *
     * @param ids A list of favorite property IDs to retrieve.
     * @return A list of favorite property IDs as [String].
     */
    suspend fun getFavoritePropertiesIds(ids: List<String>): List<String>

    /**
     * Retrieves a list of properties from the local storage based on the provided IDs.
     *
     * @param ids A list of property IDs to retrieve.
     * @return A list of [Property] with the specified IDs from the local storage.
     */
    suspend fun getLocalProperties(ids: List<String>): List<Property>

    /**
     * Retrieves a single property from the local storage based on the provided ID.
     *
     * @param id The ID of the property to retrieve.
     * @return The [Property] with the specified ID.
     */
    suspend fun getLocalProperty(id: String): Property

    /**
     * Retrieves all properties from the local storage.
     *
     * @return A flow of lists containing all local properties.
     */
    fun getAllLocalProperties(): Flow<List<Property>>

    /**
     * Refreshes the list of properties in the local storage.
     */
    suspend fun refreshProperties()

    /**
     * Removes all data from the local storage.
     */
    suspend fun removeData()

    /**
     * Retrieves detailed information of a property based on the provided ID.
     *
     * @param id The ID of the property to retrieve details for.
     * @return The [PropertyDetails] of the specified property.
     */
    suspend fun getPropertyDetails(id: String): PropertyDetails

    /**
     * Retrieves properties available for rent based on the provided [SearchRequest] model.
     *
     * Saves results and requests to local storage.
     *
     * @param data The [SearchRequest] containing criteria for property search.
     * @param page The page number for pagination (optional).
     * @return A list of [Property] objects representing properties available for rent.
     */
    suspend fun getPropertiesForRent(
        data: SearchRequest,
        page: Int? = null
    ): List<Property>
}