package com.nikitosii.studyrealtorapp.flow.dashboard

import android.widget.RadioButton
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentDashboardBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.onCheck
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.view.PulseLayout
import com.nikitosii.studyrealtorapp.view.RangeView
import timber.log.Timber

@RequiresViewModel(DashboardViewModel::class)
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, DashboardViewModel>(
        { FragmentDashboardBinding.bind(it) },
        R.layout.fragment_dashboard
    ) {
    private val recentSaleAdapter =
        SaleRequestAdapter({ onSaleRequestClick(it) }, { onFavoriteClick(it) })
    private val recentRentAdapter =
        SaleRequestAdapter({ onSaleRequestClick(it) }, { onFavoriteClick(it) })
    private val filterHousesAdapter = FilterAdapter { onHouseFilterClick(it) }

    override fun initViews() {
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
        binding.btnBuy.isChecked = true
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

    private val recentSaleRequestsObserver: Observer<WorkResult<com.nikitosii.studyrealtorapp.core.source.channel.Status<List<SearchRequest>>>> = Observer {
        when (it.status) {
            Status.SUCCESS -> processRecentSaleRequests(it.data?.obj ?: listOf())
            Status.ERROR -> handleException(it.exception) { openError() }
            Status.LOADING -> Timber.i("loading sale requests")
        }
    }

    private val recentRentRequestsObserver: Observer<WorkResult<com.nikitosii.studyrealtorapp.core.source.channel.Status<List<SearchRequest>>>> = Observer {
        when (it.status) {
            Status.SUCCESS -> processRecentRentRequests(it.data?.obj ?: listOf())
            Status.ERROR -> handleException(it.exception) { openError() }
            Status.LOADING -> Timber.i("loading rent requests")
        }
    }

    private fun onClick() {
        with(binding) {
            svSearch.setOnEndClick { svSearch.setIsFilled(viewModel.checkFilters()) }
            svSearch.setOnTextChanged { viewModel.addressFilter.value = it }

            with(lFilterAttributes) {
                ivHouse.initAnimation(rvFilterTypes)
                ivPrice.initAnimation(rvRangePrice)
                ivBath.initAnimation(rvRangeBaths)
                ivBed.initAnimation(rvRangeBeds)
                ivSqft.initAnimation(rvRangeSqft)
            }

            btnBuy.onCheck { onCheckCallback(it, btnRent, plBuy, plRent, RequestType.SALE) }
            btnRent.onCheck { onCheckCallback(it, btnBuy, plRent, plBuy, RequestType.RENT) }
            btnSearch.onClick { openSearchScreen() }
        }
    }

    private fun onCheckCallback(
        isChecked: Boolean,
        scndBtn: RadioButton,
        pulseChecked: PulseLayout,
        pulseNotChecked: PulseLayout,
        type: RequestType
    ) {
        if (isChecked) {
            pulseChecked.startAnimation()
            pulseNotChecked.stopAnimation()
            scndBtn.isChecked = false
            viewModel.requestType.value = type
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
        DashboardFragmentDirections.openSearchScreen(filter, true).navigate()
    }

    private fun onFavoriteClick(data: SearchRequest) {
        viewModel.updateRequest(data.copy(favorite = !data.favorite))
    }

    private fun openSearchScreen() {
        val request = viewModel.buildSaleRequest()
        if (viewModel.checkFilters())
            DashboardFragmentDirections.openSearchScreen(request, false)
                .navigate()
    }
}