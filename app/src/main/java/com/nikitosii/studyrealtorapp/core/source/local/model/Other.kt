package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.property.OtherResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Other(
    val listingId: String? = null,
    val listingKey: String? = null,
    val primary: Boolean? = null,
    val status: String? = null
) : Parcelable {

    companion object {
        fun from(data: OtherResponseApi?): Other = Other(
            listingId = data?.listingId,
            listingKey = data?.listingKey,
            primary = data?.primary,
            status = data?.status
        )
    }
}