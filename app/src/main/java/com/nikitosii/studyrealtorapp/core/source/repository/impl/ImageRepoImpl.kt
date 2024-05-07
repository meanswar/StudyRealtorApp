package com.nikitosii.studyrealtorapp.core.source.repository.impl

import com.nikitosii.studyrealtorapp.core.source.net.NetworkErrorHandler
import com.nikitosii.studyrealtorapp.core.source.net.api.image.ImageApi
import com.nikitosii.studyrealtorapp.core.source.repository.ImageRepo
import com.nikitosii.studyrealtorapp.core.source.repository.base.BaseRepo
import javax.inject.Inject

class ImageRepoImpl @Inject constructor(
    private val api: ImageApi,
    networkErrorHandler: NetworkErrorHandler
) : BaseRepo(networkErrorHandler), ImageRepo {

    override suspend fun getImage(query: String) =
        runWithErrorHandler { api.getPhoto(query).results.first().url?.regular }
}