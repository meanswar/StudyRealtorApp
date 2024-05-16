package com.nikitosii.studyrealtorapp.util.ext

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doOnTextChanged
import com.google.android.material.slider.RangeSlider
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

fun TextView.showText(textInt: Int?) {
    if (text.isNullOrEmpty()) hide()
    else {
        this.text = text.toString()
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

fun TextView.showText(number: Int?, resString: Int, isHidden: Boolean = true) {
    if (!number.isNotNull())  if (isHidden) hide() else invisible()
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

fun ConstraintLayout.hideWithLayoutAnim(animRes: Int) {
    val animation = AnimationUtils.loadLayoutAnimation(context, animRes)
    layoutAnimation = animation
    startLayoutAnimation()
    onAnimCompleted { hide() }
}

inline fun View.onClick(crossinline action: () -> Unit) {
    this.setOnClickListener { action() }
}

inline fun EditText.onTextChanged(crossinline action: (String) -> Unit) {
    this.doOnTextChanged { text, _, _, _ -> action(text.toString()) }
}

fun View.onAnimCompleted(time: Long = 500L, action: () -> Unit) {
    val handler = Handler(Looper.getMainLooper())
    handler.postAtTime({ action() }, time)
}

fun EditText.openKeyboard() {
    this.requestFocus()
    val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

@SuppressLint("UseCompatLoadingForDrawables")
fun TextView.selectedItem() {
    setTextColor(context.getColor(R.color.almost_white))
    background = context.getDrawable(R.drawable.bg_selected)
}

@SuppressLint("UseCompatLoadingForDrawables")
fun TextView.unselectedItem() {
    setTextColor(context.getColor(R.color.teflon))
    setBackgroundColor(context.getColor(R.color.transparent))
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

fun ViewGroup.measureWrapContentWidth(): Int {
    this.measure(
        View.MeasureSpec
            .makeMeasureSpec((this as View).measuredWidth, View.MeasureSpec.UNSPECIFIED),
        View.MeasureSpec
            .makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
    )
    return measuredWidth
}

inline fun RadioButton.onCheck(crossinline action: (Boolean) -> Unit) {
    this.setOnCheckedChangeListener { _, isChecked -> action(isChecked) }
}

inline fun RangeSlider.onChange(crossinline action: (RangeSlider, value: Float) -> Unit) {
    this.addOnChangeListener(RangeSlider.OnChangeListener { view, value, _ -> action(view, value) })
}