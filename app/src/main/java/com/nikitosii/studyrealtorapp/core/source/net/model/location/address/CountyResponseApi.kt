package com.nikitosii.studyrealtorapp.core.source.net.model.location.address

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class CountyResponseApi(
    @Json(name = "fips_code")
    val fipsCode: String? = null,
    val name: String? = null
)