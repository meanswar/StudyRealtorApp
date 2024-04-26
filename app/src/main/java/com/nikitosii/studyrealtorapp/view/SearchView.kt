package com.nikitosii.studyrealtorapp.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Outline
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.withStyledAttributes
import androidx.core.view.updateLayoutParams
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ViewSearchBinding
import com.nikitosii.studyrealtorapp.util.ext.measureWrapContentWidth
import com.nikitosii.studyrealtorapp.util.ext.onFocus
import com.nikitosii.studyrealtorapp.util.ext.scale
import com.nikitosii.studyrealtorapp.util.ext.show

class SearchView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private var animating = false
    private lateinit var animator: ValueAnimator

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
        initAnimation()
        binding.etSearch.onFocus { onFocus() }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDrawableStart(resInt: Int) {
        val drawable = context.getDrawable(resInt)
        binding.ivStart.setImageDrawable(drawable)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setDrawableEnd(resInt: Int) {
        val drawable = context.getDrawable(resInt)
        binding.ivEnd.setImageDrawable(drawable)
    }

    private fun setAnimator(animator: ValueAnimator) {
        this.animator = animator
    }

    private fun onFocus() {
        animator.start()
//        animator.reverse()
    }

    fun onFocusChanged(isFocused: Boolean, action: ((Boolean) -> Unit)? = null) {
        binding.etSearch.onFocus {
            onFocus()
            action?.invoke(isFocused)
        }
    }

    fun initAnimation(targetView: ViewGroup = binding.flStartImage) {
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
        setAnimator(animation)
    }

    fun initOutline(rect: Rect, scaleX: Float, scaleY: Float, yShift: Int): OutlineProvider {
        binding.root.getGlobalVisibleRect(rect)
        return OutlineProvider(
            rect,
            scaleX,
            scaleY, yShift)
    }


    inner class OutlineProvider(
        private val rect: Rect = Rect(),
        var scaleX: Float,
        var scaleY: Float,
        var yShift: Int
    ) : ViewOutlineProvider() {

        override fun getOutline(view: View?, outline: Outline?) {
            view?.background?.copyBounds(rect)
            rect.scale(scaleX, scaleY)

            rect.offset(0, yShift)

            val cornerRadius =
                resources.getDimensionPixelSize(R.dimen.size_10dp).toFloat()

            outline?.setRoundRect(rect, cornerRadius)
        }
    }
}