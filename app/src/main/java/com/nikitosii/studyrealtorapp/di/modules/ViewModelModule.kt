package com.nikitosii.studyrealtorapp.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nikitosii.studyrealtorapp.di.ViewModelKey
import com.nikitosii.studyrealtorapp.di.ViewModelProviderFactory
import com.nikitosii.studyrealtorapp.flow.main.MainViewModel
import com.nikitosii.studyrealtorapp.flow.rents.RentsViewModel
import com.nikitosii.studyrealtorapp.flow.sales.SalesViewModel
import com.nikitosii.studyrealtorapp.flow.sales.filter.FilterSalesViewModel
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
    @ViewModelKey(SalesViewModel::class)
    fun bindSalesViewModel(viewModel: SalesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FilterSalesViewModel::class)
    fun bindFilterSalesViewModel(viewModel: FilterSalesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RentsViewModel::class)
    fun bindRentsViewModel(viewModel: RentsViewModel): ViewModel
}