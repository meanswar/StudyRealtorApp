package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes.LeadAttributesResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class LeadAttributes(
    val leadType: String? = null,
    val opCityParameters: OpCityLeadParameters? = null,
    val readyConnectMortgage: ReadyConnectMortgage? = null,
    val showContactAgent: Boolean? = null
): Parcelable {
    companion object {
        fun from(data: LeadAttributesResponseApi?): LeadAttributes = LeadAttributes(
            leadType = data?.leadType,
            opCityParameters = OpCityLeadParameters.from(data?.opcityParameters),
            readyConnectMortgage = ReadyConnectMortgage.from(data?.readyConnectMortgage),
            showContactAgent = data?.showContactAgent
        )
    }
}