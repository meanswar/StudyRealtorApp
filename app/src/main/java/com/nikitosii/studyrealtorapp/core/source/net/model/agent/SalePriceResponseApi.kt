package com.nikitosii.studyrealtorapp.core.source.net.model.agent

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SalePriceResponseApi(
    val min: Int,
    val max: Int,
    @Json(name = "last_listing_date")
    val lastListingDate: String? = null
)