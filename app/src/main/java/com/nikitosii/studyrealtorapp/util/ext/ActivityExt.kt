package com.nikitosii.studyrealtorapp.util.ext

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat

private const val REQUEST_CODE_NOTIFICATION = 9002

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Context.hasPushNotificationPermission() = ActivityCompat.checkSelfPermission(this,
    Manifest.permission.POST_NOTIFICATIONS
) == PackageManager.PERMISSION_GRANTED

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Activity.hasPushNotificationPermission() = applicationContext.hasPushNotificationPermission()

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun Activity.requestNotificationPermission() =
    requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_CODE_NOTIFICATION)
