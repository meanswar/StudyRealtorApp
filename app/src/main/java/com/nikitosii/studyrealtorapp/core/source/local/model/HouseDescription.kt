package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.house.HouseDescriptionResponseAPI

data class HouseDescription(
    val description: String? = null,
    val dst: Boolean? = null,
    val endDate: String? = null,
    val methods: List<String>? = null,
    val startDate: String? = null,
    val timeZone: String? = null
) {
    companion object {
        fun from(api: HouseDescriptionResponseAPI?) = HouseDescription(
            description = api?.description,
            dst = api?.dst,
            endDate = api?.endDate,
            methods = api?.methods,
            startDate = api?.startDate,
            timeZone = api?.timeZone
        )
    }
}