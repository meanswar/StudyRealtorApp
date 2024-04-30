package com.nikitosii.studyrealtorapp.core.source.net.model.school

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SchoolResponseApi(
    val assigned: Boolean? = null,
    @Json(name = "distance_in_miles")
    val distance: Float? = null,
    @Json(name = "education_levels")
    val educationLevels: List<String>? = null,
    @Json(name = "funding_type")
    val fundingType: String? = null,
    val name: String? = null
)