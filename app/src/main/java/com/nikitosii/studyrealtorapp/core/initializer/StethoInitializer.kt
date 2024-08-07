package com.nikitosii.studyrealtorapp.core.initializer

import android.content.Context
import com.facebook.stetho.Stetho
import javax.inject.Inject

class StethoInitializer @Inject constructor(private val context: Context): Initializer {
    override fun perform() {
        Stetho.initializeWithDefaults(context)
    }
}