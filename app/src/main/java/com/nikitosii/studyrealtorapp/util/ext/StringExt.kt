package com.nikitosii.studyrealtorapp.util.ext

import android.content.res.TypedArray
import androidx.annotation.StyleableRes

fun String.getStringOrNull(): String? = if (this.isNotNull()) this else null

fun TypedArray.getStringOrNull(@StyleableRes index: Int) =
    if (hasValue(index)) getString(index) else null


