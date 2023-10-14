package com.studyrealtorapp.core.source.net.api

import retrofit2.http.Body
import retrofit2.http.GET

interface PropertiesApi {
    @GET
    fun getPropertiesForSale(@Body data: String)
}