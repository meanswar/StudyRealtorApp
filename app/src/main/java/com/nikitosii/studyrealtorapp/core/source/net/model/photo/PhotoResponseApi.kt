package com.nikitosii.studyrealtorapp.core.source.net.model.photo

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class PhotoResponseApi(
    @Json(name = "href")
    val url: String? = null
)