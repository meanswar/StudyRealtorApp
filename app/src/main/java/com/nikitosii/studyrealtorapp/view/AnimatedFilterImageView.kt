package com.nikitosii.studyrealtorapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.MutableLiveData
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.util.ext.getProgress
import com.nikitosii.studyrealtorapp.util.ext.measureWrapContentHeight
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show

class AnimatedFilterImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    private var isAnimating = false
    private var isExpanded = false
    private val animator by lazy {  MutableLiveData<ValueAnimator>() }
    private var animationDuration: Long = ANIMATION_DURATION.toLong()


    private val _colorEmptyFilter = MutableLiveData<Int>()
    private val colorEmptyFilter
        get() = _colorEmptyFilter.value ?: resources.getColor(R.color.ic_color_black, resources.newTheme())

    private val _colorActiveFilter = MutableLiveData<Int>()
    private val colorActiveFilter
        get() = _colorActiveFilter.value ?: resources.getColor(R.color.peach, resources.newTheme())

    private val _colorFilledFilter = MutableLiveData<Int>()
    private val colorFilledFilter
        get() = _colorFilledFilter.value ?: resources.getColor(R.color.blue_end, resources.newTheme())

    private val _isRotated = MutableLiveData(false)
    private val isRotated
        get() = _isRotated.value ?: false

    private val _isFilled = MutableLiveData(false)
    private val isFilled
        get() = _isFilled.value ?: false

    init {
        context.obtainStyledAttributes(attrs, R.styleable.AnimatedImageView).apply {
            getResourceId(R.styleable.AnimatedImageView_colorFrom, R.color.ic_color_black).let {
                _colorEmptyFilter.postValue(resources.getColor(it, resources.newTheme()))
            }
            getResourceId(R.styleable.AnimatedImageView_colorTo, R.color.peach).let {
                _colorActiveFilter.postValue(resources.getColor(it, resources.newTheme()))
            }
            getResourceId(R.styleable.AnimatedImageView_colorFilled, R.color.peach).let {
                _colorFilledFilter.postValue(resources.getColor(it, resources.newTheme()))
            }
            getBoolean(R.styleable.AnimatedImageView_isRotated, false).let {
                _isRotated.postValue(it)
            }
            getInteger(R.styleable.AnimatedImageView_animDuration, ANIMATION_DURATION).let {
                animationDuration = it.toLong()
            }
        }
        setOnClick {}
    }

    fun setIsActive(isFilled: Boolean) {
        _isFilled.postValue(isFilled)
    }

    fun hideIfExpanded() { if (isExpanded) onEndClick() }


    fun initAnimation(targetView: ViewGroup) {
        val animation = ValueAnimator.ofArgb(colorEmptyFilter, colorActiveFilter).apply {
            duration = animationDuration
            addUpdateListener {
                val color = it.animatedValue as Int
                this@AnimatedFilterImageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
                val wrapContentHeight = targetView.measureWrapContentHeight()
                val progress = reverseAnimationProgress(getProgress())
                targetView.updateLayoutParams { height = (wrapContentHeight * progress).toInt() }
                targetView.alpha = progress
                targetView.show(targetView.alpha > 0.1f)
            }
            addListener(object : AnimatorListenerAdapter() {

                override fun onAnimationStart(animation: Animator) {
                    super.onAnimationStart(animation)
                    onSettingsAnimationStart()
                }

                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    onSettingsAnimationEnd()
                }
            })
        }
        animator.postValue(animation)
    }

    fun setOnClick(action: () -> Unit) {
        this.onClick {
            action()
            onEndClick()
        }
    }

    private fun onEndClick() {
        animator.value?.start()
    }

    private fun onSettingsAnimationEnd() {
        isAnimating = false
        isExpanded = !isExpanded
        val colorFrom = when {
            isExpanded && !isFilled -> colorActiveFilter
            isFilled -> colorFilledFilter
            else -> colorEmptyFilter
        }
        val colorTo = when {
            isExpanded && isFilled -> colorFilledFilter
            isExpanded && !isFilled -> colorEmptyFilter
            else -> colorActiveFilter
        }
        animator.value?.setIntValues(colorFrom, colorTo)
    }

    private fun onSettingsAnimationStart() {
        isAnimating = true
        if (isRotated) rotate()
    }

    private fun rotate() {
        this@AnimatedFilterImageView
            .animate()
            .rotation(if (!isExpanded) ANIMATION_ROTATE_LEFT else ANIMATION_ROTATE_START)
            .setDuration(animationDuration)
            .start()
    }

    private fun reverseAnimationProgress(progress: Float): Float = if (isExpanded) 1 - progress else progress


    companion object {
        private const val ANIMATION_DURATION = 500
        private const val ANIMATION_ROTATE_LEFT = -90f
        private const val ANIMATION_ROTATE_START = 0f
    }
}