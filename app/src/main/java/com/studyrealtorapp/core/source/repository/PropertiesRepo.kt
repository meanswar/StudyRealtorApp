package com.studyrealtorapp.core.source.repository

import com.studyrealtorapp.core.source.local.model.Property

interface PropertiesRepo {
    /*
    * Returns list of properties for sale
    *
    *
    */
    suspend fun getPropertiesForSale(data: String): List<Property>
}