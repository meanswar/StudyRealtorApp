package com.studyrealtorapp.util.ext

import android.view.View

fun View.show(show: Boolean = true, useGone: Boolean = true) {
    this.visibility = if (show) View.VISIBLE else if (useGone) View.GONE else View.INVISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}