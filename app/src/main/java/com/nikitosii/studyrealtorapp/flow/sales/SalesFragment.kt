package com.nikitosii.studyrealtorapp.flow.sales

import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.*
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.dividerHorizontal
import com.nikitosii.studyrealtorapp.util.ext.dividerVertical
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.hideWithAnim
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation

@RequiresViewModel(SalesViewModel::class)
class SalesFragment :
    BaseFragment<FragmentSalesBinding, SalesViewModel>(
        { FragmentSalesBinding.bind(it) },
        R.layout.fragment_sales
    ) {

    private val adapter = SaleRequestAdapter { onSaleRequestClick(it) }

    override fun initViews() {
        with(binding) {
            rvRecent.dividerVertical(R.drawable.divider_vertical)
            rvFavorites.dividerHorizontal(R.drawable.divider_horizontal)
            rvRecent.adapter = adapter
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
            tvFilter.onClick { openFilters() }
        }
    }

    private fun processRecentRequests(data: List<SalesRequest>?) {
        with(binding) {
            if (data.isNullOrEmpty()) clRecentContainer.hideWithAnim(R.anim.scale_out)
             else {
                clRecentContainer.showWithAnimation(R.anim.scale_in)
                adapter.submitList(data)
                rvRecent.notifyDataSetChanged()
            }
        }
    }

    private fun onSaleRequestClick(filter: SalesRequest) {
        val extras = FragmentNavigatorExtras(
            binding.tvFilter to "tvFilter"
        )
        SalesFragmentDirections.openFilters(filter).navigate(extras)
    }

    private fun openFilters() {
        val extras = FragmentNavigatorExtras(
            binding.tvFilter to "tvFilter"
        )
        SalesFragmentDirections.openFilters(FILTER_NULLABLE).navigate(extras)
    }

    companion object {
        private val FILTER_NULLABLE = null
    }
}