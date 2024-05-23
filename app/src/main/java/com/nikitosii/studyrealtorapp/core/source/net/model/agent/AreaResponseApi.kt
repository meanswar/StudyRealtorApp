package com.nikitosii.studyrealtorapp.core.source.net.model.agent

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AreaResponseApi(
    val name: String,
    @Json(name = "state_code")
    val stateCode: String
)