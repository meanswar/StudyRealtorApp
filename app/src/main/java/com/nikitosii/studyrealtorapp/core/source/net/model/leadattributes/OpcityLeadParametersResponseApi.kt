package com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class OpcityLeadParametersResponseApi(
    @Json(name = "cashback_enbaled")
    val cashbackEnabled: Boolean? = null,
    @Json(name = "flip_the_market_enabled")
    val flipMarketEnabled: Boolean? = null
)