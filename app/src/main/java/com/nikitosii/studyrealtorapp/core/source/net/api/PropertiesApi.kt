package com.nikitosii.studyrealtorapp.core.source.net.api

import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
import retrofit2.http.Body
import retrofit2.http.GET

interface PropertiesApi {
    @GET
    fun getPropertiesForSale(@Body data: String): BaseResponseApi<PropertyResponseApi>
}