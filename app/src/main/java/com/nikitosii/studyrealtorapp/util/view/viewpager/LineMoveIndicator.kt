package com.nikitosii.studyrealtorapp.util.view.viewpager

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorInterface


class LineMoveIndicator(private val dachshundTabLayout: CustomTabLayout) :
    AnimatedIndicatorInterface, AnimatorUpdateListener {
    private val paint: Paint
    private val rectF: RectF
    private val rect: Rect
    private var height = 0
    private var edgeRadius: Int
    private var leftX: Int
    private var rightX: Int
    private val valueAnimatorLeft: ValueAnimator = ValueAnimator()
    private val valueAnimatorRight: ValueAnimator = ValueAnimator()
    private val linearInterpolator: LinearInterpolator = LinearInterpolator()

    init {
        valueAnimatorLeft.setDuration(500L)
        valueAnimatorLeft.addUpdateListener(this)
        valueAnimatorLeft.interpolator = linearInterpolator
        valueAnimatorRight.setDuration(500L)
        valueAnimatorRight.addUpdateListener(this)
        valueAnimatorRight.interpolator = linearInterpolator
        rectF = RectF()
        rect = Rect()
        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        leftX = dachshundTabLayout.getChildXLeft(dachshundTabLayout.getCurrentPosition()).toInt()
        rightX = dachshundTabLayout.getChildXRight(dachshundTabLayout.getCurrentPosition()).toInt()
        edgeRadius = -1
    }

    fun setEdgeRadius(edgeRadius: Int) {
        this.edgeRadius = edgeRadius
        dachshundTabLayout.invalidate()
    }

    override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
        leftX = valueAnimatorLeft.getAnimatedValue() as Int
        rightX = valueAnimatorRight.getAnimatedValue() as Int
        rect.top = dachshundTabLayout.height - height
        rect.left = leftX + height / 2
        rect.right = rightX - height / 2
        rect.bottom = dachshundTabLayout.height
        dachshundTabLayout.invalidate(rect)
    }

    override fun setSelectedTabIndicatorColor(@ColorInt color: Int) {
        paint.setColor(color)
    }

    override fun setSelectedTabIndicatorHeight(height: Int) {
        this.height = height
        if (edgeRadius == -1) {
            edgeRadius = height
        }
    }

    override fun setIntValues(
        startXLeft: Int,
        endXLeft: Int,
        startXCenter: Int,
        endXCenter: Int,
        startXRight: Int,
        endXRight: Int
    ) {
        valueAnimatorLeft.setIntValues(*intArrayOf(startXLeft, endXLeft))
        valueAnimatorRight.setIntValues(*intArrayOf(startXRight, endXRight))
    }

    override fun setCurrentPlayTime(currentPlayTime: Long) {
        valueAnimatorLeft.setCurrentPlayTime(currentPlayTime)
        valueAnimatorRight.setCurrentPlayTime(currentPlayTime)
    }

    override fun draw(canvas: Canvas) {
        rectF.top = (dachshundTabLayout.height - height).toFloat()
        rectF.left = (leftX + height / 2).toFloat()
        rectF.right = (rightX - height / 2).toFloat()
        rectF.bottom = dachshundTabLayout.height.toFloat()
        canvas.drawRoundRect(rectF, edgeRadius.toFloat(), edgeRadius.toFloat(), paint)
    }

    override fun getDuration(): Long {
        return valueAnimatorLeft.duration
    }
}
