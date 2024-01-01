package com.studyrealtorapp.di.modules

import com.studyrealtorapp.flow.main.MainActivity
import com.studyrealtorapp.flow.splashscreen.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity
}