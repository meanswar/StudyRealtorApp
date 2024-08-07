package com.nikitosii.studyrealtorapp.di

import com.nikitosii.studyrealtorapp.core.source.local.LocalStorage
import com.nikitosii.studyrealtorapp.core.source.local.LocalStorageTest
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object TestStorageModule {
    @Provides
    @Singleton
    internal fun providesLocalStorage(): LocalStorage = LocalStorageTest()
}
