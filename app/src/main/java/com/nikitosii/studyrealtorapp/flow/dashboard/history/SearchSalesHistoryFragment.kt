package com.nikitosii.studyrealtorapp.flow.dashboard.history

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentSearchSalesHistoryBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.SaleRequestAdapter
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.dividerVertical
import com.nikitosii.studyrealtorapp.util.ext.onAnimCompleted
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.showWithScaleIn

@RequiresViewModel(SearchSalesHistoryViewModel::class)
class SearchSalesHistoryFragment :
    BaseFragment<FragmentSearchSalesHistoryBinding, SearchSalesHistoryViewModel>(
        { FragmentSearchSalesHistoryBinding.bind(it) },
        R.layout.fragment_search_sales_history
    ) {

        val adapter = SaleRequestAdapter{ openFilterDetails(it) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getSearchHistory()
    }


    override fun initViews() {
        with(binding) {
            rvSearchHistory.adapter = adapter
            rvSearchHistory.dividerVertical(R.drawable.divider_vertical_10dp)
            btnBack.onClick { btnBack.playAnimation() }
            btnBack.onAnimCompleted { navController.navigateUp() }
        }
    }

    override fun subscribe() {
       with(viewModel) {
           searchHistory.observe(viewLifecycleOwner, searchHistoryObserver)
       }
    }

    private val searchHistoryObserver: Observer<List<SalesRequest>> = Observer {
        adapter.submitList(it)
        binding.rvSearchHistory.showWithScaleIn()
        binding.rvSearchHistory.notifyDataSetChanged()
    }

    private fun openFilterDetails(property: SalesRequest) {
//        SearchSalesHistoryFragmentDirections.openFilters(property).navigate()
    }
}