package com.nikitosii.studyrealtorapp.util.ext

import android.content.Context
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.InputStream

fun Uri.toByteArray(context: Context): ByteArray? {
    val inputStream: InputStream? = context.contentResolver.openInputStream(this)
    val byteBuffer = ByteArrayOutputStream()
    val bufferSize = 1024
    val buffer = ByteArray(bufferSize)

    inputStream?.use {
        var len: Int
        while (it.read(buffer).also { len = it } != -1) {
            byteBuffer.write(buffer, 0, len)
        }
    }

    return byteBuffer.toByteArray()
}