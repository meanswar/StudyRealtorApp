package com.nikitosii.studyrealtorapp.util.ext

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


fun Fragment.callIntent(phone: String?) {
    val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone"))
    startActivity(intent)
}

fun Fragment.emailIntent(email: String?) {
    val intent = Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email"))
    startActivity(intent)
}

fun Fragment.openWebsite(website: String?) {
    val intent = Intent(Intent.ACTION_VIEW)
    intent.setData(Uri.parse(website))
    startActivity(intent)
}