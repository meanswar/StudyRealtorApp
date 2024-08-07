package com.nikitosii.studyrealtorapp.core.source.net.model.source

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SourceResponseApi(
    val agents: List<AgentResponseApi>? = null,
    val id: String? = null,
    @Json(name = "plan_id")
    val planId: String? = null,
    @Json(name = "spec_id")
    val specId: String? = null,
    val type: String? = null,
)