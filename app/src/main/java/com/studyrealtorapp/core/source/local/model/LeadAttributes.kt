package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.leadattributes.LeadAttributesResponseApi

data class LeadAttributes(
    val leadType: String? = null,
    val opCityParameters: OpCityLeadParameters? = null,
    val readyConnectMortgage: ReadyConnectMortgage? = null,
    val showContactAgent: Boolean? = null
) {
    companion object {
        fun from(data: LeadAttributesResponseApi?): LeadAttributes = LeadAttributes(
            leadType = data?.leadType,
            opCityParameters = OpCityLeadParameters.from(data?.opcityParameters),
            readyConnectMortgage = ReadyConnectMortgage.from(data?.readyConnectMortgage),
            showContactAgent = data?.showContactAgent
        )
    }
}