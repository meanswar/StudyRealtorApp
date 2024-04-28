package com.nikitosii.studyrealtorapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.annotation.AnimRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.R


class AnimatedRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {
    private var orientation = VERTICAL
    private var reverse = false
    private var animationDuration = 600
    private var layoutManagerType = LayoutManagerType.LINEAR
    private var columns = 1

    @AnimRes
    private var animation: Int = R.anim.recycle_view_anim
    private var animationController: LayoutAnimationController? = null

   init {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.AnimatedRecyclerView, 0, 0)
        orientation = typedArray.getInt(
            R.styleable.AnimatedRecyclerView_layoutManagerOrientation,
            orientation
        )
        reverse =
            typedArray.getBoolean(R.styleable.AnimatedRecyclerView_layoutManagerReverse, reverse)
        animationDuration =
            typedArray.getInt(R.styleable.AnimatedRecyclerView_animationDuration, animationDuration)
        layoutManagerType =
            typedArray.getInt(R.styleable.AnimatedRecyclerView_layoutManagerType, layoutManagerType)
        columns =
            typedArray.getInt(R.styleable.AnimatedRecyclerView_gridLayoutManagerColumns, columns)
        animation = typedArray.getResourceId(
            R.styleable.AnimatedRecyclerView_layoutAnimation,
            R.anim.recycle_view_anim
        )
        if (animationController == null) animationController =
            AnimationUtils.loadLayoutAnimation(
                getContext(),
                animation
            )
        animationController!!.animation.setDuration(animationDuration.toLong())
        setLayoutAnimation(animationController)
        if (layoutManagerType == LayoutManagerType.LINEAR) setLayoutManager(
            LinearLayoutManager(
                context,
                orientation,
                reverse
            )
        ) else if (layoutManagerType == LayoutManagerType.GRID) setLayoutManager(
            GridLayoutManager(
                context,
                columns,
                orientation,
                reverse
            )
        )
    }

    @SuppressLint("NotifyDataSetChanged")
    @Throws(Exception::class)
    fun notifyDataSetChanged() {
        if (adapter != null) {
            adapter?.notifyDataSetChanged()
            scheduleLayoutAnimation()
        } else {
            throw Exception("The adapter must be set")
        }
    }

    annotation class LayoutManagerType {
        companion object {
            const val LINEAR = 0
            const val GRID = 1
        }
    }
}