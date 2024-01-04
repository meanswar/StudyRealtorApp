package com.studyrealtorapp.core.source.net.model.leadattributes

import com.google.gson.annotations.SerializedName

data class OpcityLeadParametersResponseApi(
    @SerializedName("cashback_enbaled")
    val cashbackEnabled: Boolean? = null,
    @SerializedName("flip_the_market_enabled")
    val flipMarketEnabled: Boolean? = null
)