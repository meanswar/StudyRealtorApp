package com.studyrealtorapp.core.source.net.model.source

import com.google.gson.annotations.SerializedName

data class SourceResponseApi(
    val agents: List<AgentResponseApi>? = null,
    val id: String? = null,
    @SerializedName("plan_id")
    val planId: String? = null,
    @SerializedName("spec_id")
    val specId: String? = null,
    val type: String? = null,
)