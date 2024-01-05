package com.studyrealtorapp.flow.sales

import android.os.Bundle
import android.view.View
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.studyrealtorapp.flow.base.BaseFragment
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.dividerHorizontal
import timber.log.Timber

@RequiresViewModel(SalesViewModel::class)
class SalesFragment :
    BaseFragment<FragmentSalesBinding, SalesViewModel>({ FragmentSalesBinding.bind(it) }, R.layout.fragment_sales) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doasdasd()
    }

    override fun initViews() {
        with(binding){
            rvRecent.dividerHorizontal(R.drawable.divider_horizontal)
            rvFavorites.dividerHorizontal(R.drawable.divider_horizontal)
        }
    }

    override fun subscribe() {
        with(getViewModel()) {

        }
    }

    fun doasdasd() {
        Timber.i("dasd")
    }
}