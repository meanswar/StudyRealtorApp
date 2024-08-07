package com.nikitosii.studyrealtorapp.core.source.net.model.image

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ImageResponseApi(
    @Json(name = "urls")
    val url: UrlsResponseApi? = null
)