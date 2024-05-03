package com.nikitosii.studyrealtorapp.util.ext

import android.app.Activity
import android.graphics.drawable.Drawable
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

fun Fragment.glideImage(url: String?, view: ImageView) = Glide.with(view)
    .load(url)
    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
    .skipMemoryCache(false)
    .apply(
        RequestOptions().dontTransform() // this line
    )
    .addListener(object : RequestListener<Drawable> {
        override fun onLoadFailed(
            p0: GlideException?,
            p1: Any?,
            p2: Target<Drawable>?,
            p3: Boolean
        ): Boolean {
            Timber.i("startPostponedEnterTransition error")
            startPostponedEnterTransition()
            return false
        }

        override fun onResourceReady(
            p0: Drawable?,
            p1: Any?,
            p2: Target<Drawable>?,
            p3: DataSource?,
            p4: Boolean
        ): Boolean {
            Timber.i("startPostponedEnterTransition success")
            startPostponedEnterTransition()
            return false
        }
    })
    .into(view)

fun RecyclerView.attachPagerSnap() {
    PagerSnapHelper().attachToRecyclerView(this)
}