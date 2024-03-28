package com.nikitosii.studyrealtorapp.core.source.net

import android.content.res.Resources
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nikitosii.studyrealtorapp.core.source.net.exceptions.ServerErrorException
import retrofit2.HttpException

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