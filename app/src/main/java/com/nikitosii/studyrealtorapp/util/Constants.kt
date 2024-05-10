package com.nikitosii.studyrealtorapp.util

import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType.*
import kotlinx.coroutines.flow.SharedFlow

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
    val tokensList = listOf(
        "f637db3d56msh5acef294d57040ep1c51a6jsn44a9d2611afe",
        "30f60d7805mshbdcf3d7600b490bp18d006jsnb4dc8994134e",
        "e35661dae9msh3864476f9185d76p1241b4jsnec9b7dc7f32f",
        "293285464bmshbc693a804c0eec4p1bd051jsna4df242d9d06",
        "0a82a2bf4dmsh014e001d009ea20p19f58ejsn537f184943af",
        "e6c765de7bmshfa2b0ca40c83ea1p188000jsnccf09040ffed"
    )
}

typealias Flow<T> = SharedFlow<Status<T>>