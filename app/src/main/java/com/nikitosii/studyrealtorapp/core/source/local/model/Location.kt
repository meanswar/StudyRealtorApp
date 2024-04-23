package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.location.LocationResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Location(
    val address: Address? = null,
    val county: County? = null,
    val streetViewUrl: String? = null,
): Parcelable {
    companion object {
        fun from(data: LocationResponseApi?): Location = Location(
            Address.from(data?.address),
            County.from(data?.county),
            data?.streetViewUrl
        )
    }
}