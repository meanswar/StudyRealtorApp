package com.nikitosii.studyrealtorapp.core.source.net

import android.content.res.Resources
import com.google.firebase.crashlytics.FirebaseCrashlytics
import com.nikitosii.studyrealtorapp.core.source.net.exceptions.ServerErrorException
import com.nikitosii.studyrealtorapp.core.source.net.exceptions.WrongTokenException
import retrofit2.HttpException
import timber.log.Timber

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
        val errorJson = e.response()?.errorBody()?.string() ?: return e
        e.also {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance().log(errorJson)
        }
        return errorParse(e)
    }

    private fun errorParse(e: HttpException): Exception {
        return when (e.code()) {
            WRONG_TOKEN_EXCEPTION_CODE -> WrongTokenException.createDefError(resources)
            SERVER_ERROR_EXCEPTION_CODE ->  ServerErrorException.createDefError(resources)
            else -> e
        }
    }

    companion object {
        private const val WRONG_TOKEN_EXCEPTION_CODE = 429
        private const val SERVER_ERROR_EXCEPTION_CODE = 500
    }
}