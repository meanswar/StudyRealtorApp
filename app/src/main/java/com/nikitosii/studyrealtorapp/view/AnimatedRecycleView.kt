package com.nikitosii.studyrealtorapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.annotation.AnimRes
import androidx.annotation.Nullable
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.R


class AnimatedRecyclerView : RecyclerView {
    private var orientation = LinearLayoutManager.VERTICAL
    private var reverse = false
    private var animationDuration = 600
    private var layoutManagerType = LayoutManagerType.LINEAR
    private var columns = 1

    @AnimRes
    private var animation: Int = R.anim.recycle_view_anim
    private var animationController: LayoutAnimationController? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, @Nullable attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs)
    }

    @SuppressLint("Recycle", "WrongConstant")
    private fun init(context: Context, @Nullable attrs: AttributeSet?) {
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
        animation = typedArray.getResourceId(R.styleable.AnimatedRecyclerView_layoutAnimation, -1)
        if (animationController == null) animationController =
            if (animation != -1) AnimationUtils.loadLayoutAnimation(
                getContext(),
                animation
            ) else AnimationUtils.loadLayoutAnimation(
                getContext(),
                R.anim.recycle_view_anim
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