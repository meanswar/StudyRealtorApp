package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes.OpcityLeadParametersResponseApi

data class OpCityLeadParameters(
    val cashbackEnabled: Boolean? = null,
    val flipMarketEnabled: Boolean? = null
) {
    companion object {
        fun from(data: OpcityLeadParametersResponseApi?): OpCityLeadParameters = OpCityLeadParameters(
            data?.cashbackEnabled,
            data?.flipMarketEnabled
        )
    }
}