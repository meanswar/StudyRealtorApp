package com.studyrealtorapp.flow.sales.filter

import android.transition.TransitionInflater
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentFilterBinding
import com.studyrealtorapp.flow.base.BaseFragment
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.onAnimCompleted
import com.studyrealtorapp.util.ext.onClick

@RequiresViewModel(FilterSalesViewModel::class)
class FilterSalesFragment : BaseFragment<FragmentFilterBinding, FilterSalesViewModel>(
    { FragmentFilterBinding.bind(it) }, R.layout.fragment_filter
) {
    override fun initViews() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        with(binding) {
            btnBack.onAnimCompleted { navController.navigateUp() }
        }
        onClick()
    }

    private fun onClick() {
        with(binding) {
            btnBack.onClick { btnBack.playAnimation() }
        }
    }

    override fun subscribe() {
        with(getViewModel()) {

        }
    }
}