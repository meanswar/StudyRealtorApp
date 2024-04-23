package com.nikitosii.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.flow.sales.filter.FilterSalesFragment
import com.nikitosii.studyrealtorapp.flow.rents.RentsFragment
import com.nikitosii.studyrealtorapp.flow.sales.SalesFragment
import com.nikitosii.studyrealtorapp.flow.sales.details.SalePropertyDetailsFragment
import com.nikitosii.studyrealtorapp.flow.sales.history.SearchSalesHistoryFragment
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

    @ContributesAndroidInjector
    abstract fun contributeSearchSalesHistoryFragment(): SearchSalesHistoryFragment

    @ContributesAndroidInjector
    abstract fun contributeSalePropertyDetailsFragment(): SalePropertyDetailsFragment
}