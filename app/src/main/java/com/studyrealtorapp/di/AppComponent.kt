package com.studyrealtorapp.di

import android.app.Application
import com.studyrealtorapp.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidInjectionModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: RealtorApp)
}