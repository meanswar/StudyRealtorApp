package com.nikitosii.studyrealtorapp.core.source.net.model.location

import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CountyResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailedLocationResponseApi(
    val address: AddressResponseApi? = null,
    val country: String? = null,
    val line: String? = null,
    val state: String? = null,
    @Json(name = "state_code")
    val stateCode: String? = null,
    val county: CountyResponseApi? = null
)