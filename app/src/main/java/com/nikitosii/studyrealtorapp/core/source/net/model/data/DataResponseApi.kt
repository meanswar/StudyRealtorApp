package com.nikitosii.studyrealtorapp.core.source.net.model.data

import com.nikitosii.studyrealtorapp.core.source.local.PropertyType
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class DataResponseApi(
    val name: String? = null,
    val photo: String? = null,
    val type: String? = null
)