package com.nikitosii.studyrealtorapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitosii.studyrealtorapp.di.ViewModelKey
import com.nikitosii.studyrealtorapp.di.ViewModelProviderFactory
import com.nikitosii.studyrealtorapp.flow.main.MainViewModel
import com.nikitosii.studyrealtorapp.flow.rents.RentsViewModel
import com.nikitosii.studyrealtorapp.flow.sales.DashboardViewModel
import com.nikitosii.studyrealtorapp.flow.sales.details.SalePropertyDetailsViewModel
import com.nikitosii.studyrealtorapp.flow.sales.filter.FilterSalesViewModel
import com.nikitosii.studyrealtorapp.flow.sales.history.SearchSalesHistoryViewModel
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
    @ViewModelKey(FilterSalesViewModel::class)
    fun bindFilterSalesViewModel(viewModel: FilterSalesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RentsViewModel::class)
    fun bindRentsViewModel(viewModel: RentsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchSalesHistoryViewModel::class)
    fun bindSearchSalesViewModel(viewModel: SearchSalesHistoryViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SalePropertyDetailsViewModel::class)
    fun bindSalePropertyDetailsViewModel(viewModel: SalePropertyDetailsViewModel): ViewModel
}