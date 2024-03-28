package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.flags.FlagsResponseApi

data class Flags(
    val isComingSoon: Boolean? = null,
    val isContingent: Boolean? = null,
    val isForeclosure: Boolean? = null,
    val isNewConstruction: Boolean? = null,
    val isNewListing: Boolean? = null,
    val isPending: Boolean? = null,
    val isPlan: Boolean? = null,
    val isPriceReduced: Boolean? = null,
    val isSubdivision: Boolean? = null
) {
    companion object {
        fun from(data: FlagsResponseApi?): Flags = Flags(
            isComingSoon = data?.isComingSoon,
            isContingent = data?.isContingent,
            isForeclosure = data?.isForeclosure,
            isNewConstruction = data?.isNewConstruction,
            isNewListing = data?.isNewListing,
            isPending = data?.isPending,
            isPlan = data?.isPlan,
            isPriceReduced = data?.isPriceReduced,
            isSubdivision = data?.isSubdivision
        )
    }
}