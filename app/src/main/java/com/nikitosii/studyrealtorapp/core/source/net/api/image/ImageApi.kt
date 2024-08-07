package com.nikitosii.studyrealtorapp.core.source.net.api.image

import com.nikitosii.studyrealtorapp.core.source.net.model.base.BaseResponseApi
import com.nikitosii.studyrealtorapp.core.source.net.model.image.ImageResponseApi
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {

    @GET("/search/photos")
    suspend fun getPhoto(
        @Query("query") query: String,
        @Query("per_page") perPage: Int = 1,
        @Query("orientation") orientation: String = ORIENTATION_LANDSCAPE,
    ): BaseResponseApi<ImageResponseApi>

    companion object {
        private const val ORIENTATION_LANDSCAPE = "landscape"
    }
}