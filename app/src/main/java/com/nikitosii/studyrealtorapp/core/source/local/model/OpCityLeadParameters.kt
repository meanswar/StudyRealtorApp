package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.leadattributes.OpcityLeadParametersResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class OpCityLeadParameters(
    val cashbackEnabled: Boolean? = null,
    val flipMarketEnabled: Boolean? = null
): Parcelable {
    companion object {
        fun from(data: OpcityLeadParametersResponseApi?): OpCityLeadParameters = OpCityLeadParameters(
            data?.cashbackEnabled,
            data?.flipMarketEnabled
        )
    }
}