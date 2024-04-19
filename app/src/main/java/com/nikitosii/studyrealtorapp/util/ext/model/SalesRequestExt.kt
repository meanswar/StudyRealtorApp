package com.nikitosii.studyrealtorapp.util.ext.model

import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest

fun SalesRequest.request(): String {
    val request = StringBuilder()
    address?.let { request.append("location=$it") }
    houses?.let { request.append("&house=${it.joinToString(",")}") }
    priceMin?.let { request.append("&price_min=$it") }
    priceMax?.let { request.append("&price_max=$it") }
    bedsMin?.let { request.append("&beds_min=$it") }
    bedsMax?.let { request.append("&beds_max=$it") }
    bathsMin?.let { request.append("&baths_min=$it") }
    bathsMax?.let { request.append("&baths_max=$it") }
    sqftMin?.let { request.append("&sqft_min=$it") }
    sqftMax?.let { request.append("&sqft_max=$it") }
    return request.toString()
}

fun SalesRequest.getFiltersCount(): Int? {
    var count = 0
    houses?.let { if (it.size > 0) count++ }
    priceMin?.let { count++ }
    bedsMax?.let { count++ }
    bathsMin?.let { count++ }
    bathsMax?.let { count++ }
    sqftMin?.let { count++ }
    sqftMax?.let { count++ }
    return if (count > 0) count else null
}