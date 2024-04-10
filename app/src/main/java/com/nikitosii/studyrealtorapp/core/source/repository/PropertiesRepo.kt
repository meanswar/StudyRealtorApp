package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest

interface PropertiesRepo {

    /**
     * Retrieves properties available for sale based on the provided [SalesRequest] model.
     *
     * @param data The [SalesRequest] containing criteria for property search.
     * @return A list of [Property] objects representing properties available for sale.
     */
    suspend fun getPropertiesForSale(data: SalesRequest, page: Int = 0, sort: String? = null): List<Property>
}