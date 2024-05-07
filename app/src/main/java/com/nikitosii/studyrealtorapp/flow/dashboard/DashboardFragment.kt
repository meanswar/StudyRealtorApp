package com.nikitosii.studyrealtorapp.flow.dashboard

import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentDashboardBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.view.RangeView

@RequiresViewModel(DashboardViewModel::class)
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, DashboardViewModel>(
        { FragmentDashboardBinding.bind(it) },
        R.layout.fragment_dashboard
    ) {
    private val recentSaleAdapter = SaleRequestAdapter { onSaleRequestClick(it) }
    private val recentRentAdapter = SaleRequestAdapter { onSaleRequestClick(it) }
    private val filterHousesAdapter = FilterAdapter { onHouseFilterClick(it) }

    override fun initViews() {
        viewModel.getRecentRentRequests()
        viewModel.getRecentSaleRequests()
        with(binding) {
            rvRecentSaleSearches.adapter = recentSaleAdapter
            rvRecentRentSearches.adapter = recentRentAdapter
            filterHousesAdapter.submitList(Constants.housesList)
            svSearch.initEndAnimation(clFilters)

            with(lFilterAttributes) {
                grFilters.hide()
                rvFilterTypes.adapter = filterHousesAdapter
                rvRangePrice.onRangeChanged { first, second -> onPriceChanged(first, second) }
                rvRangeBaths.onRangeChanged { first, second -> onBathsChanged(first, second) }
                rvRangeBeds.onRangeChanged { first, second -> onBedsChanged(first, second) }
                rvRangeSqft.onRangeChanged { first, second -> onSqftChanged(first, second) }
            }
        }
        onClick()
    }

    private fun onHouseFilterClick(house: HouseType): Boolean {
        with(viewModel) {
            val result = viewModel.setFilterHouse(house)
            binding.ivHouse.setIsFilled(isFilterHousesFilled())
            return result
        }
    }


    override fun subscribe() {
        with(viewModel) {
            recentSaleRequests.observe(viewLifecycleOwner, recentSaleRequestsObserver)
            recentRentRequests.observe(viewLifecycleOwner, recentRentRequestsObserver)
        }
    }

    private val recentSaleRequestsObserver: Observer<List<SearchRequest>> =
        Observer { processRecentSaleRequests(it) }

    private val recentRentRequestsObserver: Observer<List<SearchRequest>> =
        Observer { processRecentRentRequests(it) }

    private fun onClick() {
        with(binding) {
            svSearch.setOnEndClick { svSearch.setIsFilled(viewModel.checkFilters()) }
            svSearch.setOnTextChanged { viewModel.addressFilter.value = it }
            btnBuy.onClick { viewModel.requestType.value = RequestType.SALE; openSearchSaleScreen() }

            with(lFilterAttributes) {
                ivHouse.initAnimation(rvFilterTypes)
                ivPrice.initAnimation(rvRangePrice)
                ivBath.initAnimation(rvRangeBaths)
                ivBed.initAnimation(rvRangeBeds)
                ivSqft.initAnimation(rvRangeSqft)
            }
        }
    }


    private fun onPriceChanged(minValue: Int, maxValue: Int) {
        binding.ivPrice.setIsFilled(minValue != RangeView.RANGE_VALUE_ANY || maxValue != RangeView.RANGE_VALUE_ANY)
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.priceMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.priceMaxFilter.value = maxValue
    }

    private fun onBedsChanged(minValue: Int, maxValue: Int) {
        binding.ivBed.setIsFilled(minValue != RangeView.RANGE_VALUE_ANY || maxValue != RangeView.RANGE_VALUE_ANY)
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.bedsMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.bedsMaxFilter.value = maxValue
    }

    private fun onBathsChanged(minValue: Int, maxValue: Int) {
        binding.ivBath.setIsFilled(minValue != RangeView.RANGE_VALUE_ANY || maxValue != RangeView.RANGE_VALUE_ANY)
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.bathsMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.bathsMaxFilter.value = maxValue
    }

    private fun onSqftChanged(minValue: Int, maxValue: Int) {
        binding.ivSqft.setIsFilled(minValue != RangeView.RANGE_VALUE_ANY || maxValue != RangeView.RANGE_VALUE_ANY)
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.sqftMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.sqftMaxFilter.value = maxValue
    }

    private fun processRecentSaleRequests(data: List<SearchRequest>) {
        with(binding) {
            if (data.isNotEmpty()) {
                recentSaleAdapter.submitList(data)
                rvRecentSaleSearches.notifyDataSetChanged()
            } else grRecentSaleContent.hide()
        }
    }

    private fun processRecentRentRequests(data: List<SearchRequest>) {
        with(binding) {
            if (data.isNotEmpty()) {
                recentRentAdapter.submitList(data)
                rvRecentRentSearches.notifyDataSetChanged()
            } else grRecentRentContent.hide()
        }
    }

    private fun onSaleRequestClick(filter: SearchRequest) {
        val extras = FragmentNavigatorExtras(
            binding.svSearch to "svSearch"
        )
        DashboardFragmentDirections.openSearchScreen(filter, true).navigate(extras)
    }

    private fun openSearchSaleScreen() {
        val extras = FragmentNavigatorExtras(
            binding.svSearch to "svSearch"
        )
        viewModel.setRequestType(RequestType.SALE)
        val request = viewModel.buildSaleRequest()
        if (viewModel.checkFilters())
            DashboardFragmentDirections.openSearchScreen(request, false)
                .navigate(extras)
    }

}