package com.studyrealtorapp.core.source.local.model

import com.studyrealtorapp.core.source.net.model.product.ProductResponseApi

data class Product(
    val brandName: String? = null,
    val product: List<String>? = null
) {

    companion object {
        fun from(data: ProductResponseApi?): Product = Product(
            data?.brandName,
            data?.product
        )
    }
}