package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.source.AgentResponseApi

data class Agent(val officeName: String? = null) {
    companion object {
        fun from(data: AgentResponseApi?): Agent = Agent(data?.officeName)
    }
}