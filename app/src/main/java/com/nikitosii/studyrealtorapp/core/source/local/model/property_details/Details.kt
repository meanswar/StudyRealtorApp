package com.nikitosii.studyrealtorapp.core.source.local.model.property_details

import com.nikitosii.studyrealtorapp.core.source.net.model.description.DetailsResponseApi

data class Details(
    val category: String? = null,
    val parentCategory: String? = null,
    val text: List<String>? = null
) {
    companion object {
        fun from(source: DetailsResponseApi?) = Details(
            source?.category,
            source?.parentCategory,
            source?.text
        )
    }
}