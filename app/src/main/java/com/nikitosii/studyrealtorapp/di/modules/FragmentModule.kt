package com.nikitosii.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterSalesFragment
import com.nikitosii.studyrealtorapp.flow.rents.RentsFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.DashboardFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.details.PropertyDetailsFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.history.SearchSalesHistoryFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeSalesFragment(): DashboardFragment

    @ContributesAndroidInjector
    abstract fun contributeFilterSalesFragment(): FilterSalesFragment

    @ContributesAndroidInjector
    abstract fun contributeRentsFragment(): RentsFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchSalesHistoryFragment(): SearchSalesHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeSalePropertyDetailsFragment(): PropertyDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}