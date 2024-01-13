package com.studyrealtorapp.flow.sales

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.BuildConfig
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.studyrealtorapp.flow.base.BaseFragment
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.dividerHorizontal
import com.studyrealtorapp.util.ext.dividerVertical
import com.studyrealtorapp.util.ext.onClick
import timber.log.Timber

@RequiresViewModel(SalesViewModel::class)
class SalesFragment :
    BaseFragment<FragmentSalesBinding, SalesViewModel>({ FragmentSalesBinding.bind(it) }, R.layout.fragment_sales) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        with(binding){
            rvRecent.dividerHorizontal(R.drawable.divider_horizontal)
            rvFavorites.dividerHorizontal(R.drawable.divider_horizontal)
            (elSearch.secondLayout.findViewById(R.id.rvRecentSearches) as RecyclerView).apply {
                dividerVertical(R.drawable.divider_horizontal)
            }
            (elSearch.secondLayout.findViewById(R.id.rvFavorites) as RecyclerView).apply {
                dividerVertical(R.drawable.divider_horizontal)
            }
            elSearch.showSpinner = false
        }
        onClick()
    }

    override fun subscribe() {
        with(getViewModel()) {

        }
    }

    private fun onClick() {
        with(binding) {
            elSearch.onClick{ elSearch.toggleLayout() }
        }
    }
}