package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes.ReadyConnectMortgageResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class ReadyConnectMortgage(
    val showContactLender: Boolean? = null,
    val showVeteransUnited: Boolean? = null
): Parcelable {
    companion object {
        fun from(data: ReadyConnectMortgageResponseApi?): ReadyConnectMortgage = ReadyConnectMortgage(
            data?.showContactLender,
            data?.showVeteransUnited
        )
    }
}