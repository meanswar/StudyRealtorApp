package com.nikitosii.studyrealtorapp.core.source.local.model.property_details

import com.nikitosii.studyrealtorapp.core.source.net.model.school.EducationFacilitiesResponseApi

data class EducationFacilities(
    val schools: List<School>? = null,
    val total: Int? = null,
) {
    companion object {
        fun from(response: EducationFacilitiesResponseApi?) = EducationFacilities(
            schools = response?.schools?.map { School.from(it) },
            total = response?.total
        )
    }
}