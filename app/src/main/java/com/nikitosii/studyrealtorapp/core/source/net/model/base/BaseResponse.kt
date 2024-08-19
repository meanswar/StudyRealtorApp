package com.nikitosii.studyrealtorapp.core.source.net.model.base

import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseResponse(
    @Json(name = "home_search")
    val homeSearch: BaseHomeSearch<PropertyResponseApi>? = null
)