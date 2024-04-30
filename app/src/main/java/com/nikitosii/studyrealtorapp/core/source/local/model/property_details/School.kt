package com.nikitosii.studyrealtorapp.core.source.local.model.property_details

import com.nikitosii.studyrealtorapp.core.source.net.model.school.SchoolResponseApi

data class School(
    val assigned: Boolean? = null,
    val distance: Float? = null,
    val educationLevels: List<String>? = null,
    val fundingType: String? = null,
    val name: String? = null
) {
    companion object {
        fun from(source: SchoolResponseApi?) = School(
            source?.assigned,
            source?.distance,
            source?.educationLevels,
            source?.fundingType,
            source?.name
        )
    }
}