package com.studyrealtorapp.util.ext

import android.content.Context
import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.drawable(@DrawableRes drawableId: Int) = ContextCompat.getDrawable(this, drawableId)
fun Fragment.drawable(@DrawableRes drawableId: Int) = context!!.drawable(drawableId)
fun View.drawable(@DrawableRes drawableId: Int) = context!!.drawable(drawableId)