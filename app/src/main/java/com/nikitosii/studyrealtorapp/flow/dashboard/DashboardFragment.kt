package com.nikitosii.studyrealtorapp.flow.dashboard

import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
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
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.attachPagerSnap
import com.nikitosii.studyrealtorapp.util.ext.hideWithScaleOut
import com.nikitosii.studyrealtorapp.util.ext.onClick
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
            rvFilterHouses.adapter = filterHousesAdapter
            filterHousesAdapter.submitList(Constants.housesList)

            svSearch.initEndAnimation(clFilters)
            ivHouse.initAnimation(clFilterHouse)
            ivPrice.initAnimation(clRangePrice)
            ivBath.initAnimation(rvRangeBaths)
            ivBed.initAnimation(rvRangeBeds)
            ivSqft.initAnimation(rvRangeSquare)
        }
        onClick()
    }

    private fun onHouseFilterClick(house: HouseType): Boolean {
        with(viewModel) {
            val result = viewModel.setFilterHouse(house.apiType)
            binding.ivHouse.setIsFilled(isFilterHousesFilled())
            return result
        }
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
            svSearch.setOnEndClick { svSearch.setIsFilled(viewModel.checkFilters()); toggleFilter() }
            ivHouse.setOnClick { checkFilterIsRotated(ivHouse) }
            ivPrice.setOnClick { checkFilterIsRotated(ivPrice) }
            ivBath.setOnClick { checkFilterIsRotated(ivBath) }
            ivBed.setOnClick { checkFilterIsRotated(ivBed) }
            ivSqft.setOnClick { checkFilterIsRotated(ivSqft) }

            svSearch.setOnTextChanged { viewModel.addressFilter.value = it }
//            rvRangePrice.onTextChanged { first, second -> onPriceChanged(first, second) }
            rvRangeBaths.onTextChanged { first, second -> onBathsChanged(first, second) }
            rvRangeBeds.onTextChanged { first, second -> onBedsChanged(first, second) }
            rvRangeSquare.onTextChanged { first, second -> onSqftChanged(first, second) }

            btnBuy.onClick { openSearchScreen() }
        }
    }

    private fun toggleFilter() {
        with(binding) {
            if (svSearch.isExpanded()) lastOpenedFilter?.hideIfExpanded() else lastOpenedFilter?.toggle()
        }
    }


    private fun onPriceChanged(textFrom: String, textTo: String) {
        binding.ivPrice.setIsFilled(textFrom.isNotEmpty() || textTo.isNotEmpty())
        if (textFrom.isNotEmpty()) viewModel.priceMinFilter.value = textFrom.toInt()
        if (textTo.isNotEmpty()) viewModel.priceMaxFilter.value = textTo.toInt()
    }

    private fun onBedsChanged(textFrom: String, textTo: String) {
        binding.ivBed.setIsFilled(textFrom.isNotEmpty() || textTo.isNotEmpty())
        if (textFrom.isNotEmpty()) viewModel.bedsMinFilter.value = textFrom.toInt()
        if (textTo.isNotEmpty()) viewModel.bedsMaxFilter.value = textTo.toInt()
    }

    private fun onBathsChanged(textFrom: String, textTo: String) {
        binding.ivBath.setIsFilled(textFrom.isNotEmpty() || textTo.isNotEmpty())
        if (textFrom.isNotEmpty()) viewModel.bathsMinFilter.value = textFrom.toInt()
        if (textTo.isNotEmpty()) viewModel.bathsMaxFilter.value = textTo.toInt()
    }

    private fun onSqftChanged(textFrom: String, textTo: String) {
        binding.ivSqft.setIsFilled(textFrom.isNotEmpty() || textTo.isNotEmpty())
        if (textFrom.isNotEmpty()) viewModel.sqftMinFilter.value = textFrom.toInt()
        if (textTo.isNotEmpty()) viewModel.sqftMaxFilter.value = textTo.toInt()
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
                rvRecentSearches.notifyDataSetChanged()
            } else grRecentContent.hideWithScaleOut()
        }
    }

    private fun onSaleRequestClick(filter: SalesRequest) {
        val extras = FragmentNavigatorExtras(
            binding.svSearch to "svSearch"
        )
        DashboardFragmentDirections.openSearchScreen(filter, true).navigate(extras)
    }

    private fun openSearchScreen() {
        val extras = FragmentNavigatorExtras(
            binding.svSearch to "svSearch"
        )
        if(viewModel.checkFilters())
        DashboardFragmentDirections.openSearchScreen(viewModel.buildSaleRequest(), false).navigate(extras)
    }

    companion object {
        private val FILTER_NULLABLE = null
    }
}