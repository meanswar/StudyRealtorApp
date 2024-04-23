package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.commnunity.CommunityDescriptionResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class CommunityDescription(
    val name: String? = null
): Parcelable {
    companion object {
        fun from(response: CommunityDescriptionResponseApi?) = CommunityDescription(
            name = response?.name
        )
    }
}