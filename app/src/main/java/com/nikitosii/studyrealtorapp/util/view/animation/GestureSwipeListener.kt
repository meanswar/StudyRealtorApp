package com.nikitosii.studyrealtorapp.util.view.animation

import android.view.GestureDetector
import android.view.MotionEvent
import timber.log.Timber

class GestureSwipeListener(
    private val onClick: () -> Unit,
    private val onSwipeLeft: () -> Unit
) : GestureDetector.SimpleOnGestureListener() {

    private val SWIPE_THRESHOLD = 100
    private val SWIPE_VELOCITY_THRESHOLD = 100

    override fun onSingleTapUp(e: MotionEvent): Boolean {
        // Handle click
        onClick()
        return true
    }

    override fun onFling(
        e1: MotionEvent?,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        if (e1 != null && e2 != null) {
            val diffX = e2.x - e1.x
            val diffY = e2.y - e1.y
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        Timber.i("swipe right")
                        // Handle swipe right
                    } else {
                        Timber.i("swipe left")
                        // Handle swipe left
                        onSwipeLeft()
                    }
                    return true
                }
            }
        }
        return false
    }

    override fun onDown(e: MotionEvent): Boolean {
        onSwipeLeft()
        return true
    }
}