package com.nikitosii.studyrealtorapp.flow.sales.filter

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.FragmentFilterBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.onAnimCompleted
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onTextChanged
import com.nikitosii.studyrealtorapp.util.ext.openKeyboard
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.view.ExpandableTextView
import timber.log.Timber
import java.lang.ref.WeakReference

@RequiresViewModel(FilterSalesViewModel::class)
class FilterSalesFragment : BaseFragment<FragmentFilterBinding, FilterSalesViewModel>(
    { FragmentFilterBinding.bind(it) }, R.layout.fragment_filter
) {

    private val _adapter = WeakReference(FilterAdapter { onHouseFilterClick(it) })
    private var lastOpenedFilter: ExpandableTextView? = null
    private val adapter: FilterAdapter?
        get() = _adapter.get()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        initAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
    }

    private fun initAnimation() {
        with(binding) {
            tvFilterHouses.initAnimation(rvContent)
            tvFilterPrices.initAnimation(rvFilterPrices)
            tvFilterSquare.initAnimation(rvFilterSquare)
            tvFilterBaths.initAnimation(rvFilterBaths)
            tvFilterBeds.initAnimation(rvFilterBeds)
        }
    }

    override fun initViews() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        with(binding) {
            adapter?.submitList(Constants.housesList.map { it.type })
            rvContent.adapter = adapter
            lFilter.etSearch.openKeyboard()
        }
    }

    private fun onClick() {
        with(binding) {
            btnBack.onClick { btnBack.playAnimation() }
            btnAccept.onClick { viewModel.findProperties() }
            btnBack.onAnimCompleted { navController.navigateUp() }
            tvFilterHouses.onArrowClick { checkOnClick(tvFilterHouses) }
            tvFilterPrices.onArrowClick { checkOnClick(tvFilterPrices) }
            tvFilterBeds.onArrowClick { checkOnClick(tvFilterBeds) }
            tvFilterSquare.onArrowClick { checkOnClick(tvFilterSquare) }
            tvFilterBaths.onArrowClick { checkOnClick(tvFilterBaths) }

            lFilter.etSearch.onTextChanged { viewModel.addressFilter.postValue(it) }
            rvFilterPrices.binding.etFrom.onTextChanged { viewModel.priceMinFilter.postValue(it.toInt()) }
            rvFilterPrices.binding.etTo.onTextChanged { viewModel.priceMaxFilter.postValue(it.toInt()) }
            rvFilterBaths.binding.etFrom.onTextChanged { viewModel.bathsMinFilter.postValue(it.toInt()) }
            rvFilterBaths.binding.etTo.onTextChanged { viewModel.bathsMaxFilter.postValue(it.toInt()) }
            rvFilterBeds.binding.etFrom.onTextChanged { viewModel.bedsMinFilter.postValue(it.toInt()) }
            rvFilterBeds.binding.etTo.onTextChanged { viewModel.bedsMaxFilter.postValue(it.toInt()) }
            rvFilterSquare.binding.etFrom.onTextChanged { viewModel.sqftMinFilter.postValue(it.toInt()) }
            rvFilterSquare.binding.etTo.onTextChanged { viewModel.sqftMaxFilter.postValue(it.toInt()) }
        }
    }

    private fun checkOnClick(view: ExpandableTextView) {
        if (lastOpenedFilter != view) lastOpenedFilter?.hideIfExpanded()
        lastOpenedFilter = view
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

    private fun onHouseFilterClick(house: String): Boolean = viewModel.setFilterHouse(house)

    private fun observeProperties(data: List<Property>?) {

    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            gMainContent.show(!isLoading)
            lavLoading.show(isLoading)
            btnAccept.isEnabled = !isLoading
            if (isLoading) lavLoading.playAnimation() else lavLoading.pauseAnimation()
        }
    }

}