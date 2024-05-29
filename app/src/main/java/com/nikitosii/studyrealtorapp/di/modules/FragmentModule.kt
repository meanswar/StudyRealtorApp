package com.nikitosii.studyrealtorapp.di.modules

import com.nikitosii.studyrealtorapp.flow.agent.details.AgentDetailsFragment
import com.nikitosii.studyrealtorapp.flow.agent.homepage.AgentsFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.DashboardFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.search.SearchFragment
import com.nikitosii.studyrealtorapp.flow.details.PropertyDetailsFragment
import com.nikitosii.studyrealtorapp.flow.profile.ProfileViewPagerFragment
import com.nikitosii.studyrealtorapp.flow.profile.agents.ProfileAgentsFragment
import com.nikitosii.studyrealtorapp.flow.profile.edit.EditProfileFragment
import com.nikitosii.studyrealtorapp.flow.profile.properties.ProfilePropertiesFragment
import com.nikitosii.studyrealtorapp.flow.profile.requests.ProfileRequestsFragment
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

    @ContributesAndroidInjector
    abstract fun contributeAgentsHomePageFragment(): AgentsFragment

    @ContributesAndroidInjector
    abstract fun contributeAgentDetailsFragment(): AgentDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileViewPagerFragment(): ProfileViewPagerFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileAgentsFragment(): ProfileAgentsFragment

    @ContributesAndroidInjector
    abstract fun contributeProfilePropertiesFragment(): ProfilePropertiesFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileRequestsFragment(): ProfileRequestsFragment

    @ContributesAndroidInjector
    abstract fun contributeEditProfileFragment(): EditProfileFragment
}