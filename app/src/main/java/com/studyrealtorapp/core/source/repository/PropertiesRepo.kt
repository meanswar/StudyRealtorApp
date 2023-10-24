package com.studyrealtorapp.core.source.repository

import com.studyrealtorapp.core.source.net.model.property.PropertyResponseApi

interface PropertiesRepo {
    /*
    * Returns list of properties for sale
    *
    *
     */
    suspend fun getPropertiesForSale(data: String): List<PropertyResponseApi>
}