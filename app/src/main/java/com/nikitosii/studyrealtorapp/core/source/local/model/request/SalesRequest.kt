package com.nikitosii.studyrealtorapp.core.source.local.model.request

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SalesRequest(
    val address: String? = null,
    val houses: List<String>? = null,
    val priceMin: Int? = null,
    val priceMax: Int? = null,
    val bedsMin: Int? = null,
    val bedsMax: Int? = null,
    val bathsMin: Int? = null,
    val bathsMax: Int? = null,
    val sqftMin: Int? = null,
    val sqftMax: Int? = null
): Parcelable {
    companion object {
        fun create(
            address: String?,
            houses: List<String>?,
            priceMin: Int?,
            priceMax: Int?,
            bedsMin: Int?,
            bedsMax: Int?,
            bathsMin: Int?,
            bathsMax: Int?,
            sqftMin: Int?,
            sqftMax: Int?
        ): SalesRequest = SalesRequest(
            address,
            houses,
            priceMin,
            priceMax,
            bedsMin,
            bedsMax,
            bathsMin,
            bathsMax,
            sqftMin,
            sqftMax
        )
    }
}