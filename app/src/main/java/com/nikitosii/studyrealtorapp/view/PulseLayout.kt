package com.nikitosii.studyrealtorapp.view

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.nikitosii.studyrealtorapp.R

class PulseLayout : FrameLayout {

    companion object {
        private const val PAINT_OPAQUE_ALPHA = 255
        private const val DEFAULT_WAVE_COUNT = 4
        private const val DEFAULT_REPEAT_COUNT = ValueAnimator.INFINITE
        private const val DEFAULT_ANIMATION_DURATION = 1000
        private const val DEFAULT_WAVE_RADIUS = 50
        private const val DEFAULT_CORNER_RADIUS = 50f
        private const val DEFAULT_WAVE_START_ALPHA = 1f
        private const val DEFAULT_WAVE_END_ALPHA = 0f
        private const val DEFAULT_WAVE_COLOR = Color.BLACK

        private const val ONE_CHILD = 1
        private const val FIRST_CHILD_INDEX = 0
        private const val TRANSPARENT = 0f

        fun builder(context: Context) = Builder(context)
    }

    private var waveRadius = DEFAULT_WAVE_RADIUS
    private var waveCount = DEFAULT_WAVE_COUNT
    private var animationDuration = DEFAULT_ANIMATION_DURATION
    private var animationRepeatCount = DEFAULT_REPEAT_COUNT
    private var waveStartAlpha = DEFAULT_WAVE_START_ALPHA
    private var wavEndAlpha = DEFAULT_WAVE_END_ALPHA
    private var waveColor = DEFAULT_WAVE_COLOR
    private var cornerRadius = DEFAULT_CORNER_RADIUS

    var childMeasuredHeight = 0
    var childMeasuredWidth = 0

    private val wavePaintList = mutableListOf<Paint>()
    private val waveBottomList = mutableListOf<Int>()
    private val waveRightList = mutableListOf<Int>()
    private val waveLeftList = mutableListOf<Int>()
    private val waveTopList = mutableListOf<Int>()

    private val animatorSet = AnimatorSet()

    private constructor(context: Context, builder: Builder) : super(context, null, 0) {
        waveCount = builder.waveCount
        animationDuration = builder.animationDuration
        animationRepeatCount = builder.waveRepeatCount
        waveStartAlpha = builder.startAlpha
        wavEndAlpha = builder.endAlpha
        waveColor = builder.waveColor
        waveRadius = builder.waveRadius
        cornerRadius = builder.cornerRadius

        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setAttributes(attrs)

        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setAttributes(attrs)
        init()
    }

    private fun setAttributes(attrs: AttributeSet) {
        val attr = context.theme.obtainStyledAttributes(attrs, R.styleable.WaveAttributes, 0, 0)
        try {
            waveCount = attr.getInteger(R.styleable.WaveAttributes_wave_count,
                DEFAULT_WAVE_COUNT
            )
            animationDuration = attr.getInteger(R.styleable.WaveAttributes_animation_duration,
                DEFAULT_ANIMATION_DURATION
            )
            waveRadius = attr.getInteger(R.styleable.WaveAttributes_wave_radius,
                DEFAULT_WAVE_RADIUS
            )
            cornerRadius = attr.getFloat(R.styleable.WaveAttributes_corner_radius,
                DEFAULT_CORNER_RADIUS
            )
            animationRepeatCount = attr.getInteger(R.styleable.WaveAttributes_animation_repeat_count,
                DEFAULT_REPEAT_COUNT
            )
            waveStartAlpha = attr.getFloat(R.styleable.WaveAttributes_wave_start_alpha,
                DEFAULT_WAVE_START_ALPHA
            )
            wavEndAlpha = attr.getFloat(R.styleable.WaveAttributes_wave_end_alpha,
                DEFAULT_WAVE_END_ALPHA
            )
            waveColor = attr.getColor(R.styleable.WaveAttributes_wave_color,
                DEFAULT_WAVE_COLOR
            )
        } finally {
            attr.recycle()
        }
    }

    /**
     * Sets [PulsingWaveLayout] size to child size + 2*[waveRadius] and centers the child.
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        if (childCount != ONE_CHILD) {
            throw IllegalArgumentException("There must precisely 1 child view inside PulsingWaveLayout")
        }

        val child = getChildAt(FIRST_CHILD_INDEX)

        childMeasuredWidth = child.measuredWidth
        childMeasuredHeight = child.measuredHeight

        centerChild(child, childMeasuredHeight, childMeasuredWidth)

        setMeasuredDimension(childMeasuredWidth + waveRadius * 2, childMeasuredHeight + waveRadius * 2)
    }

    /**
     * Draws waves with the changing parameters to get the animation.
     * [waveLeftList]: used to set left coordinate for the wave.
     * [waveTopList]: used to set top coordinate for the wave.
     * [waveRightList]: used to set right coordinate for the wave.
     * [waveBottomList]: used to set bottom coordinate for the wave.
     * [cornerRadius]: rounds the corner of the rectangle.
     * [wavePaintList]: paint used for drawing the rectangle which changes alpha in each step of the animation from waveStartAlpha to wavEndAlpha.
     *
     * All Calculations are done in [startAnimation]
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for (i in 0 until waveCount) {
            canvas.drawRoundRect(
                waveLeftList[i].toFloat(),
                waveTopList[i].toFloat(),
                waveRightList[i].toFloat(),
                waveBottomList[i].toFloat(),
                cornerRadius,
                cornerRadius,
                wavePaintList[i]
            )
        }
    }

    /**
     * Initializes the parameters that wil be used for drawing the waves.
     */
    private fun init() {
        setWillNotDraw(false)

        for (i in 0 until this.waveCount) {
            waveRightList.add(childMeasuredWidth)
            waveBottomList.add(childMeasuredHeight)
            waveLeftList.add(0)
            waveTopList.add(0)
            val paint = Paint().apply {
                isAntiAlias = true
                style = Paint.Style.FILL
                color = waveColor
                alpha = 0
            }
            wavePaintList.add(paint)
        }

        val layoutParams = generateDefaultLayoutParams()
        layoutParams.gravity = Gravity.CENTER
        setLayoutParams(layoutParams)
    }

