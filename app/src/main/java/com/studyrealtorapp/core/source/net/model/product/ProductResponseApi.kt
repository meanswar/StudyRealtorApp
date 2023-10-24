package com.studyrealtorapp.core.source.net.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponseApi(
    @SerializedName("brand_name")
    val brandName: String? = null,
    val product: List<String>? = null
)