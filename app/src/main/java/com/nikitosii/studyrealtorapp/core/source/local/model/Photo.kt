package com.nikitosii.studyrealtorapp.core.source.local.model

import android.os.Parcelable
import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(val url: String? = null) : Parcelable {
    companion object {
        fun from(data: PhotoResponseApi?): Photo = Photo(data?.url)
    }
}