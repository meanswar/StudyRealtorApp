package com.studyrealtorapp.core.source.net.model.source

import com.google.gson.annotations.SerializedName

data class AgentApi(
    @SerializedName("office_name")
    val officeName: String? = null
)