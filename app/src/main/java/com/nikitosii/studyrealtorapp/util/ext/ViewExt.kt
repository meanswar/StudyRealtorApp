package com.nikitosii.studyrealtorapp.util.ext

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.constraintlayout.widget.ConstraintLayout

fun View.show(show: Boolean = true, useGone: Boolean = true) {
    this.visibility = if (show) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.showWithAnimation(animRes: Int) {
    val animation = AnimationUtils.loadAnimation(context, animRes)
    this.visibility = View.VISIBLE
    startAnimation(animation)
}


fun ConstraintLayout.showWithLayoutAnim(animRes: Int) {
    val animation = AnimationUtils.loadLayoutAnimation(context, animRes)
    this.visibility = View.VISIBLE
    layoutAnimation = animation
    startLayoutAnimation()
}

fun View.isHidden() = this.visibility == View.GONE

fun View.hide() {
    this.visibility = View.GONE
}

fun View.hideWithAnim(animRes: Int) {
    val animation = AnimationUtils.loadAnimation(context, animRes)
    startAnimation(animation)
    onAnimCompleted { hide() }
}

inline fun View.onClick(crossinline action: () -> Unit) {
    this.setOnClickListener { action() }
}

fun View.onAnimCompleted(action: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    handler.postAtTime({ action() }, 500L)
}

fun EditText.openKeyboard() {
    this.requestFocus()
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}