package com.studyrealtorapp.flow.sales

import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.studyrealtorapp.flow.base.BaseFragment
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.dividerHorizontal
import com.studyrealtorapp.util.ext.onClick

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
        with(getViewModel()) {

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
        findNavController().navigate(R.id.filterFragment, null, null, extras)
        SalesFragmentDirections.openFilters().navigate()
    }
}