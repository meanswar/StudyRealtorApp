package com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class ReadyConnectMortgageResponseApi(
    @Json(name = "show_contact_a_lender")
    val showContactLender: Boolean? = null,
    @Json(name = "show_veterans_united")
    val showVeteransUnited: Boolean? = null
)