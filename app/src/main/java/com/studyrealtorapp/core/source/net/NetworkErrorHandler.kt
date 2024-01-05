package com.studyrealtorapp.core.source.net

import android.content.res.Resources
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.squareup.moshi.Moshi
import com.studyrealtorapp.core.source.net.exceptions.BaseNetworkException
import com.studyrealtorapp.core.source.net.exceptions.ServerErrorException
import com.studyrealtorapp.core.source.net.exceptions.TimeoutErrorException
import retrofit2.HttpException
import java.net.SocketTimeoutException

class NetworkErrorHandler(
    private val resources: Resources
) {

    suspend fun <T> runWithErrorHandler(block: suspend () -> T): T {
        try {
            return block()
        } catch (e: HttpException) {
            throw  parseNetworkError(e)
        }
    }

    fun parseNetworkError(e: HttpException): Exception {
        if (e.code() == 500) return ServerErrorException.createDefError(resources)
        val errorJson = e.response()?.errorBody()?.string() ?: return e
        return e.also {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance().log(errorJson)
        }
    }
}