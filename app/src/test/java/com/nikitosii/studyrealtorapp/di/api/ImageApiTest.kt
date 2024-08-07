package com.nikitosii.studyrealtorapp.di.api

import com.nikitosii.studyrealtorapp.util.TestConstants.PHOTO_VALID
import com.nikitosii.studyrealtorapp.core.source.net.api.image.ImageApi
import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.image.ImageResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.image.UrlsResponseApi
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ImageApiTest {

    @Provides
    @Singleton
    fun providesImageApi(): ImageApi = object : ImageApi {
        override suspend fun getPhoto(
            query: String,
            perPage: Int,
            orientation: String
        ): BaseResponseApi<ImageResponseApi> {
            return BaseResponseApi(
                listOf(
                    ImageResponseApi(
                        UrlsResponseApi(PHOTO_VALID)
                    )
                )
            )
        }
    }
}