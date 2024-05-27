package com.nikitosii.studyrealtorapp.util.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import timber.log.Timber

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

@SuppressLint("CheckResult")
fun Fragment.glideImage(url: String?, view: ImageView, placeHolder: Int? = null) = Glide.with(view)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    .apply {
        if (placeHolder != null) placeholder(placeHolder)
    }
    .skipMemoryCache(false)
    .apply(
        RequestOptions().dontTransform() // this line
    ).into(view)

@SuppressLint("CheckResult")
fun Fragment.glideImage(uri: Uri?, view: ImageView, placeHolder: Int? = null) = Glide.with(view)
    .load(uri)
    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    .apply {
        if (placeHolder != null) placeholder(placeHolder)
    }
    .skipMemoryCache(false)
    .apply(
        RequestOptions().dontTransform() // this line
    ).into(view)

fun RecyclerView.attachPagerSnap() {
    PagerSnapHelper().attachToRecyclerView(this)
}