    /**
     * Centers the child inside pulsatingLayout
     */
    private fun centerChild(child: View, childHeight: Int, childWidth: Int) {
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT
        )
        params.gravity = Gravity.CENTER
        params.height = childHeight
        params.width = childWidth
        child.layoutParams = params
    }

    fun startAnimation() {
        val animatorList = mutableListOf<Animator>()
        val child = getChildAt(FIRST_CHILD_INDEX)

        for (i in 0 until waveCount) {
            val delay = (i * animationDuration / waveCount).toLong()

            val waveAnimator = ValueAnimator.ofInt(0, waveRadius).apply {
                duration = animationDuration.toLong()
                repeatCount = animationRepeatCount
                repeatMode = ValueAnimator.RESTART
                startDelay = delay
                addUpdateListener { animation ->
                    val value = animation.animatedValue as Int
                    waveBottomList[i] = child.bottom + value
                    waveRightList[i] = child.right + value
                    waveTopList[i] = child.top - value
                    waveLeftList[i] = child.left - value

                    val alpha = waveStartAlpha - (value * (waveStartAlpha - wavEndAlpha) / waveRadius)
                    wavePaintList[i].alpha = toPaintAlpha(alpha)
                    invalidate()
                }
            }

            animatorList.add(waveAnimator)
        }

        animatorSet.playTogether(animatorList)
        animatorSet.start()
    }

    /**
     * Pauses the animation.
     */
    fun pauseAnimation() = animatorSet.pause()

    /**
     * Resumes the animation.
     */
    fun resumeAnimation() = animatorSet.resume()

    /**
     * Stops the animation and removes waves from the screen.
     */
    fun stopAnimation() {
        animatorSet.removeAllListeners()
        animatorSet.end()
        animatorSet.cancel()
        animatorSet.childAnimations.forEach { it.removeAllListeners() }
        for (i in 0 until waveCount) {
            waveRightList[i] = childMeasuredWidth
            waveBottomList[i] = childMeasuredHeight
            waveTopList[i] = 0
            waveLeftList[i] = 0
            wavePaintList[i].alpha = TRANSPARENT.toInt()
        }
        invalidate()
    }

    /**
     * Converts alpha that ranges from 0.0 to 1 into the format from 0 to 255 which is used for paint alpha.
     */
    private fun toPaintAlpha(alpha: Float) = (alpha * PAINT_OPAQUE_ALPHA).toInt()

    class Builder(val context: Context) {

        var waveCount = 0
            private set
        var animationDuration = 0
            private set
        var waveRepeatCount = ValueAnimator.INFINITE
            private set
        var startAlpha = 1f
            private set
        var endAlpha = 0f
            private set
        var waveColor = 0
            private set
        var waveRadius = 0
            private set
        var cornerRadius = 50f
            private set

        /**
         * Number of waves in the animation which appear on the screen at the same time. If not set it defaults to 4.
         */
        fun waveCount(waveCount: Int) = apply { this.waveCount = waveCount }

        /**
         * Time needed for one wave to finish animating. If not set it defaults to 1 second.
         */
        fun waveAnimationDuration(animationDuration: Int) = apply { this.animationDuration = animationDuration }

        /**
         * Number of times the animation will repeat. If not set, it will repeat infinitely.
         */
        fun waveRepeatCount(waveRepeatCount: Int) = apply { this.waveRepeatCount = waveRepeatCount }

        /**
         * Starting alpha for each wave in the animation. If not set it defaults to 1f.
         */
        fun startAlpha(startAlpha: Float) = apply { this.startAlpha = startAlpha }

        /**
         * End alpha for each wave in the animation. If not set it defaults to 0f
         */
        fun endAlpha(endAlpha: Float) = apply { this.endAlpha = endAlpha }

        /**
         * The color used to draw the wave. If not set it defaults to black.
         */
        fun waveColor(waveColor: Int) = apply { this.waveColor = waveColor }

        /**
         * The amount of space that will be covered by wave outside of the child in pixels. If not set it defaults to 50.
         */
        fun waveRadius(waveRadius: Int) = apply { this.waveRadius = waveRadius }

        /**
         * Radius of the oval used to round the corners for each wave. If not set it defaults to 50f.
         */
        fun cornerRadius(cornerRadius: Float) = apply { this.cornerRadius = cornerRadius }

        fun build(): PulseLayout = PulseLayout(context, this)
    }
}