package com.studyrealtorapp.core.source.net.model.leadattributes

import com.google.gson.annotations.SerializedName

data class LeadAttributesApi(
    @SerializedName("lead_type")
    val leadType: String? = null,
    @SerializedName("opcity_lead_attributes")
    val opcity_parameters: OpcityLeadParametersApi? = null,
    @SerializedName("ready_connect_mortgage")
    val readyConnectMortgage: ReadyConnectMortgageApi? = null,
    @SerializedName("show_contact_an_agent")
    val showContactAgent: Boolean? = null
)