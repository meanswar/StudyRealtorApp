package com.studyrealtorapp.flow.splashscreen

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.studyrealtorapp.flow.main.MainActivity
import dagger.android.support.DaggerAppCompatActivity

class SplashActivity: DaggerAppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        openMainActivity()
    }

    private fun openMainActivity() {
        Thread.sleep(1000)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}