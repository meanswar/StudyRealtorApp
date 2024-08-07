package com.nikitosii.studyrealtorapp.core.source.net.model.property

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class OtherResponseApi(
    @Json(name = "listing_id")
    val listingId: String? = null,
    @Json(name = "listing_key")
    val listingKey: String? = null,
    val primary: Boolean? = null,
    val status: String? = null
)