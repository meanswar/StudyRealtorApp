package com.nikitosii.studyrealtorapp.core.domain.repository.impl

import com.nikitosii.studyrealtorapp.core.source.db.dao.ImageDataDao
import com.nikitosii.studyrealtorapp.core.source.db.entity.ImageDataEntity
import com.nikitosii.studyrealtorapp.core.source.local.model.image.ImageData
import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.image.ImageApi
import com.nikitosii.studyrealtorapp.core.domain.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.domain.repository.base.BaseRepo
import javax.inject.Inject

class ImageRepoImpl @Inject constructor(
    private val api: ImageApi,
    private val dao: ImageDataDao,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), ImageRepo {

    override suspend fun getImage(query: String): ImageData = runWithErrorHandler {
        val url = api.getPhoto(query).results.firstOrNull()?.url?.regular
        ImageData(query, url)
    }

    override suspend fun insertImageData(data: ImageData) {
        dao.insertImageData(ImageDataEntity.from(data))
    }

    override suspend fun getLocalImage(id: String): ImageData? = ImageData.from(dao.getImage(id))

    override suspend fun removeAll() { dao.deleteAll() }
}