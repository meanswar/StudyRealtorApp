package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.vrtour.VrTourResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class VrTour(
    val href: String?,
    val type: String?
): Parcelable {
    companion object {
        fun from(data: VrTourResponseApi?): VrTour = VrTour(
            data?.href,
            data?.type
        )
    }
}