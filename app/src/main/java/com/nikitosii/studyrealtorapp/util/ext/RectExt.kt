package com.nikitosii.studyrealtorapp.util.ext

import android.graphics.Rect

fun Rect.scale(factorX: Float, factorY: Float) {
    val diffHorizontal = (right - left) * (factorX - 1f)
    val diffVertical = (bottom - top) * (factorY - 1f)

    top -= (diffVertical / 2f).toInt()
    bottom += (diffVertical / 2f).toInt()

    left -= (diffHorizontal / 2f).toInt()
    right += (diffHorizontal / 2f).toInt()
}