package com.nikitosii.studyrealtorapp.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.google.gson.Gson
import com.nikitosii.studyrealtorapp.BuildConfig
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import com.nikitosii.studyrealtorapp.core.source.local.impl.LocalStorageImpl
import com.nikitosii.studyrealtorapp.core.source.net.TokenInterceptor
import com.nikitosii.studyrealtorapp.core.source.net.api.PropertiesApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideTrustManager(): Array<TrustManager> {
        return arrayOf(object : X509TrustManager {
            override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                Timber.e(NO_OP_FUNCTION_CALL)
            }

            override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
                Timber.e(NO_OP_FUNCTION_CALL)
            }

            override fun getAcceptedIssuers(): Array<X509Certificate> {
                return arrayOf()
            }
        })
    }

    @Provides
    @Singleton
    fun provideSocketFactory(trustAllCerts: Array<TrustManager>): SSLSocketFactory {
        val sslContext = SSLContext.getInstance("SSL")
        sslContext.init(null, trustAllCerts, java.security.SecureRandom())

        return sslContext.socketFactory
    }

    @Provides
    @Singleton
    internal fun providesLoggingInterceptor() =
        HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.tag(OKHTTP_TAG).d(message)
            }
        }).apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    internal fun providesChuckInterceptor(context: Context) = ChuckerInterceptor.Builder(context).build()

    @Provides
    @Singleton
    internal fun providesLocalStorage(): LocalStorage = LocalStorageImpl()

    @Provides
    @Singleton
    internal fun providesTokenInterceptor(localStorage: LocalStorage) = TokenInterceptor(localStorage)

    @Provides
    @Singleton
    internal fun providesClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
        chuckInterceptor: ChuckerInterceptor
    ) = OkHttpClient().newBuilder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(chuckInterceptor)
        .callTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    internal fun providesRetrofit(client: OkHttpClient, moshi: Moshi) = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    @Provides
    @Singleton
    internal fun providesPropertiesApi(retrofit: Retrofit) =
        retrofit.create(PropertiesApi::class.java)

    companion object {
        private const val OKHTTP_TAG = "OkHttp"
        private const val NO_OP_FUNCTION_CALL = "No-op function call!"
    }
}