package com.nikitosii.studyrealtorapp.flow.sales

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentDashboardBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.hideWithScaleOut
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithScaleIn

@RequiresViewModel(DashboardViewModel::class)
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, DashboardViewModel>(
        { FragmentDashboardBinding.bind(it) },
        R.layout.fragment_dashboard
    ) {

    private val adapter = SaleRequestAdapter { onSaleRequestClick(it) }

    override fun initViews() {
        with(binding) {
            rvRecentSearches.adapter = adapter
        }
        onClick()
    }

    override fun subscribe() {
        with(viewModel) {
            saleRequestsHistory.observe(viewLifecycleOwner, saleRequestsHistoryObserver)
        }
    }

    private val saleRequestsHistoryObserver: Observer<WorkResult<Status<List<SalesRequest>>>> =
        Observer {
            when (it.status) {
                SUCCESS -> processRecentRequests(it.data?.obj)
                LOADING -> logi("loading recent requests")
                ERROR -> handleException(it.exception) { openError() }
            }
        }

    private fun onClick() {
        with(binding) {

        }
    }

    private fun processRecentRequests(data: List<SalesRequest>?) {
        with(binding) {
            if (data?.isNotEmpty() == true) {
                adapter.submitList(data)
                rvRecentSearches.show()
                rvRecentSearches.notifyDataSetChanged()
            }
            else grRecentContent.hideWithScaleOut()
        }
    }

    private fun onSaleRequestClick(filter: SalesRequest) {
//        val extras = FragmentNavigatorExtras(
//            binding.tvFilter to "tvFilter"
//        )
//        DashboardFragmentDirections.openFilters(filter).navigate(extras)
    }

    private fun openFilters() {
//        val extras = FragmentNavigatorExtras(
//            binding.tvFilter to "tvFilter"
//        )
//        DashboardFragmentDirections.openFilters(FILTER_NULLABLE).navigate(extras)
    }

    private fun openSaleSearchHistory() {
        DashboardFragmentDirections.openSaleSearchHistory().navigate()
    }

    companion object {
        private val FILTER_NULLABLE = null
    }
}