package com.studyrealtorapp.core.source.net.model.leadattributes

import com.google.gson.annotations.SerializedName

data class ReadyConnectMortgageResponseApi(
    @SerializedName("show_contact_a_lender")
    val showContactLender: Boolean? = null,
    @SerializedName("show_veterans_united")
    val showVeteransUnited: Boolean? = null
)