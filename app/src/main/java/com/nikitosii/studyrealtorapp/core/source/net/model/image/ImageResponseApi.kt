package com.nikitosii.studyrealtorapp.core.source.net.model.image

import com.nikitosii.studyrealtorapp.core.source.local.model.image.Urls
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ImageResponseApi(
    @Json(name = "urls.regular")
    val url: UrlsResponseApi? = null
)