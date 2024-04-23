package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CoordinateApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Coordinate(
    val latitude: Double,
    val longitude: Double
): Parcelable {
    companion object {
        fun from(data: CoordinateApi?): Coordinate = Coordinate(
            data?.lat ?: 0.0,
            data?.lon ?: 0.0
        )
    }
}