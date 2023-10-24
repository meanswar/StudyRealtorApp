package com.studyrealtorapp.core.source.net.model.description

import com.google.gson.annotations.SerializedName

data class DescriptionResponseApi(
    val baths: Int? = null,
    @SerializedName(value = "baths_1qtr")
    val bathsGtr1: Int? = null,
    @SerializedName(value = "baths_3qtr")
    val bathsGtr3: Int? = null,
    @SerializedName(value = "baths_full")
    val bathsFull: Int? = null,
    @SerializedName(value = "baths_half")
    val bathsHalf: Int? = null,
    val beds: Int? = null,
    val garage: Int? = null,
    @SerializedName(value = "lot_sqft")
    val lotSqft: Int? = null,
    val name: String? = null,
    @SerializedName(value = "sold_date")
    val soldDate: String? = null,
    @SerializedName(value = "sold_price")
    val soldPrice: Int? = null,
    val sqft: Int? = null,
    val stories: Int? = null,
    @SerializedName(value = "sub_type")
    val subType: String? = null,
    val type: String? = null,
    @SerializedName(value = "year_built")
    val yearBuilt: Int? = null
)