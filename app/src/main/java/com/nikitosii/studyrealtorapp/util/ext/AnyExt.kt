package com.nikitosii.studyrealtorapp.util.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

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
    .diskCacheStrategy(DiskCacheStrategy.DATA)
    .transition(DrawableTransitionOptions.withCrossFade(200))
    .apply {
        if (placeHolder != null) placeholder(placeHolder)
    }
    .skipMemoryCache(false)
    .into(view)

@SuppressLint("CheckResult")
fun Fragment.glideImage(uri: Uri?, view: ImageView, placeHolder: Int? = null) = Glide.with(view)
    .load(uri)
    .diskCacheStrategy(DiskCacheStrategy.ALL)
    .apply { if (placeHolder != null) placeholder(placeHolder) }
    .transition(DrawableTransitionOptions.withCrossFade(200))
    .skipMemoryCache(false)
    .into(view)

@SuppressLint("CheckResult")
fun Fragment.glideImage(image: Bitmap?, view: ImageView, placeHolder: Int? = null) =
    Glide.with(view)
        .load(image)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .apply { if (placeHolder != null) placeholder(placeHolder) }
        .skipMemoryCache(false)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .into(view)

fun RecyclerView.attachPagerSnap() {
    PagerSnapHelper().attachToRecyclerView(this)
}

@SuppressLint("CheckResult")
fun RecyclerView.ViewHolder.glideImage(view: ImageView, url: String?, placeHolder: Int? = null) {
    Glide.with(itemView)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .transition(DrawableTransitionOptions.withCrossFade(200))
        .apply { if (placeHolder != null) placeholder(placeHolder) }
        .skipMemoryCache(false)
        .into(view)
}

inline fun LottieAnimationView.onFavorite(
    crossinline checked: () -> Boolean,
    crossinline onAnimFinished: () -> Unit
) {
    if (checked()) {
        frame = maxFrame.toInt()
        speed = -6.0f
    } else {
        frame = minFrame.toInt()
        speed = 6.0f
    }
    onAnimCompleted(onCompleted = {
        speed *= -1
        onAnimFinished()
    })
}