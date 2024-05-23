package com.nikitosii.studyrealtorapp.core.source.net.model.base

import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AgentDetailsResponseApi
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class BaseAgentDetailsResponse(

    val agentDetails: AgentDetailsResponseApi
)