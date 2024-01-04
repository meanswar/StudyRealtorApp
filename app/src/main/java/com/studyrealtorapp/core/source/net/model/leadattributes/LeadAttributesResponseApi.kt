package com.studyrealtorapp.core.source.net.model.leadattributes

import com.google.gson.annotations.SerializedName

data class LeadAttributesResponseApi(
    @SerializedName("lead_type")
    val leadType: String? = null,
    @SerializedName("opcity_lead_attributes")
    val opcityParameters: OpcityLeadParametersResponseApi? = null,
    @SerializedName("ready_connect_mortgage")
    val readyConnectMortgage: ReadyConnectMortgageResponseApi? = null,
    @SerializedName("show_contact_an_agent")
    val showContactAgent: Boolean? = null
)