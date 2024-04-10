package com.nikitosii.studyrealtorapp.flow.sales

import androidx.navigation.fragment.FragmentNavigatorExtras
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.dividerHorizontal
import com.nikitosii.studyrealtorapp.util.ext.onClick

@RequiresViewModel(SalesViewModel::class)
class SalesFragment :
    BaseFragment<FragmentSalesBinding, SalesViewModel>({ FragmentSalesBinding.bind(it) }, R.layout.fragment_sales) {

    override fun initViews() {
        with(binding){
            rvRecent.dividerHorizontal(R.drawable.divider_horizontal)
            rvFavorites.dividerHorizontal(R.drawable.divider_horizontal)
        }
        onClick()
    }

    override fun subscribe() {
        with(viewModel) {

        }
    }

    private fun onClick() {
        with(binding) {
            tvFilter.onClick { openFilters() }
        }
    }

    private fun openFilters() {
        val extras = FragmentNavigatorExtras(
            binding.tvFilter to "tvFilter"
        )
        SalesFragmentDirections.openFilters().navigate(extras)
    }
}