package com.nikitosii.studyrealtorapp.core.source.net.model.data

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class BrandingResponseApi(
    val name: String? = null,
    val photo: String? = null,
    val type: String? = null
)