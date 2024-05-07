package com.nikitosii.studyrealtorapp.core.source.local.model

import com.squareup.moshi.Json

enum class HouseType(val type: String, val apiType: String) {
    @Json(name = "single_family")
    SINGLE_FAMILY("Single Family", "single_family"),
    @Json(name = "multi_family")
    MULTI_FAMILY("Multi Family", "multi_family"),
    @Json(name = "land")
    LAND("Land", "land"),
    @Json(name = "townhomes")
    TOWNHOMES("Townhomes", "townhomes"),
    @Json(name = "duplex_triplex")
    DUPLEX("Duplex/Triplex", "duplex_triplex"),
    @Json(name = "mobile")
    MOBILE("Mobile", "mobile"),
    @Json(name = "condos")
    CONDOS("Condos", "condos"),
    @Json(name = "condo_townhome_rowhome_coop")
    CONDO_TOWNHOME_ROWHOME_COOP("Con/Rowhome/Co-op", "condo_townhome_rowhome_coop"),
    @Json(name = "condo_townhome")
    CONDO_TOWNHOME("Condo/Townhome", "condo_townhome"),
    @Json(name = "apartment")
    APARTMENT("Apartment", "apartment"),
    @Json(name = "cottage")
    COTTAGE("Cottage", "cottage"),
    @Json(name = "farm")
    FARM("Farm", "farm")
}