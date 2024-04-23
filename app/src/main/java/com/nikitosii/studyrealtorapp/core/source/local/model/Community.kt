package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.commnunity.CommunityResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Community(
    val description: CommunityDescription? = null,
    val propertyId: String? = null
): Parcelable {
    companion object {
        fun from(response: CommunityResponseApi?) = Community(
            description = CommunityDescription.from(response?.description),
            propertyId = response?.propertyId
        )
    }
}