package com.nikitosii.studyrealtorapp.util.view.viewpager

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.annotation.ColorInt
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorInterface


class DachshundIndicator(private val dachshundTabLayout: CustomTabLayout) :
    AnimatedIndicatorInterface, AnimatorUpdateListener {
    private val paint: Paint
    private val rectF: RectF
    private val rect: Rect
    private var height = 0
    private val valueAnimatorLeft: ValueAnimator
    private val valueAnimatorRight: ValueAnimator
    private val accelerateInterpolator: AccelerateInterpolator
    private val decelerateInterpolator: DecelerateInterpolator
    private var leftX: Int
    private var rightX: Int

    init {
        valueAnimatorLeft = ValueAnimator()
        valueAnimatorLeft.setDuration(500L)
        valueAnimatorLeft.addUpdateListener(this)
        valueAnimatorRight = ValueAnimator()
        valueAnimatorRight.setDuration(500L)
        valueAnimatorRight.addUpdateListener(this)
        accelerateInterpolator = AccelerateInterpolator()
        decelerateInterpolator = DecelerateInterpolator()
        rectF = RectF()
        rect = Rect()
        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        leftX = dachshundTabLayout.getChildXCenter(dachshundTabLayout.getCurrentPosition()).toInt()
        rightX = leftX
    }

    override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
        leftX = valueAnimatorLeft.getAnimatedValue() as Int
        rightX = valueAnimatorRight.getAnimatedValue() as Int
        rect.top = dachshundTabLayout.height - height
        rect.left = leftX - height / 2
        rect.right = rightX + height / 2
        rect.bottom = dachshundTabLayout.height
        dachshundTabLayout.invalidate(rect)
    }

    override fun setSelectedTabIndicatorColor(@ColorInt color: Int) {
        paint.setColor(color)
    }

    override fun setSelectedTabIndicatorHeight(height: Int) {
        this.height = height
    }

    override fun setIntValues(
        startXLeft: Int,
        endXLeft: Int,
        startXCenter: Int,
        endXCenter: Int,
        startXRight: Int,
        endXRight: Int
    ) {
        val toRight = endXCenter - startXCenter >= 0
        if (toRight) {
            valueAnimatorLeft.interpolator = accelerateInterpolator
            valueAnimatorRight.interpolator = decelerateInterpolator
        } else {
            valueAnimatorLeft.interpolator = decelerateInterpolator
            valueAnimatorRight.interpolator = accelerateInterpolator
        }
        valueAnimatorLeft.setIntValues(*intArrayOf(startXCenter, endXCenter))
        valueAnimatorRight.setIntValues(*intArrayOf(startXCenter, endXCenter))
    }

    override fun setCurrentPlayTime(currentPlayTime: Long) {
        valueAnimatorLeft.setCurrentPlayTime(currentPlayTime)
        valueAnimatorRight.setCurrentPlayTime(currentPlayTime)
    }

    override fun draw(canvas: Canvas) {
        rectF.top = (dachshundTabLayout.height - height).toFloat()
        rectF.left = (leftX - height / 2).toFloat()
        rectF.right = (rightX + height / 2).toFloat()
        rectF.bottom = dachshundTabLayout.height.toFloat()
        canvas.drawRoundRect(rectF, height.toFloat(), height.toFloat(), paint)
    }

    override fun getDuration(): Long {
        return valueAnimatorLeft.duration
    }
}
