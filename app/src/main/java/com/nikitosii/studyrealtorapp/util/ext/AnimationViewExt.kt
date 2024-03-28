package com.nikitosii.studyrealtorapp.util.ext

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView

fun LottieAnimationView.onAnimCompleted(onCompleted: () -> Unit) {
    this.addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {
        }

        override fun onAnimationEnd(p0: Animator) {
            onCompleted()
        }

        override fun onAnimationCancel(p0: Animator) {
        }

        override fun onAnimationRepeat(p0: Animator) {
        }
    })
}