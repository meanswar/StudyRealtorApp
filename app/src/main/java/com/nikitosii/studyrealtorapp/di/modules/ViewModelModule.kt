package com.nikitosii.studyrealtorapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitosii.studyrealtorapp.di.ViewModelKey
import com.nikitosii.studyrealtorapp.di.ViewModelProviderFactory
import com.nikitosii.studyrealtorapp.flow.agent.details.AgentDetailsViewModel
import com.nikitosii.studyrealtorapp.flow.agent.homepage.AgentsHomePageViewModel
import com.nikitosii.studyrealtorapp.flow.dashboard.DashboardViewModel
import com.nikitosii.studyrealtorapp.flow.dashboard.search.SearchViewModel
import com.nikitosii.studyrealtorapp.flow.details.PropertyDetailsViewModel
import com.nikitosii.studyrealtorapp.flow.main.MainViewModel
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
    @ViewModelKey(AgentsHomePageViewModel::class)
    fun bindAgentsHomePageViewModel(viewModel: AgentsHomePageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AgentDetailsViewModel::class)
    fun bindAgentDetailsViewModel(viewModel: AgentDetailsViewModel): ViewModel
}