package com.nikitosii.studyrealtorapp.core.source.net.model.location.address

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CoordinateApi(
    val lat: Double? = null,
    val lon: Double? = null
)