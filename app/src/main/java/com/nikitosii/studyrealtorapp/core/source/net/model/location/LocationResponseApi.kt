package com.nikitosii.studyrealtorapp.core.source.net.model.location

import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CountyResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class LocationResponseApi(
    val address: AddressResponseApi? = null,
    val county: CountyResponseApi? = null,
    @Json(name = "street_view_url")
    val streetViewUrl: String? = null,
)