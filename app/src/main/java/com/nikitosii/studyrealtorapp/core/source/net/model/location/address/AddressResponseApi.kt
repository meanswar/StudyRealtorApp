package com.nikitosii.studyrealtorapp.core.source.net.model.location.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class AddressResponseApi(
    @Json(name = "city")
    val city: String? = null,
    @Json(name = "coordinate")
    val coordinate: CoordinateApi? = null,
    @Json(name = "line")
    val line: String? = null,
    @Json(name = "postal_code")
    val postalCode: String? = null,
    @Json(name = "state")
    val state: String? = null,
    @Json(name = "state_code")
    val stateCode: String? = null
)