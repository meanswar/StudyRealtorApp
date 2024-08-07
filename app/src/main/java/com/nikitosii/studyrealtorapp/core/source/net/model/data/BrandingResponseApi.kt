package com.nikitosii.studyrealtorapp.core.source.net.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BrandingResponseApi(
    val name: String? = null,
    val photo: String? = null,
    val type: String? = null,
    @Json(name = "accent_color")
    val accentColor: String? = null,
    val phone: String? = null,
)