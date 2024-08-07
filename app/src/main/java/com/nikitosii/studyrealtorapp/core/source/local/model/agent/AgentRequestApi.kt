package com.nikitosii.studyrealtorapp.core.source.local.model.agent

data class AgentRequestApi(
    val lang: String? = null,
    val price: String? = null,
    val photo: Boolean? = null,
    val rating: Int? = null,
    val agentName: String? = null,
    val location: String,
)