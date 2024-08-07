package com.nikitosii.studyrealtorapp.core.source.local.model.image

import com.nikitosii.studyrealtorapp.core.source.db.entity.ImageDataEntity
import com.nikitosii.studyrealtorapp.core.source.net.model.image.ImageResponseApi

data class Image(
    val urls: Urls
) {
    companion object {
        fun from(data: ImageResponseApi) = Image(Urls.from(data.url))
    }
}