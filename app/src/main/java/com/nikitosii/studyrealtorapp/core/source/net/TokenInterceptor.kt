package com.nikitosii.studyrealtorapp.core.source.net

import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.BuildConfig
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber

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

//    private fun Interceptor.Chain.requestWithToken(token: String = KEY_TOKEN): Request = request()
//        .newBuilder()
//        .get()
//        .addHeader(TOKEN_HEADER, token)
//        .addHeader(HOST_HEADER, KEY_HOST)
//        .build()

    private fun Interceptor.Chain.requestWithToken(token: String = KEY_TOKEN): Request {
        val oldRequest = request().newBuilder()
        .get()
        .addHeader(TOKEN_HEADER, token)
        .addHeader(HOST_HEADER, KEY_HOST)
        .build()
        Timber.i("1request is: $oldRequest")
        val newRequest = Request.Builder()
            .url("${BuildConfig.BASE_URL}forsale?location=santa%20monica&type=single_family%2Ccondos")
            .get()
            .addHeader(TOKEN_HEADER, token)
            .addHeader(HOST_HEADER, KEY_HOST)
            .build()
        Timber.i("2request is: ${newRequest.body.toString()}")
        return newRequest
    }

    companion object {
        private const val KEY_TOKEN = "e35661dae9msh3864476f9185d76p1241b4jsnec9b7dc7f32f"
        private const val TOKEN_HEADER = "X-RapidAPI-Key"
        private const val KEY_HOST = "realtor16.p.rapidapi.com"
        private const val HOST_HEADER = "X-RapidAPI-Host"
    }
}