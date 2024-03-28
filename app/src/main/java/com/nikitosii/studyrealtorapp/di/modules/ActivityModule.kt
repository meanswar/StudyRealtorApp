package com.nikitosii.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.flow.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

}