package com.nikitosii.studyrealtorapp.core.source.repository

import com.nikitosii.studyrealtorapp.core.source.local.model.image.ImageData

interface ImageRepo {

    suspend fun getImage(query: String): ImageData

    suspend fun insertImageData(data: ImageData)

    suspend fun getLocalImage(id: String): ImageData?

    suspend fun removeAll()
}