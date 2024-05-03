package com.nikitosii.studyrealtorapp.core.source.net.model.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AgentResponseApi(
    @Json(name = "office_name")
    val officeName: String? = null
)