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
    private val resources: Resources,
    private val moshi: Moshi,
    private val errorMap: Map<String, BaseNetworkException>,
    private val errorMapFueling: Map<String, BaseNetworkException>
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
        val adapter = ErrorResponseJsonAdapter(moshi)
        val response = adapter.nullSafe().fromJson(errorJson) ?: return e
        return chooseErrorMap(response)[if (response.externalErrorCode == "null") response.error else response.externalErrorCode
            ?: response.error]?.also {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance().log(errorJson)
        } ?: response.externalErrorDescription?.run {
            FirebaseCrashlytics.getInstance().log(this)
            FirebaseCrashlytics.getInstance().log(errorJson)
            return BaseNetworkException(this)
        } ?: return BaseNetworkException(
            response.errorMsg ?: "Generic error"
        ).also {
            FirebaseCrashlytics.getInstance().log(response.toString())
        }
    }

    private fun chooseErrorMap(response: ErrorResponse): Map<String, BaseNetworkException> {
        return if (response.error == "ERR_COMM_000") errorMapFueling else errorMap
    }
}