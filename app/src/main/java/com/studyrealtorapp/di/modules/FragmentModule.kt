package com.studyrealtorapp.di.modules

import com.studyrealtorapp.flow.rents.RentsFragment
import com.studyrealtorapp.flow.sales.SalesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeSalesFragment(): SalesFragment

    @ContributesAndroidInjector
    abstract fun contributeRentsFragment(): RentsFragment
}