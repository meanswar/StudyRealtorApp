package com.nikitosii.studyrealtorapp.core.source.net.model.base

import com.nikitosii.studyrealtorapp.core.source.net.model.property.PropertyDetailsResponseApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseSinglePropertyResponseApi(
    @Json(name = "home")
    val result: PropertyDetailsResponseApi
)