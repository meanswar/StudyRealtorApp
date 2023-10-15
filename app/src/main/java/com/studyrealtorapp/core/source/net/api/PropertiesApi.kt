package com.studyrealtorapp.core.source.net.api

import com.studyrealtorapp.core.source.net.model.base.BaseResponseApi
import com.studyrealtorapp.core.source.net.model.property.PropertyApi
import retrofit2.http.Body
import retrofit2.http.GET

interface PropertiesApi {
    @GET
    fun getPropertiesForSale(@Body data: String): BaseResponseApi<PropertyApi>
}