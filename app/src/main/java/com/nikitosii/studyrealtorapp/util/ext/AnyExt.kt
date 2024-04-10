package com.nikitosii.studyrealtorapp.util.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Any?.isNotNull(): Boolean = this != null

fun Fragment.toast(message: String) =
    run { Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show() }

fun Activity.toast(message: String) =
    run { Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show() }

fun Fragment.string(id: Int) = resources.getString(id)

fun Activity.string(id: Int) = resources.getString(id)