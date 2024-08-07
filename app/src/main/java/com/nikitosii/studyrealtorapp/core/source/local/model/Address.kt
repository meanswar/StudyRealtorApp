package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.AddressResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Address(
    val city: String? = null,
    val coordinate: Coordinate? = null,
    val line: String? = null,
    val postalCode: String? = null,
    val state: String? = null,
    val stateCode: String? = null
) : Parcelable {
    companion object {
        fun from(data: AddressResponseApi?): Address = Address(
            data?.city,
            Coordinate.from(data?.coordinate),
            data?.line,
            data?.postalCode,
            data?.state,
            data?.stateCode
        )
    }
}