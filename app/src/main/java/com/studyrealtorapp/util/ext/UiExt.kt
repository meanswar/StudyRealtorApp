package com.studyrealtorapp.util.ext

import android.view.MotionEvent
import android.view.View
import android.widget.EditText

inline fun View?.onClick(crossinline action: () -> Unit) {
    this?.setOnClickListener { action.invoke() }
}

inline fun EditText?.onFocus(crossinline action: () -> Unit) {
    this?.setOnFocusChangeListener { _, _ -> action.invoke() }
}

inline fun EditText?.onTouchEvent(crossinline action: (View, MotionEvent) -> Unit) {
    this?.setOnTouchListener() { view, event -> action.invoke(view, event); true }
}
