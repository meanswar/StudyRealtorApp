package com.nikitosii.studyrealtorapp.util.ext

import android.content.res.TypedArray
import androidx.annotation.StyleableRes

fun String.getStringOrNull(): String? = if (this.isNotNull()) this else null

fun TypedArray.getStringOrNull(@StyleableRes index: Int) =
    if (hasValue(index)) getString(index) else null

fun String.toInteger(): Int? = if (this.isEmpty()) null else this.toInt()

//fun String.formatPrice(): String {
//    // Remove existing spaces and commas from the price string
//    val priceWithoutSpaces = this.replace("[ ,]".toRegex(), "")
//
//    // Insert spaces between groups of three digits from the end
//    val formattedPrice = StringBuilder(priceWithoutSpaces)
//    var index = formattedPrice.length - 3
//    while (index > 0) {
//        formattedPrice.insert(index, ' ')
//        index -= 3
//    }
//
//    return formattedPrice.toString()
//}

fun String.formatPrice(): String {
    // Remove existing spaces and commas from the price string
    val price = this.toFloat()
    return when {
        price / 1000000000 > 0.1f -> String.format("%.1fB", price / 1000000000)
        price / 1000000 > 0.1f -> String.format("%.1fM", price / 1000000)
        price / 1000 > 0.1f -> String.format("%.1fk", price / 1000)
        else -> String.format("%f", price)
    }
}

fun String?.ifNull(default: String?): String? = this ?: default

fun String?.ifNullOrEmpty(default: String?): String? = if (isNullOrEmpty()) default else this


