package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType.*

object Constants {

    const val BASE_URL = "https://realtor.p.rapidapi.com/"

    val housesList = listOf(
        SINGLE_FAMILY,
        DUPLEX,
        MULTI_FAMILY,
        LAND,
        TOWNHOMES,
        DUPLEX,
        MOBILE,
        CONDOS,
        CONDO_TOWNHOME_ROWHOME_COOP,
        CONDO_TOWNHOME,
        FARM
    )
}