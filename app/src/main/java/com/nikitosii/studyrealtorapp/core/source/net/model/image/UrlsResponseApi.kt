package com.nikitosii.studyrealtorapp.core.source.net.model.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class UrlsResponseApi(
    @Json(name = "regular")
    val regular: String? = null
)