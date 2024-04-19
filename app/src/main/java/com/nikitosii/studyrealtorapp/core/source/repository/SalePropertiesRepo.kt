package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.db.entity.SalePropertiesSearchEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.util.Flow

interface SalePropertiesRepo {

    /**
     * Retrieves properties available for sale based on the provided [SalesRequest] model.
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
     * Retrieves the search history for sale properties.
     *
     * @return A list of [SalesRequest] objects representing the search history for sale properties.
     */

    fun getSaleRequestsHistory(): Flow<List<SalesRequest>>

    suspend fun updateRequestHistory()

    suspend fun getByQuery(query: SalesRequest): List<Property>
}