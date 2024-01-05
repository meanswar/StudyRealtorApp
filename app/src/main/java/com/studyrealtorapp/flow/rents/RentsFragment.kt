package com.studyrealtorapp.flow.rents

import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.studyrealtorapp.flow.base.BaseFragment
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.dividerHorizontal

@RequiresViewModel(RentsViewModel::class)
class RentsFragment : BaseFragment<FragmentSalesBinding, RentsViewModel>(
    { FragmentSalesBinding.bind(it) },
    R.layout.fragment_sales
) {

    override fun initViews() {
        with(binding) {
            rvRecent.dividerHorizontal(R.drawable.divider_horizontal)
            rvFavorites.dividerHorizontal(R.drawable.divider_horizontal)
        }
    }

    override fun subscribe() {

    }
}