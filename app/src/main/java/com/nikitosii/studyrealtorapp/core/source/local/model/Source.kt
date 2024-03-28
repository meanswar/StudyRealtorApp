package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.local.model.Agent
import com.nikitosii.studyrealtorapp.core.source.net.model.source.SourceResponseApi

data class Source(
    val agents: List<Agent>? = null,
    val id: String? = null,
    val planId: String? = null,
    val specId: String? = null,
    val type: String? = null,
) {

    companion object {
        fun from(data: SourceResponseApi?): Source = Source(
            agents = data?.agents?.map { Agent.from(it) },
            id = data?.id,
            planId = data?.planId,
            specId = data?.specId,
            type = data?.type
        )
    }
}