package com.nikitosii.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.flow.dashboard.DashboardFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.search.SearchFragment
import com.nikitosii.studyrealtorapp.flow.details.PropertyDetailsFragment
import com.nikitosii.studyrealtorapp.flow.property_photos.PropertyPhotosFragment
import com.nikitosii.studyrealtorapp.flow.rents.RentsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributeSalesFragment(): DashboardFragment


    @ContributesAndroidInjector
    abstract fun contributeRentsFragment(): RentsFragment

    @ContributesAndroidInjector
    abstract fun contributeSalePropertyDetailsFragment(): PropertyDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    abstract fun contributePropertyPhotosFragment(): PropertyPhotosFragment
}