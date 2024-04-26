package com.nikitosii.studyrealtorapp.core.source.net

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.BuildConfig
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.lang.Exception

class TokenInterceptor(
    private val storage: LocalStorage
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = MutableLiveData(storage.getToken())
        /**
         *  this code doesn`t have to exist, but i use free api which  have no ability
         *  to generate tokens and it uses default token, which stores here in [KEY_TOKEN] value
         */
        while (token.value?.isEmpty() == null) {
            storage.saveToken(KEY_TOKEN)
            token.postValue(storage.getToken())
        }
        val response = if (token.value?.isBlank() == true) {
            chain.proceed(chain.request())
        }
        else {
            chain.proceed(chain.requestWithToken(token.value!!))
        }
        return response
    }

    private fun Interceptor.Chain.requestWithToken(token: String = KEY_TOKEN): Request {
        val url = request().url.toString()
        return when {
            url.contains("$REALTOR_DOMAIN_NAME/") -> {
                request()
                    .newBuilder()
                    .get()
                    .addHeader(TOKEN_HEADER, token)
                    .addHeader(HOST_HEADER, KEY_HOST)
                    .build()
            }
            url.contains(IMAGE_DOMAIN_NAME) -> {
                request()
                    .newBuilder()
                    .get()
                    .addHeader(IMAGE_HEADER_KEY, IMAGE_HEADER_VALUE)
                    .addHeader(IMAGE_HEADER_TOKEN_KEY, IMAGE_HEADER_TOKEN_VALUE)
                    .build()
            }
            else -> throw Exception("Unknown url: $url")
        }
    }

    companion object {
        private const val KEY_TOKEN = "e35661dae9msh3864476f9185d76p1241b4jsnec9b7dc7f32f"
        private const val TOKEN_HEADER = "X-RapidAPI-Key"
        private const val KEY_HOST = "realtor16.p.rapidapi.com"
        private const val HOST_HEADER = "X-RapidAPI-Host"
        private const val REALTOR_DOMAIN_NAME = "realtor16.p.rapidapi.com"

        private const val IMAGE_DOMAIN_NAME = "api.unsplash.com"
        private const val IMAGE_HEADER_KEY = "Accept-Version"
        private const val IMAGE_HEADER_TOKEN_KEY = "Authorization"
        private const val IMAGE_HEADER_VALUE = "v1"
        private const val IMAGE_HEADER_TOKEN_VALUE = "Client-ID iHrJzNj791pw_mPatZhBT7a4dhxzDMTbZpw_vUDj8Ws"
    }
}