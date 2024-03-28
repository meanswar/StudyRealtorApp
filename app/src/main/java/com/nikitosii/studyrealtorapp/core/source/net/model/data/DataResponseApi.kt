package com.nikitosii.studyrealtorapp.core.source.net.model.data

import com.nikitosii.studyrealtorapp.core.source.local.PropertyType

data class DataResponseApi(
    val name: String? = null,
    val photo: String? = null,
    val type: PropertyType? = null
) {
}