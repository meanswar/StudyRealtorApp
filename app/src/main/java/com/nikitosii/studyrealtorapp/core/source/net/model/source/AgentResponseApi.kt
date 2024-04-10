package com.nikitosii.studyrealtorapp.core.source.net.model.source

import com.squareup.moshi.Json

data class AgentResponseApi(
    @Json(name = "office_name")
    val officeName: String? = null
)