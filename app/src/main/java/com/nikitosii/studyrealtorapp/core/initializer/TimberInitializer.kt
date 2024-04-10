package com.nikitosii.studyrealtorapp.core.initializer

import com.nikitosii.studyrealtorapp.BuildConfig
import timber.log.Timber

class TimberInitializer: Initializer {

    override fun perform() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}