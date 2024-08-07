package com.nikitosii.studyrealtorapp.util

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import okio.buffer
import okio.source
import timber.log.Timber
import java.io.IOException
import java.io.InputStream

object JsonReader {
    private const val TAG = "JsonReader"

    inline fun <reified T> readJson(context: Context, rawResId: Int): T? {
        val moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<T> = moshi.adapter(T::class.java)
        return try {
            context.resources.openRawResource(rawResId).use { inputStream: InputStream ->
                jsonAdapter.fromJson(inputStream.source().buffer())
            }
        } catch (e: IOException) {
            Timber.e("Error reading JSON from resource: ${e.message}")
            null
        }
    }
}