package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.util.Flow

interface PropertiesRepo {

    /**
     * Retrieves properties available for sale based on the provided [SalesRequest] model.
     *
     * Saves results and requests to local storage.
     *
     * @param data The [SalesRequest] containing criteria for property search.
     * @return A list of [Property] objects representing properties available for sale.
     */
    suspend fun getPropertiesForSale(
        data: SalesRequest,
        page: Int? = null,
        sort: String? = null
    ): List<Property>

    /**
     * Retrieves the last 3 search history requests for sale properties.
     *
     * @return A list of [SalesRequest] objects representing the search history for sale properties.
     */

    fun getLastSaleRequestsHistory(): Flow<List<SalesRequest>>

    /**
     * Retrieves all search history requests for sale properties.
     *
     * @return A list of [SalesRequest] objects representing all search history for sale properties.
     */

    suspend fun getSaleRequestsHistory(): List<SalesRequest>

    /**
     * Refreshers search history requests for sale properties.
     *
     */
    suspend fun updateRequestHistory()

    /**
     * Retrieves list of properties based on the provided [SalesRequest] model.
     *
     * @return A list of [Property] objects representing all sale properties.
     */

    suspend fun getByQuery(query: SalesRequest): List<Property>

    /**
     * Retrieves detailed information about a specific property based on its unique identifier.
     *
     * This function fetches comprehensive details about a property, such as its specifications,
     * amenities, location details, and pricing information.
     *
     * @param id The unique identifier of the property to retrieve details for.
     * @return A [PropertyDetails] object containing detailed information about the specified property.
     */
    suspend fun getPropertyDetails(id: String): PropertyDetails
}