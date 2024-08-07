package com.nikitosii.studyrealtorapp.core.source.net.model.description

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DetailsResponseApi(
    val category: String? = null,
    @Json(name = "parent_category")
    val parentCategory: String? = null,
    val text: List<String>? = null
)