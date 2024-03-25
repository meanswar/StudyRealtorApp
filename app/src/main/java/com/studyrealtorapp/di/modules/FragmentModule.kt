package com.studyrealtorapp.di.modules

import com.studyrealtorapp.flow.rents.RentsFragment
import com.studyrealtorapp.flow.sales.SalesFragment
import com.studyrealtorapp.flow.sales.filter.FilterSalesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeSalesFragment(): SalesFragment

    @ContributesAndroidInjector
    abstract fun contributeFilterSalesFragment(): FilterSalesFragment

    @ContributesAndroidInjector
    abstract fun contributeRentsFragment(): RentsFragment
}