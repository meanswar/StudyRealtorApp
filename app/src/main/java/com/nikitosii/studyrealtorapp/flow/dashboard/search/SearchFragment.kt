package com.nikitosii.studyrealtorapp.flow.dashboard.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentSearchBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterAdapter
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.PropertyAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.hideWithScaleOut
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import com.nikitosii.studyrealtorapp.view.RangeView
import timber.log.Timber

@RequiresViewModel(SearchViewModel::class)
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>({
    FragmentSearchBinding.bind(it)
}, R.layout.fragment_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(activity).inflateTransition(android.R.transition.move)
    }

    private val args: SearchFragmentArgs by navArgs()
    private val propertiesAdapter by lazy {
        PropertyAdapter({ data, view ->
            openPropertyDetails(
                data,
                view
            )
        }, { onFavoriteClick(it) })
    }
    private val filtersAdapter by lazy { FilterAdapter { onHouseFilterClick(it) } }

    private fun onHouseFilterClick(house: HouseType): Boolean =
        viewModel.setFilterHouse(house)

    override fun initViews() {
        onClick()
        initRangeValues()
        with(binding) {
            rvProperties.adapter = propertiesAdapter
            toolbar.showEndButton()
            toolbar.initEndBtnAnimation(clFilters)
            svProperty.setText(args.propertyRequest.address)

            with(lFilters) {
                grFilters.show()
                rvFilterTypes.adapter = filtersAdapter
                filtersAdapter.setSelectedList(args.propertyRequest.houses)
                filtersAdapter.submitList(Constants.housesList)
            }
        }
        viewModel.setSearchRequest(args.propertyRequest)
        getPropertiesData()
    }

    private fun onClick() {
        with(binding) {
            btnAccept.onClick { viewModel.getPropertiesForSale() }
            svProperty.setOnTextChanged { viewModel.addressFilter.value = it }
            with(lFilters) {
                rvRangePrice.onRangeChanged { first, second -> onPriceChanged(first, second) }
                rvRangeBaths.onRangeChanged { first, second -> onBathsChanged(first, second) }
                rvRangeBeds.onRangeChanged { first, second -> onBedsChanged(first, second) }
                rvRangeSqft.onRangeChanged { first, second -> onSqftChanged(first, second) }
            }
        }
    }

    private fun onPriceChanged(minValue: Int, maxValue: Int) {
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.priceMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.priceMaxFilter.value = maxValue
    }

    private fun onBedsChanged(minValue: Int, maxValue: Int) {
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.bedsMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.bedsMaxFilter.value = maxValue
    }

    private fun onBathsChanged(minValue: Int, maxValue: Int) {
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.bathsMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.bathsMaxFilter.value = maxValue
    }

    private fun onSqftChanged(minValue: Int, maxValue: Int) {
        if (minValue != RangeView.RANGE_VALUE_ANY) viewModel.sqftMinFilter.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) viewModel.sqftMaxFilter.value = maxValue
    }

    private fun initRangeValues() {
        with(binding.lFilters) {
            rvRangePrice.initResult(args.propertyRequest.priceMin, args.propertyRequest.priceMax)
            rvRangeBaths.initResult(args.propertyRequest.bathsMin, args.propertyRequest.bathsMax)
            rvRangeBeds.initResult(args.propertyRequest.bedsMin, args.propertyRequest.bedsMax)
            rvRangeSqft.initResult(args.propertyRequest.sqftMin, args.propertyRequest.sqftMax)
        }
    }

    private fun getPropertiesData() {
        with(viewModel) {
            if (isDataAlreadyUploaded.value == false) {
                if (args.localRequest) getLocalProperties(args.propertyRequest)
                else getPropertiesForSale(args.propertyRequest)
                isDataAlreadyUploaded.postValue(true)
            }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            propertiesForSaleData.observe(viewLifecycleOwner, propertiesObserver)
            localProperties.observe(viewLifecycleOwner, localPropertiesObserver)
        }
    }

    private val propertiesObserver: Observer<WorkResult<Pair<SearchRequest, List<Property>>>> =
        Observer {
            showLoading(it.status == Status.LOADING)
            when (it.status) {
                Status.LOADING -> Timber.i("loading properties")
                Status.SUCCESS -> observeProperties(it.data)
                Status.ERROR -> handleException(it.exception) { openError() }
            }
        }

    private val localPropertiesObserver: Observer<WorkResult<List<Property>>> =
        Observer { observeProperties(it.data) }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) lavLoading.showWithAnimation(R.anim.scale_in)
            else lavLoading.hideWithScaleOut()
            btnAccept.isEnabled = !isLoading
        }
    }

    private fun observeProperties(data: Pair<SearchRequest, List<Property>>?) {
        observeProperties(data?.second)
        viewModel.updateSaleRequest(data?.first?.id)
    }

    private fun observeProperties(data: List<Property>?) {
        with(binding) {
            lavLoading.hide()
            propertiesAdapter.submitList(data)
            rvProperties.notifyDataSetChanged()
        }
    }

    private fun openPropertyDetails(property: Property, view: ImageView) {
        val extras = FragmentNavigatorExtras(
            view to view.transitionName
        )
        SearchFragmentDirections.openPropertyDetails(property).navigate(extras)
    }

    private fun onFavoriteClick(property: Property) {
        viewModel.onFavoriteClick(property)
    }
}