package com.nikitosii.studyrealtorapp.util.ext

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.nikitosii.studyrealtorapp.R

fun View.show(show: Boolean = true, useGone: Boolean = true) {
    this.visibility = if (show) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun TextView.showText(text: String?) {
    if (text.isNullOrEmpty()) hide()
    else {
        this.text = text
        show()
    }
}

fun TextView.showText(text: String?, resString: Int) {
    if (text.isNullOrEmpty()) hide()
    else {
        this.text = context.getString(resString, text)
        show()
    }
}

fun TextView.showText(number: Int?, resString: Int) {
    if (!number.isNotNull()) hide()
    else {
        this.text = context.getString(resString, number)
        show()
    }
}

fun View.showWithAnimation(animRes: Int) {
    val animation = AnimationUtils.loadAnimation(context, animRes)
    this.visibility = View.VISIBLE
    startAnimation(animation)
}

fun View.showWithScaleIn() {
    val animation = AnimationUtils.loadAnimation(context, R.anim.scale_in)
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

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.hideWithAnim(animRes: Int) {
    val animation = AnimationUtils.loadAnimation(context, animRes)
    startAnimation(animation)
    onAnimCompleted { hide() }
}

fun View.hideWithScaleOut() {
    val animation = AnimationUtils.loadAnimation(context, R.anim.scale_out)
    startAnimation(animation)
    onAnimCompleted { hide() }
}

inline fun View.onClick(crossinline action: () -> Unit) {
    this.setOnClickListener { action() }
}

inline fun EditText.onTextChanged(crossinline action: (String) -> Unit) {
    this.doOnTextChanged { text, _, _, _ -> action(text.toString()) }
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

fun TextView.selectedItem() {
    setTextColor(context.getColor(R.color.almost_white))
    background = context.getDrawable(R.drawable.bg_selected)
}

fun TextView.unselectedItem() {
    setTextColor(context.getColor(R.color.teflon))
    background = context.getDrawable(R.drawable.bg_not_selected)
}

fun ViewGroup.measureWrapContentHeight(): Int {
    this.measure(
        View.MeasureSpec
            .makeMeasureSpec((this.parent as View).measuredWidth, View.MeasureSpec.EXACTLY),
        View.MeasureSpec
            .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    return measuredHeight
}