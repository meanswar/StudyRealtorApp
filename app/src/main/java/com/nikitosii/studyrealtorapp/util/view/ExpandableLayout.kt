package com.nikitosii.studyrealtorapp.util.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.updateLayoutParams
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.util.ext.measureWrapContentHeight

class ExpandableLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : CardView(context, attrs) {


    private lateinit var title: View
    private lateinit var content: ViewGroup
    private lateinit var arrow: View

    private var expanded = false

    private var animating = false

    private var animDuration = 1000L

    private val expandAnimator: ValueAnimator = ValueAnimator.ofFloat(0f, 1f).apply {
        duration = animDuration
        addUpdateListener {
            val progress = it.animatedValue as Float
            val wrapContentHeight = content.measureWrapContentHeight()
            content.updateLayoutParams {
                height = (wrapContentHeight * progress).toInt()
            }

            arrow.rotation = progress * 180
        }
        addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                animating = true
            }

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                animating = false
            }
        })
    }


    override fun onFinishInflate() {
        super.onFinishInflate()
        val parentLayout = getChildAt(0) as ViewGroup

        title = parentLayout.getChildAt(0)
        content = parentLayout.getChildAt(1) as ViewGroup
        arrow = parentLayout.findViewById(R.id.arrowImageView)

        if (expanded) {
            arrow.rotation = 180f
        } else {
            content.updateLayoutParams { height = 0 }
            arrow.rotation = 0f

        }

        title.setOnClickListener {
            when {
                animating -> {
                    expandAnimator.reverse()
                    expanded = !expanded
                }
                expanded -> {
                    expandAnimator.reverse()
                    expanded = false
                }
                else -> {
                    expandAnimator.start()
                    expanded = true
                }
            }
        }
    }
}

