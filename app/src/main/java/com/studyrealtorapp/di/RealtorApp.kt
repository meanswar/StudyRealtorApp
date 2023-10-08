package com.studyrealtorapp.di

import android.app.Application
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class RealtorApp: Application(), HasAndroidInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

//    @Inject
//    lateinit var initializerSet: Set<@JvmSuppressWildcards Initializer>

    private lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        application = this
        component = DaggerAppComponent.builder()
            .app(this)
            .build()
        component.inject(this)

//        initializerSet.forEach { it.perform() }
//        RxJavaPlugins.setErrorHandler {
//            FirebaseCrashlytics.getInstance().recordException(it)
//        }

    }

    override fun androidInjector() = dispatchingAndroidInjector

    companion object {
        lateinit var application: RealtorApp

        fun component(): AppComponent = application.component
    }
}