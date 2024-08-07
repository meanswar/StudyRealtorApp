package com.nikitosii.studyrealtorapp.util.view.viewpager

import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorInterface


class LineFadeIndicator(private val dachshundTabLayout: CustomTabLayout) :
    AnimatedIndicatorInterface, AnimatorUpdateListener {
    private val paint: Paint
    private val rectF: RectF
    private var height = 0
    private var edgeRadius: Int
    private val valueAnimator: ValueAnimator
    private var startXLeft: Int
    private var startXRight: Int
    private var endXLeft = 0
    private var endXRight = 0
    private var originColor = 0
    private var startColor = 0
    private var endColor = 0

    init {
        valueAnimator = ValueAnimator()
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.setDuration(500L)
        valueAnimator.addUpdateListener(this)
        valueAnimator.setIntValues(*intArrayOf(0, 255))
        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        rectF = RectF()
        startXLeft = dachshundTabLayout.getChildXLeft(dachshundTabLayout.getCurrentPosition()).toInt()
        startXRight = dachshundTabLayout.getChildXRight(dachshundTabLayout.getCurrentPosition()).toInt()
        edgeRadius = -1
    }

    fun setEdgeRadius(edgeRadius: Int) {
        this.edgeRadius = edgeRadius
        dachshundTabLayout.invalidate()
    }

    override fun onAnimationUpdate(valueAnimator: ValueAnimator) {
        val startAlpha = 255 - valueAnimator.getAnimatedValue() as Int
        startColor = Color.argb(
            startAlpha, Color.red(originColor), Color.green(
                originColor
            ), Color.blue(originColor)
        )
        val endAlpha = valueAnimator.getAnimatedValue() as Int
        endColor = Color.argb(
            endAlpha, Color.red(originColor), Color.green(
                originColor
            ), Color.blue(originColor)
        )
        dachshundTabLayout.invalidate()
    }

    override fun setSelectedTabIndicatorColor(@ColorInt color: Int) {
        originColor = color
        startColor = color
        endColor = 0
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
        this.startXLeft = startXLeft
        this.startXRight = startXRight
        this.endXLeft = endXLeft
        this.endXRight = endXRight
    }

    override fun setCurrentPlayTime(currentPlayTime: Long) {
        valueAnimator.setCurrentPlayTime(currentPlayTime)
    }

    override fun draw(canvas: Canvas) {
        rectF.left = (startXLeft + height / 2).toFloat()
        rectF.right = (startXRight - height / 2).toFloat()
        rectF.top = (dachshundTabLayout.height - height).toFloat()
        rectF.bottom = dachshundTabLayout.height.toFloat()
        paint.setColor(startColor)
        canvas.drawRoundRect(rectF, edgeRadius.toFloat(), edgeRadius.toFloat(), paint)
        rectF.left = (endXLeft + height / 2).toFloat()
        rectF.right = (endXRight - height / 2).toFloat()
        paint.setColor(endColor)
        canvas.drawRoundRect(rectF, edgeRadius.toFloat(), edgeRadius.toFloat(), paint)
    }

    override fun getDuration(): Long {
        return valueAnimator.duration
    }
}
