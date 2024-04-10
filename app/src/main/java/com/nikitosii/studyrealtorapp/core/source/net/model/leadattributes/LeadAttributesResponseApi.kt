package com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class LeadAttributesResponseApi(
    @Json(name = "lead_type")
    val leadType: String? = null,
    @Json(name = "opcity_lead_attributes")
    val opcityParameters: OpcityLeadParametersResponseApi? = null,
    @Json(name = "ready_connect_mortgage")
    val readyConnectMortgage: ReadyConnectMortgageResponseApi? = null,
    @Json(name = "show_contact_an_agent")
    val showContactAgent: Boolean? = null
)