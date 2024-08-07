package com.nikitosii.studyrealtorapp.flow.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.nikitosii.studyrealtorapp.flow.main.MainActivity
import dagger.android.DaggerActivity

class SplashActivity : DaggerActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            MainActivity.start(this)
        }, 2000)
    }
}