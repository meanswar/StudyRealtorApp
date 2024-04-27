package com.nikitosii.studyrealtorapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnRepeat
import androidx.core.content.withStyledAttributes
import androidx.core.view.updateLayoutParams
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ViewSearchBinding
import com.nikitosii.studyrealtorapp.util.ext.measureWrapContentWidth
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onFocus
import com.nikitosii.studyrealtorapp.util.ext.show

class SearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var animating = false
    private lateinit var animatorStart: ValueAnimator
    private lateinit var animatorEnd: ValueAnimator

    private val binding =
        ViewSearchBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        context.withStyledAttributes(attrs, R.styleable.SearchView) {
            getResourceId(R.styleable.SearchView_drawableStart, R.drawable.ic_search).run {
                setDrawableStart(this)
            }
            getResourceId(R.styleable.SearchView_drawableStart, R.drawable.ic_filters).run {
                setDrawableEnd(this)
            }
        }
        initStartAnimation()
        with(binding) {
            etSearch.onFocus { onFocus() }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDrawableStart(resInt: Int) {
        val drawable = context.getDrawable(resInt)
        binding.ivStart.setImageDrawable(drawable)
    }

    fun setOnEndClick(action: () -> Unit) {
        binding.ivEnd.setOnClick {
            action()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDrawableEnd(resInt: Int) {
        val drawable = context.getDrawable(resInt)
        binding.ivEnd.setImageDrawable(drawable)
    }

    private fun setStartAnimator(animator: ValueAnimator) {
        animatorStart = animator
    }

    private fun setEndAnimator(animator: ValueAnimator) {
        animatorEnd = animator
    }

    private fun onFocus() {
        animatorStart.start()
    }

    fun onFocusChanged(isFocused: Boolean, action: ((Boolean) -> Unit)? = null) {
        binding.etSearch.onFocus {
            onFocus()
            action?.invoke(isFocused)
        }
    }

    private fun initStartAnimation(targetView: ViewGroup = binding.flStartImage) {
        val animation = ValueAnimator.ofFloat(1000f, 0f).apply {
            duration = 100
            addUpdateListener {
                val progress = it.animatedValue as Float
                val wrapContentWidth = targetView.measureWrapContentWidth()
                targetView.updateLayoutParams {
                    width = (wrapContentWidth * progress / 1000f).toInt()
                }
                targetView.show(progress > 0.2f)
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
        setStartAnimator(animation)
    }

    fun initEndAnimation(targetView: ViewGroup) {
        binding.ivEnd.initAnimation(targetView)
    }
}