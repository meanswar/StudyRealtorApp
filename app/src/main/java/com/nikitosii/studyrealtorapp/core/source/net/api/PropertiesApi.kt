package com.nikitosii.studyrealtorapp.core.source.net.api

import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PropertiesApi {

    @GET("/forsale")
    suspend fun getPropertiesForSale(
        @Query("location") location: String? = null,
        @Query("type") house: String? = null,
        @Query("list_price-min") priceMin: Int? = null,
        @Query("list_price-max") priceMax: Int? = null,
        @Query("beds-min") bedsMin: Int? = null,
        @Query("beds-max") bedsMax: Int? = null,
        @Query("baths-min") bathsMin: Int? = null,
        @Query("baths-max") bathsMax: Int? = null,
        @Query("sqft-min") sqftMin: Int? = null,
        @Query("sqft-max") sqftMax: Int? = null,
        @Query("page") page: Int? = null,
        @Query("sort") sort: String? = null
    ): BaseResponse

    @GET("/property")
    suspend fun getPropertyDetails(
        @Query("id") id: Int
    ): BaseResponse
}