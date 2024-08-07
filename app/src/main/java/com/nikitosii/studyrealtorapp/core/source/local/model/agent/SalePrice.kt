package com.nikitosii.studyrealtorapp.core.source.local.model.agent

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.SalePriceResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class SalePrice(
    val min: Int?,
    val max: Int?,
    val lastListingDate: String?
): Parcelable {
    companion object {
        fun from(data: SalePriceResponseApi?): SalePrice = SalePrice(
            data?.min,
            data?.max,
            data?.lastListingDate
        )
    }
}