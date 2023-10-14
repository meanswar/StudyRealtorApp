package com.studyrealtorapp.di.modules

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.nikitosii.studyrealtorapp.BuildConfig
import com.studyrealtorapp.core.source.local.LocalStorage
import com.studyrealtorapp.core.source.net.TokenInterceptor
import com.studyrealtorapp.core.source.net.api.PropertiesApi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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
    internal fun providesChunkInterceptor(context: Context) =
        ChuckerInterceptor.Builder(context)
            .alwaysReadResponseBody(true)
            .build()

    @Provides
    @Singleton
    internal fun providesTokenInterceptor(
        context: Context,
        localStorage: LocalStorage
    ) = TokenInterceptor(context, localStorage)

    @Provides
    @Singleton
    internal fun providesClient(
        loggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
        chuckerInterceptor: ChuckerInterceptor
    ) = OkHttpClient().newBuilder()
        .addInterceptor(tokenInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(chuckerInterceptor)
        .callTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .connectTimeout(60, TimeUnit.SECONDS)
        .build()

    @Provides
    @Singleton
    internal fun providesRetrofit(client: OkHttpClient) = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
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