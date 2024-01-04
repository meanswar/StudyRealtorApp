package com.studyrealtorapp.core.source.net

import android.content.Context
import com.studyrealtorapp.core.source.local.LocalStorage
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val storage: LocalStorage
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = storage.getToken()
        val response = if (token.isNullOrBlank()) {
            chain.proceed(chain.request())
        }
        else {
            chain.proceed(chain.requestWithToken(token))
        }
        return response
    }

    private fun Interceptor.Chain.requestWithToken(token: String = KEY_TOKEN) = request()
        .newBuilder()
        .addHeader(TOKEN_HEADER, token)
        .addHeader(HOST_HEADER, KEY_HOST)
        .build()

    companion object {
        private const val KEY_TOKEN = "f637db3d56msh5acef294d57040ep1c51a6jsn44a9d2611afe"
        private const val TOKEN_HEADER = "X-RapidAPI-Key"
        private const val KEY_HOST = "realtor16.p.rapidapi.com"
        private const val HOST_HEADER = "X-RapidAPI-Host"
    }
}