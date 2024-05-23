package com.nikitosii.studyrealtorapp.core.source.local.model.agent

import com.nikitosii.studyrealtorapp.core.source.net.model.agent.AreaResponseApi
import com.squareup.moshi.Json

data class Area(
    val name: String,
    @Json(name = "state_code")
    val stateCode: String,
    val imageUrl: String? = null
) {
    companion object {

        fun from(marketingArea: AreaResponseApi) = Area(
            name = marketingArea.name,
            stateCode = marketingArea.stateCode
        )
    }
}