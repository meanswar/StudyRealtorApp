package com.nikitosii.studyrealtorapp.core.initializer

import android.content.Context
import com.orhanobut.hawk.Hawk
import timber.log.Timber

class HawkInitializer(private val context: Context) : Initializer {

    override fun perform() {
        Hawk.init(context)
            .setLogInterceptor { Timber.tag("Hawk").d(it) }
            .build()
    }

}