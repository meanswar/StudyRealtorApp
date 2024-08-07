package com.nikitosii.studyrealtorapp.core.source.local.model.agent

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.agent.ReviewResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Review(
    val author: String?,
    val description: String?,
    val ratingValue: Int?
): Parcelable {
    companion object {
        fun from(data: ReviewResponseApi): Review = Review(
            data.author,
            data.description,
            data.ratingValue
        )
    }
}