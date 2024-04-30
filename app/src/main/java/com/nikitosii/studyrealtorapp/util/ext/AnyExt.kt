package com.nikitosii.studyrealtorapp.util.ext

import android.app.Activity
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun Any?.isNotNull(): Boolean = this != null

fun Fragment.toast(message: String) =
    run { Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show() }

fun Activity.toast(message: String) =
    run { Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show() }

fun Fragment.string(id: Int) = resources.getString(id)

fun Fragment.string(id: Int, vararg args: Int) = resources.getString(id, args)

fun Fragment.string(id: Int, vararg args: Float) = resources.getString(id, args)

fun Fragment.string(id: Int, vararg args: String) = resources.getString(id, args)

fun Activity.string(id: Int) = resources.getString(id)

fun Fragment.glideImage(url: String?, view: ImageView) = Glide.with(view)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    .skipMemoryCache(false)
    .into(view)