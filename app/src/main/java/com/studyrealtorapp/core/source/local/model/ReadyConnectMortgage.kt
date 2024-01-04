package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.leadattributes.ReadyConnectMortgageResponseApi

data class ReadyConnectMortgage(
    val showContactLender: Boolean? = null,
    val showVeteransUnited: Boolean? = null
) {
    companion object {
        fun from(data: ReadyConnectMortgageResponseApi?): ReadyConnectMortgage = ReadyConnectMortgage(
            data?.showContactLender,
            data?.showVeteransUnited
        )
    }
}