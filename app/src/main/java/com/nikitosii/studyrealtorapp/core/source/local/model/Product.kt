package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.product.ProductResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val brandName: String? = null,
    val product: List<String>? = null
) : Parcelable {

    companion object {
        fun from(data: ProductResponseApi?): Product = Product(
            data?.brandName,
            data?.product
        )
    }
}