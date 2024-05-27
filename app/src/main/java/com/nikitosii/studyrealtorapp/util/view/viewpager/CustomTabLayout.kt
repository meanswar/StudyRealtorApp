package com.nikitosii.studyrealtorapp.util.view.viewpager

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.core.view.ViewCompat
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.google.android.material.tabs.TabLayout
import com.kekstudio.dachshundtablayout.HelperUtils
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorInterface
import com.kekstudio.dachshundtablayout.indicators.AnimatedIndicatorType
import com.kekstudio.dachshundtablayout.R


/**
 * A TitlePageIndicator is a PageIndicator which displays the title of left view
 * (if exist), the title of the current select view (centered) and the title of
 * the right view (if exist). When the user scrolls the ViewPager then titles are
 * also scrolled.
 */

class CustomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    TabLayout(context, attrs, defStyleAttr), OnPageChangeListener {
    private var mIndicatorColor: Int
    private var mIndicatorHeight: Int
    private var mCurrentPosition = 0
    private var mCenterAlign: Boolean
    private val mTabStrip: LinearLayout
    private val mAnimatedIndicatorType: AnimatedIndicatorType
    private var mAnimatedIndicator: AnimatedIndicatorInterface? = null
    private var mTempPosition = 0
    private var mTempPositionOffsetPixels = 0
    private var mTempPositionOffset = 0f

    init {
        super.setSelectedTabIndicatorHeight(0)
        mTabStrip = super.getChildAt(0) as LinearLayout
        val ta = context.obtainStyledAttributes(attrs, R.styleable.DachshundTabLayout)
        mIndicatorHeight = ta.getDimensionPixelSize(
            R.styleable.DachshundTabLayout_ddIndicatorHeight,
            HelperUtils.dpToPx(6)
        )
        mIndicatorColor = ta.getColor(R.styleable.DachshundTabLayout_ddIndicatorColor, -1)
        mCenterAlign = ta.getBoolean(R.styleable.DachshundTabLayout_ddCenterAlign, false)
        mAnimatedIndicatorType = AnimatedIndicatorType.entries[ta.getInt(
            R.styleable.DachshundTabLayout_ddAnimatedIndicator,
            0
        )]
        ta.recycle()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        if (mCenterAlign) {
            val firstTab = (getChildAt(0) as ViewGroup).getChildAt(0)
            val lastTab =
                (getChildAt(0) as ViewGroup).getChildAt((getChildAt(0) as ViewGroup).childCount - 1)
            ViewCompat.setPaddingRelative(
                getChildAt(0),
                this.width / 2 - firstTab.width / 2,
                0,
                this.width / 2 - lastTab.width / 2,
                0
            )
        }
        if (mAnimatedIndicator == null) {
            setupAnimatedIndicator()
        }
        onPageScrolled(mTempPosition, mTempPositionOffset, mTempPositionOffsetPixels)
    }

    private fun setupAnimatedIndicator() {
        when (mAnimatedIndicatorType) {
            AnimatedIndicatorType.DACHSHUND -> setAnimatedIndicator(DachshundIndicator(this))
            AnimatedIndicatorType.LINE_MOVE -> setAnimatedIndicator(LineMoveIndicator(this))
            AnimatedIndicatorType.LINE_FADE -> setAnimatedIndicator(LineFadeIndicator(this))
            else -> throw IllegalArgumentException()
        }
    }

    fun setAnimatedIndicator(animatedIndicator: AnimatedIndicatorInterface) {
        mAnimatedIndicator = animatedIndicator
        animatedIndicator.setSelectedTabIndicatorColor(mIndicatorColor)
        animatedIndicator.setSelectedTabIndicatorHeight(mIndicatorHeight)
        this.invalidate()
    }

    override fun setSelectedTabIndicatorColor(@ColorInt color: Int) {
        mIndicatorColor = color
        if (mAnimatedIndicator != null) {
            mAnimatedIndicator!!.setSelectedTabIndicatorColor(color)
            this.invalidate()
        }
    }

    override fun setSelectedTabIndicatorHeight(height: Int) {
        mIndicatorHeight = height
        if (mAnimatedIndicator != null) {
            mAnimatedIndicator!!.setSelectedTabIndicatorHeight(height)
            this.invalidate()
        }
    }

