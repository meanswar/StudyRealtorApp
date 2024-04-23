package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.location.address.CountyResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class County(
    val fipsCode: String? = null,
    val name: String? = null
) : Parcelable {
    companion object {
        fun from(data: CountyResponseApi?): County = County(
            data?.fipsCode,
            data?.name
        )
    }
}