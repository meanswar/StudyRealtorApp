package com.nikitosii.studyrealtorapp.util.ext

import android.animation.ValueAnimator

fun ValueAnimator.getProgress(): Float {
    val value = currentPlayTime.toFloat() / this.duration
    return if (value > 1) 1f else value
}