package com.nikitosii.studyrealtorapp.core.source.net.model.description

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DescriptionResponseApi(
    val baths: Int? = null,
    @Json(name = "baths_1qtr")
    val bathsGtr1: Int? = null,
    @Json(name = "baths_3qtr")
    val bathsGtr3: Int? = null,
    @Json(name = "baths_full")
    val bathsFull: Int? = null,
    @Json(name = "baths_half")
    val bathsHalf: Int? = null,
    val beds: Int? = null,
    val garage: Int? = null,
    @Json(name = "lot_sqft")
    val lotSqft: Int? = null,
    val name: String? = null,
    val sqft: Int? = null,
    val pool: String? = null,
    val text: String? = null,
    val type: String? = null,
    val rooms: Int? = null
)