    fun setCenterAlign(centerAlign: Boolean) {
        mCenterAlign = centerAlign
        requestLayout()
    }

    override fun setupWithViewPager(viewPager: ViewPager?) {
        this.setupWithViewPager(viewPager, true)
    }

    override fun setupWithViewPager(viewPager: ViewPager?, autoRefresh: Boolean) {
        super.setupWithViewPager(viewPager, autoRefresh)
        if (viewPager != null) {
            viewPager.removeOnPageChangeListener(this)
            viewPager.addOnPageChangeListener(this)
        }
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (mAnimatedIndicator != null) {
            mAnimatedIndicator!!.draw(canvas)
        }
    }

    override fun onPageScrollStateChanged(state: Int) {}
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mTempPosition = position
        mTempPositionOffset = positionOffset
        mTempPositionOffsetPixels = positionOffsetPixels
        if (position > mCurrentPosition || position + 1 < mCurrentPosition) {
            mCurrentPosition = position
        }
        val mStartXLeft: Int
        val mStartXCenter: Int
        val mStartXRight: Int
        val mEndXLeft: Int
        val mEndXCenter: Int
        val mEndXRight: Int
        if (position != mCurrentPosition) {
            mStartXLeft = getChildXLeft(mCurrentPosition).toInt()
            mStartXCenter = getChildXCenter(mCurrentPosition).toInt()
            mStartXRight = getChildXRight(mCurrentPosition).toInt()
            mEndXLeft = getChildXLeft(position).toInt()
            mEndXRight = getChildXRight(position).toInt()
            mEndXCenter = getChildXCenter(position).toInt()
            if (mAnimatedIndicator != null) {
                mAnimatedIndicator!!.setIntValues(
                    mStartXLeft,
                    mEndXLeft,
                    mStartXCenter,
                    mEndXCenter,
                    mStartXRight,
                    mEndXRight
                )
                mAnimatedIndicator!!.setCurrentPlayTime(
                    ((1.0f - positionOffset) * mAnimatedIndicator!!.duration.toInt()
                        .toFloat()).toLong()
                )
            }
        } else {
            mStartXLeft = getChildXLeft(mCurrentPosition).toInt()
            mStartXCenter = getChildXCenter(mCurrentPosition).toInt()
            mStartXRight = getChildXRight(mCurrentPosition).toInt()
            if (mTabStrip.getChildAt(position + 1) != null) {
                mEndXLeft = getChildXLeft(position + 1).toInt()
                mEndXCenter = getChildXCenter(position + 1).toInt()
                mEndXRight = getChildXRight(position + 1).toInt()
            } else {
                mEndXLeft = getChildXLeft(position).toInt()
                mEndXCenter = getChildXCenter(position).toInt()
                mEndXRight = getChildXRight(position).toInt()
            }
            if (mAnimatedIndicator != null) {
                mAnimatedIndicator!!.setIntValues(
                    mStartXLeft,
                    mEndXLeft,
                    mStartXCenter,
                    mEndXCenter,
                    mStartXRight,
                    mEndXRight
                )
                mAnimatedIndicator!!.setCurrentPlayTime(
                    (positionOffset * mAnimatedIndicator!!.duration.toInt()
                        .toFloat()).toLong()
                )
            }
        }
        if (positionOffset == 0.0f) {
            mCurrentPosition = position
        }
    }

    override fun onPageSelected(position: Int) {}
    fun getCurrentPosition(): Int {
        return mCurrentPosition
    }

    fun getChildXLeft(position: Int): Float {
        return if (mTabStrip.getChildAt(position) != null) mTabStrip.getChildAt(position).x else 0.0f
    }

    fun getChildXCenter(position: Int): Float {
        return if (mTabStrip.getChildAt(position) != null) mTabStrip.getChildAt(position).x + (mTabStrip.getChildAt(
            position
        ).width / 2).toFloat() else 0.0f
    }

    fun getChildXRight(position: Int): Float {
        return if (mTabStrip.getChildAt(position) != null) mTabStrip.getChildAt(position).x + mTabStrip.getChildAt(
            position
        ).width.toFloat() else 0.0f
    }

    fun getAnimatedIndicator(): AnimatedIndicatorInterface? {
        return mAnimatedIndicator
    }

    companion object {
        private const val DEFAULT_HEIGHT_DP = 6
    }
}
