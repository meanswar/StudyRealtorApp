package com.nikitosii.studyrealtorapp.core.source.local.model

import com.nikitosii.studyrealtorapp.core.source.net.model.photo.PhotoResponseApi

data class Photo(val url: String? = null) {
    companion object {
        fun from(data: PhotoResponseApi?): Photo = Photo(data?.url)
    }
}