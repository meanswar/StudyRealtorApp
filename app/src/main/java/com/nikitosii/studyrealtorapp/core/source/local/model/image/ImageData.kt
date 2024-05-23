package com.nikitosii.studyrealtorapp.core.source.local.model.image

import com.nikitosii.studyrealtorapp.core.source.db.entity.ImageDataEntity

data class ImageData(
    val request: String,
    val url: String?
) {
    companion object {
        fun from(data: ImageDataEntity?): ImageData? = data?.let { ImageData(it.id, it.url) }
    }
}