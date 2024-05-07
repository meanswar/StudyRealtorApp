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
import com.nikitosii.studyrealtorapp.databinding.FragmentSearchBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterAdapter
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.PropertyAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
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
        PropertyAdapter { data, view ->
            openPropertyDetails(
                data,
                view
            )
        }
    }
    private val filtersAdapter by lazy { FilterAdapter { onHouseFilterClick(it) } }

    private fun onHouseFilterClick(house: HouseType): Boolean =
        viewModel.setFilterHouse(house)

    override fun initViews() {
        with(binding) {
            rvProperties.adapter = propertiesAdapter
            toolbar.showEndButton()
            toolbar.initEndBtnAnimation(clFilters)
            with(lFilters) {
                rvFilterTypes.show()
                rvRangePrice.show()
                rvRangeBeds.show()
                rvRangeBaths.show()
                rvRangeSqft.show()

                rvRangePrice.onRangeChanged { first, second -> onPriceChanged(first, second) }
                rvRangeBaths.onRangeChanged { first, second -> onBathsChanged(first, second) }
                rvRangeBeds.onRangeChanged { first, second -> onBedsChanged(first, second) }
                rvRangeSqft.onRangeChanged { first, second -> onSqftChanged(first, second) }

                rvFilterTypes.adapter = filtersAdapter
                filtersAdapter.submitList(Constants.housesList)
            }
        }
        getPropertiesData()
    }

    private fun onClick() {
        with(binding) {
            btnAccept.onClick { viewModel.getProperties() }
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

    private fun getPropertiesData() {
        with(viewModel) {
            if (isDataAlreadyUploaded.value == false) {
                if (args.localRequest) getLocalSaleProperties(args.propertyRequest)
                else getProperties(args.propertyRequest)
                isDataAlreadyUploaded.postValue(true)
            }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            properties.observe(viewLifecycleOwner, propertiesObserver)
        }
    }

    private val propertiesObserver: Observer<WorkResult<List<Property>>> = Observer {
        showLoading(it.status == Status.LOADING)
        when (it.status) {
            Status.LOADING -> Timber.i("loading properties")
            Status.SUCCESS -> observeProperties(it.data)
            Status.ERROR -> handleException(it.exception) { openError() }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) lavLoading.showWithAnimation(R.anim.scale_in)
            btnAccept.isEnabled = !isLoading
        }
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
}