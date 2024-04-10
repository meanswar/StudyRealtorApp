package com.nikitosii.studyrealtorapp.core.source.local.model.request

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
) {
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