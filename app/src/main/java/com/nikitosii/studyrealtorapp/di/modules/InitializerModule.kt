package com.nikitosii.studyrealtorapp.di.modules

import android.content.Context
import com.nikitosii.studyrealtorapp.core.initializer.HawkInitializer
import com.nikitosii.studyrealtorapp.core.initializer.Initializer
import com.nikitosii.studyrealtorapp.core.initializer.TimberInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class InitializerModule {
    @Provides
    @IntoSet
    internal fun providesTimberInitializer(): Initializer = TimberInitializer()

    @Provides
    @IntoSet
    internal fun providesHawkInitializer(context: Context): Initializer = HawkInitializer(context)
}