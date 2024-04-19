package com.nikitosii.studyrealtorapp.core.source.local.model

enum class HouseType(val type: String, val apiType: String) {
    SINGLE_FAMILY("Single Family", "single_family"),
    MULTI_FAMILY("Multi Family", "multi_family"),
    LAND("Land", "land"),
    TOWNHOMES("Townhomes", "townhomes"),
    DUPLEX("Duplex/Triplex", "duplex_triplex"),
    MOBILE("Mobile", "mobile"),
    CONDOS("Condos", "condos"),
    CONDO_TOWNHOME_ROWHOME_COOP("Con/Rowhome/Co-op", "condo_townhome_rowhome_coop"),
    CONDO_TOWNHOME("Condo/Townhome", "condo_townhome"),
    FARM("Farm", "farm")
}