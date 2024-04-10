package com.nikitosii.studyrealtorapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.getIntegerOrThrow
import androidx.core.content.withStyledAttributes
import androidx.core.view.updateLayoutParams
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ViewTextExpandableBinding
import com.nikitosii.studyrealtorapp.util.ext.getStringOrNull
import com.nikitosii.studyrealtorapp.util.ext.measureWrapContentHeight
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show

class ExpandableTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding =
        ViewTextExpandableBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var animator: ValueAnimator
    private var expanded = false
    private var animating = false


    init {
        context.withStyledAttributes(attrs, R.styleable.ExpandableTextView) {
            getStringOrNull(R.styleable.ExpandableTextView_title)?.let { setTitle(it) }
            setTextAppearance(getResourceId(R.styleable.ExpandableTextView_style, 0))
            getIntegerOrThrow(R.styleable.ExpandableTextView_viewTarget).let { setTargetView(it) }
            onArrowClickDefault()
        }
    }

    private fun setTitle(title: String) {
        binding.tvTitle.text = title
    }

    private fun setTextAppearance(style: Int) {
        if (style != 0) binding.tvTitle.setTextAppearance(style)
    }

    private fun setTargetView(target: Int) {
        if (target != 0) initAnimation(getViewById(target) as ViewGroup)
    }

    private fun setAnimator(animator: ValueAnimator) {
        this.animator = animator
    }

    private fun onArrowClickDefault() {
        onArrowClick {}
    }

    fun onArrowClick(onClick: () -> Unit) {
        binding.clContent.onClick {
            onRotatedClick()
            onClick()
        }
    }

    private fun onRotatedClick() {
        when {
            animating -> {
                animator.reverse()
                expanded = !expanded
            }

            expanded -> {
                animator.reverse()
                expanded = false
            }

            else -> {
                animator.start()
                expanded = true
            }
        }
    }


    fun hideIfExpanded() { if (expanded) onRotatedClick() }

    fun initAnimation(targetView: ViewGroup) {
        val animation = ValueAnimator.ofFloat(0f, 1f).apply {
            duration = 500
            val arrow = binding.ivArrow
            addUpdateListener {
                val progress = it.animatedValue as Float
                val wrapContentHeight = targetView.measureWrapContentHeight()
                targetView.updateLayoutParams {
                    height = (wrapContentHeight * progress).toInt()
                }
                targetView.alpha = progress
                targetView.show(targetView.alpha != 0f)
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
        setAnimator(animation)
    }
}