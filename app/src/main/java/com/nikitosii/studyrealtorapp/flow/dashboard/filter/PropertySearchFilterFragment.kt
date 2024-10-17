package com.nikitosii.studyrealtorapp.flow.dashboard.filter

import android.widget.RadioButton
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.databinding.FragmentPropertySearchFilterBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.onCheck
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.view.PulseLayout
import com.nikitosii.studyrealtorapp.util.view.RangeView

@RequiresViewModel(PropertySearchFilterViewModel::class)
class PropertySearchFilterFragment : BaseFragment<FragmentPropertySearchFilterBinding, PropertySearchFilterViewModel>(
    { FragmentPropertySearchFilterBinding.bind(it) }, R.layout.fragment_property_search_filter
) {

    private val filterHousesAdapter = FilterAdapter { onHouseFilterClick(it) }

    override fun initViews() {
        with(binding) {
            svSearch.initEndAnimation(lFilterAttributes.root)
            filterHousesAdapter.submitList(Constants.housesList)

            with(lFilterAttributes) {
                rvFilterTypes.adapter = filterHousesAdapter
                rvRangePrice.onRangeChanged { first, second -> onPriceChanged(first, second) }
                rvRangeBaths.onRangeChanged { first, second -> onBathsChanged(first, second) }
                rvRangeBeds.onRangeChanged { first, second -> onBedsChanged(first, second) }
                rvRangeSqft.onRangeChanged { first, second -> onSqftChanged(first, second) }
            }
        }
        onClick()
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

    private fun onHouseFilterClick(house: HouseType): Boolean {
        with(viewModel) {
            val result = viewModel.setFilterHouse(house)
            binding.ivHouse.setIsFilled(isFilterHousesFilled())
            return result
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

    override fun subscribe() {
    }

    private fun  openSearchScreen() {
        val request = viewModel.buildSearchRequest()
        PropertySearchFilterFragmentDirections.openSearchScreen(request).apply {
            isLocalSearch = false
        }.navigate()
    }
}