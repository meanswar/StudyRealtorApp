package com.nikitosii.studyrealtorapp.flow.dashboard.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.nikitosii.studyrealtorapp.util.ext.hideWithScaleOut
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import com.nikitosii.studyrealtorapp.util.view.RangeView
import com.nikitosii.studyrealtorapp.util.view.recyclerview.PaginatingScrollListener
import timber.log.Timber

@RequiresViewModel(SearchViewModel::class)
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>({
    FragmentSearchBinding.bind(it)
}, R.layout.fragment_search) {

    private val args: SearchFragmentArgs by navArgs()
    private val isFirstItemsLoading = MutableLiveData(true)
    private val isLoadingData = MutableLiveData(false)

    private val propertiesAdapter by lazy {
        PropertyAdapter(
            { openPropertyDetails(it) },
            { onFavoriteClick(it) }
        )
    }
    private val filtersAdapter by lazy { FilterAdapter { onHouseFilterClick(it) } }

    private fun onHouseFilterClick(house: HouseType): Boolean = viewModel.setFilterHouse(house)

    override fun initViews() {
        onClick()
        initRangeValues()
        initRecyclerViewSettings()
        with(binding) {
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
            btnAccept.onClick { onSearch() }
            svProperty.setOnTextChanged { viewModel.addressFilter.value = it }
            with(lFilters) {
                rvRangePrice.onRangeChanged { first, second -> onPriceChanged(first, second) }
                rvRangeBaths.onRangeChanged { first, second -> onBathsChanged(first, second) }
                rvRangeBeds.onRangeChanged { first, second -> onBedsChanged(first, second) }
                rvRangeSqft.onRangeChanged { first, second -> onSqftChanged(first, second) }
            }
        }
    }

    private fun onSearch() {
        propertiesAdapter.submitList(listOf())
        isFirstItemsLoading.value = true
        viewModel.getPropertiesFromNetwork()
    }

    private fun initRecyclerViewSettings() {
        with(binding) {
            rvProperties.adapter = propertiesAdapter
            rvProperties.addOnScrollListener(object :
                PaginatingScrollListener(rvProperties.layoutManager as LinearLayoutManager) {
                override fun loadMoreItems() = viewModel.getPropertiesFromNetwork()


                override fun isLoading(): Boolean = isLoadingData.value == true

                override fun isLastPage(): Boolean = viewModel.isEmptyResponse()
            })
        }
    }

    private fun onPriceChanged(minValue: Int, maxValue: Int) {
        onValueFilterChanged(minValue, maxValue, viewModel.priceMinFilter, viewModel.priceMaxFilter)
    }

    private fun onBedsChanged(minValue: Int, maxValue: Int) {
        onValueFilterChanged(minValue, maxValue, viewModel.bedsMinFilter, viewModel.bedsMaxFilter)
    }

    private fun onBathsChanged(minValue: Int, maxValue: Int) {
        onValueFilterChanged(minValue, maxValue, viewModel.bathsMinFilter, viewModel.bathsMaxFilter)
    }

    private fun onSqftChanged(minValue: Int, maxValue: Int) {
        onValueFilterChanged(minValue, maxValue, viewModel.sqftMinFilter, viewModel.sqftMaxFilter)
    }

    private fun onValueFilterChanged(
        minValue: Int,
        maxValue: Int,
        containerMin: MutableLiveData<Int>,
        containerMax: MutableLiveData<Int>
    ) {
        if (minValue != RangeView.RANGE_VALUE_ANY) containerMin.value = minValue
        if (maxValue != RangeView.RANGE_VALUE_ANY) containerMax.value = minValue
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
                else getPropertiesFromNetwork()
                isDataAlreadyUploaded.postValue(true)
            }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            properties.observe(viewLifecycleOwner, propertiesObserver)
            localProperties.observe(viewLifecycleOwner, localPropertiesObserver)
            updatedProperty.observe(viewLifecycleOwner, updatedPropertyObserver)
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

    private val updatedPropertyObserver: Observer<Property> = Observer { updatedData ->
        val idInAdapter =
            propertiesAdapter.currentList.indexOfFirst { it.propertyId == updatedData.propertyId }
        val data = mutableListOf(propertiesAdapter.currentList).flatten().toMutableList()
        data.replaceAll { if (it.propertyId == updatedData.propertyId) updatedData else it }
        propertiesAdapter.submitList(data)
        propertiesAdapter.notifyItemChanged(idInAdapter)
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            isLoadingData.postValue(isLoading)
            if (isLoading) lavLoading.showWithAnimation(R.anim.scale_in)
            else lavLoading.hideWithScaleOut()
            btnAccept.isEnabled = !isLoading
        }
    }

    private fun observeProperties(data: Pair<SearchRequest, List<Property>>?) {
        observeProperties(data?.second)
        viewModel.setRequestId(data?.first?.id)
    }

    private fun observeProperties(data: List<Property>?) {
        with(binding) {
            if (data?.isEmpty() == true) viewModel.setIsEmptyResponse(true)
            else viewModel.incrementPage()

            val newList = mutableListOf<Property>().apply {
                addAll(propertiesAdapter.currentList)
                addAll(data ?: emptyList())
            }
            propertiesAdapter.submitList(newList)
            if (isFirstItemsLoading.value == true) {
                rvProperties.notifyDataSetChanged()
                isFirstItemsLoading.postValue(false)
            }
        }
    }

    private fun openPropertyDetails(property: Property) {
        viewModel.openedPropertyId.value = property.propertyId
        viewModel.setNeedToUpdateLocalProperty(true)
        SearchFragmentDirections.openPropertyDetails(property).navigate()
    }

    private fun onFavoriteClick(property: Property) {
        viewModel.onFavoriteClick(property)
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isNeedToUpdateLocalProperty()) getUpdatedLocalProperty()
    }

    private fun getUpdatedLocalProperty() {
        viewModel.setNeedToUpdateLocalProperty(false)
        viewModel.getUpdatedProperty()
    }
}