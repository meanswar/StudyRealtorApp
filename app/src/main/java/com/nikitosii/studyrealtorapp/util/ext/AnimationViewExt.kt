package com.nikitosii.studyrealtorapp.util.ext

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView

fun LottieAnimationView.onAnimCompleted(onCompleted: () -> Unit, onStarted: () -> Unit = {}) {
    this.addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {
            this@onAnimCompleted.isEnabled = false
        }

        override fun onAnimationEnd(p0: Animator) {
            onCompleted()
            this@onAnimCompleted.isEnabled = true
        }

        override fun onAnimationCancel(p0: Animator) {
        }

        override fun onAnimationRepeat(p0: Animator) {
        }
    })
}