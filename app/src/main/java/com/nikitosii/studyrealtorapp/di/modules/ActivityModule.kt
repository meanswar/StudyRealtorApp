package com.nikitosii.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.flow.main.MainActivity
import com.nikitosii.studyrealtorapp.flow.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeSplashActivity(): SplashActivity

}