package com.nikitosii.studyrealtorapp.flow.sales

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentDashboardBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.sales.filter.FilterAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.dividerHorizontal
import com.nikitosii.studyrealtorapp.util.ext.hideWithScaleOut
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.view.AnimatedFilterImageView

@RequiresViewModel(DashboardViewModel::class)
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, DashboardViewModel>(
        { FragmentDashboardBinding.bind(it) },
        R.layout.fragment_dashboard
    ) {
    private var lastOpenedFilter: AnimatedFilterImageView? = null
    private val adapter = SaleRequestAdapter { onSaleRequestClick(it) }
    private val filterHousesAdapter = FilterAdapter { onHouseFilterClick(it) }

    override fun initViews() {
        with(binding) {
            rvRecentSearches.adapter = adapter
            rvRecent.dividerHorizontal(R.drawable.divider_horizontal_20dp)
            rvFilterHouses.adapter = filterHousesAdapter
            filterHousesAdapter.submitList(Constants.housesList)

            svSearch.initEndAnimation(clFilters)
            ivHouse.initAnimation(clFilterHouse)
            ivPrice.initAnimation(rvRangePrice)
            ivBath.initAnimation(rvRangeBaths)
            ivBed.initAnimation(rvRangeBeds)
            ivSqft.initAnimation(rvRangeSquare)
        }
        onClick()
    }

    private fun onHouseFilterClick(house: HouseType): Boolean =
        viewModel.setFilterHouse(house.apiType)

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
            svSearch.setOnEndClick {
            }
            ivHouse.setOnClick { checkFilterIsRotated(ivHouse) }
            ivPrice.setOnClick { checkFilterIsRotated(ivPrice) }
            ivBath.setOnClick { checkFilterIsRotated(ivBath) }
            ivBed.setOnClick { checkFilterIsRotated(ivBed) }
            ivSqft.setOnClick { checkFilterIsRotated(ivSqft) }
        }
    }

    private fun checkFilterIsRotated(view: AnimatedFilterImageView) {
        if (lastOpenedFilter != view) {
            lastOpenedFilter?.hideIfExpanded()
            lastOpenedFilter = view
        }
    }

    private fun processRecentRequests(data: List<SalesRequest>?) {
        with(binding) {
            if (data?.isNotEmpty() == true) {
                adapter.submitList(data)
                rvRecentSearches.show()
                rvRecentSearches.notifyDataSetChanged()
            } else grRecentContent.hideWithScaleOut()
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