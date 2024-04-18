package com.nikitosii.studyrealtorapp.core.source.net.model.commnunity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommunityDescriptionResponseApi(
    @Json(name = "name")
    val name: String? = null
)