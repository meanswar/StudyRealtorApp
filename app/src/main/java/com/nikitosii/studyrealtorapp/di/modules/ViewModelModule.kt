package com.nikitosii.studyrealtorapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitosii.studyrealtorapp.di.ViewModelKey
import com.nikitosii.studyrealtorapp.di.ViewModelProviderFactory
import com.nikitosii.studyrealtorapp.flow.agent.details.AgentDetailsViewModel
import com.nikitosii.studyrealtorapp.flow.agent.homepage.AgentsViewModel
import com.nikitosii.studyrealtorapp.flow.dashboard.DashboardViewModel
import com.nikitosii.studyrealtorapp.flow.dashboard.search.SearchViewModel
import com.nikitosii.studyrealtorapp.flow.details.PropertyDetailsViewModel
import com.nikitosii.studyrealtorapp.flow.main.MainViewModel
import com.nikitosii.studyrealtorapp.flow.profile.ProfileViewModel
import com.nikitosii.studyrealtorapp.flow.profile.agents.ProfileAgentsViewModel
import com.nikitosii.studyrealtorapp.flow.profile.properties.ProfilePropertiesViewModel
import com.nikitosii.studyrealtorapp.flow.profile.requests.ProfileRequestsViewModel
import com.nikitosii.studyrealtorapp.flow.rents.RentsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {
    @Binds
    fun bindViewModelFactory(factory: ViewModelProviderFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DashboardViewModel::class)
    fun bindSalesViewModel(viewModel: DashboardViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RentsViewModel::class)
    fun bindRentsViewModel(viewModel: RentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PropertyDetailsViewModel::class)
    fun bindSalePropertyDetailsViewModel(viewModel: PropertyDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewmodel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AgentsViewModel::class)
    fun bindAgentsHomePageViewModel(viewModel: AgentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AgentDetailsViewModel::class)
    fun bindAgentDetailsViewModel(viewModel: AgentDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindAgentProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileAgentsViewModel::class)
    fun bindAgentProfileAgentsViewModel(viewModel: ProfileAgentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfilePropertiesViewModel::class)
    fun bindAgentProfilePropertiesViewModel(viewModel: ProfilePropertiesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileRequestsViewModel::class)
    fun bindAgentProfileRequestsViewModel(viewModel: ProfileRequestsViewModel): ViewModel
}