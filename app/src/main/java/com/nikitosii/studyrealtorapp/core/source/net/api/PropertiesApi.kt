package com.nikitosii.studyrealtorapp.core.source.net.api

import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseHomeSearch
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponse
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseSinglePropertyResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
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
        @Query("property_id") id: String
    ): BaseSinglePropertyResponseApi

    @GET("/forrent")
    suspend fun getPropertiesForRent(
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
        @Query("cats") cats: Boolean? = null,
        @Query("dogs") dogs: Boolean? = null,
        @Query("page") page: Int? = null,
        @Query("sort") sort: String? = null
    ): BaseHomeSearch<PropertyResponseApi>
}