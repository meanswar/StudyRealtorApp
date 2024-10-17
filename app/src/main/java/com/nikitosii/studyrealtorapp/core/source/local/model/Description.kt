package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.description.DescriptionResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Description(
    val baths: Int? = null,
    val bathsGtr1: Int? = null,
    val bathsGtr3: Int? = null,
    val bathsFull: Int? = null,
    val bathsHalf: Int? = null,
    val beds: Int? = null,
    val garage: Int? = null,
    val lotSqft: Int? = null,
    val name: String? = null,
    val sqft: Int? = null,
    val type: HouseType? = null,
    val rooms: Int? = null,
    val pool: String? = null,
    val text: String? = null,
) : Parcelable {
    companion object {
        fun from(data: DescriptionResponseApi?): Description = Description(
            baths = data?.baths,
            bathsGtr1 = data?.bathsGtr1,
            bathsGtr3 = data?.bathsGtr3,
            bathsFull = data?.bathsFull,
            bathsHalf = data?.bathsHalf,
            beds = data?.beds,
            garage = data?.garage,
            lotSqft = data?.lotSqft,
            name = data?.name,
            sqft = data?.sqft,
            type = getType(data?.type),
            text = data?.text,
            pool = data?.pool,
            rooms = data?.rooms
        )

        private fun getType(text: String?): HouseType = when (text) {
            "single_family" -> HouseType.SINGLE_FAMILY
            "multi_family" -> HouseType.MULTI_FAMILY
            "condos" -> HouseType.CONDOS
            "townhomes" -> HouseType.TOWNHOMES
            "land" -> HouseType.LAND
            "duplex_triplex" -> HouseType.DUPLEX
            "mobile" -> HouseType.MOBILE
            "apartment" -> HouseType.APARTMENT
            "cottage" -> HouseType.COTTAGE
            "coop" -> HouseType.COOP
            "farms" -> HouseType.FARM
            else -> HouseType.OTHER
        }
    }
}