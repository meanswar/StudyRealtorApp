package com.studyrealtorapp.core.source.net.model.source

import com.google.gson.annotations.SerializedName

data class AgentResponseApi(
    @SerializedName("office_name")
    val officeName: String? = null
)