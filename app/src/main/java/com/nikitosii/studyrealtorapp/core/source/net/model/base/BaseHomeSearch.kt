package com.nikitosii.studyrealtorapp.core.source.net.model.base

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseHomeSearch<T>(
    @Json(name = "results")
    val results: List<T>,
    @Json(name = "count")
    val count: Int
)