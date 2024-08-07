package com.nikitosii.studyrealtorapp.core.source.net.model.commnunity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityResponseApi(
    @Json(name = "description")
    val description: CommunityDescriptionResponseApi? = null,
    @Json(name = "property_id")
    val propertyId: String? = null
)