package com.nikitosii.studyrealtorapp.flow.sales.filter

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SalesRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentFilterBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.sales.SaleRequestAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.dividerVertical
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.hideWithAnim
import com.nikitosii.studyrealtorapp.util.ext.hideWithScaleOut
import com.nikitosii.studyrealtorapp.util.ext.invisible
import com.nikitosii.studyrealtorapp.util.ext.onAnimCompleted
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.onTextChanged
import com.nikitosii.studyrealtorapp.util.ext.openKeyboard
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import com.nikitosii.studyrealtorapp.util.ext.showWithScaleIn
import com.nikitosii.studyrealtorapp.util.ext.toInteger
import com.nikitosii.studyrealtorapp.view.ExpandableTextView
import timber.log.Timber
import java.lang.ref.WeakReference

@RequiresViewModel(FilterSalesViewModel::class)
class FilterSalesFragment : BaseFragment<FragmentFilterBinding, FilterSalesViewModel>(
    { FragmentFilterBinding.bind(it) }, R.layout.fragment_filter
) {

    private val _adapter = WeakReference(FilterAdapter { onHouseFilterClick(it) })
    private val recentSearchesAdapter = SaleRequestAdapter { viewModel.getLocalProperties(it) }
    private val args: FilterSalesFragmentArgs by navArgs()
    private var lastOpenedFilter: ExpandableTextView? = null
    private val adapter: FilterAdapter?
        get() = _adapter.get()

    private val salesPropertiesAdapter by lazy { SalesAdapter { openPropertyDetails(it) } }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onClick()
        initAnimation()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (args.filter == null) {
            sharedElementEnterTransition =
                TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
        }
    }

    private fun initAnimation() {
        with(binding) {
            tvFilterHouses.initAnimation(rvHouseTypes)
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
            adapter?.submitList(Constants.housesList)
            rvHouseTypes.adapter = adapter
            if (args.filter == null) lFilter.etSearch.openKeyboard()
            rvSaleProperties.adapter = salesPropertiesAdapter
            rvSaleProperties.dividerVertical(R.drawable.divider_vertical_10dp)
            rvRecentSearches.adapter = recentSearchesAdapter
        }
        args.filter?.let { viewModel.getLocalProperties(it) }
    }

    private fun onClick() {
        with(binding) {
            btnBack.onClick { btnBack.playAnimation() }
            btnAccept.onClick {
                lastOpenedFilter?.hideIfExpanded()
                viewModel.findProperties()
            }
            btnBack.onAnimCompleted { navController.navigateUp() }
            tvFilterHouses.onArrowClick { checkOnClick(tvFilterHouses) }
            tvFilterPrices.onArrowClick { checkOnClick(tvFilterPrices) }
            tvFilterBeds.onArrowClick { checkOnClick(tvFilterBeds) }
            tvFilterSquare.onArrowClick { checkOnClick(tvFilterSquare) }
            tvFilterBaths.onArrowClick { checkOnClick(tvFilterBaths) }

            lFilter.etSearch.onTextChanged { checkFilters(); viewModel.addressFilter.postValue(it) }
            rvFilterPrices.binding.etFrom.onTextChanged {
                checkFilters(); viewModel.priceMinFilter.postValue(
                it.toInteger()
            )
            }
            rvFilterPrices.binding.etTo.onTextChanged {
                checkFilters(); viewModel.priceMaxFilter.postValue(
                it.toInteger()
            )
            }
            rvFilterBaths.binding.etFrom.onTextChanged {
                checkFilters(); viewModel.bathsMinFilter.postValue(
                it.toInteger()
            )
            }
            rvFilterBaths.binding.etTo.onTextChanged {
                checkFilters(); viewModel.bathsMaxFilter.postValue(
                it.toInteger()
            )
            }
            rvFilterBeds.binding.etFrom.onTextChanged {
                checkFilters(); viewModel.bedsMinFilter.postValue(
                it.toInteger()
            )
            }
            rvFilterBeds.binding.etTo.onTextChanged {
                checkFilters(); viewModel.bedsMaxFilter.postValue(
                it.toInteger()
            )
            }
            rvFilterSquare.binding.etFrom.onTextChanged {
                checkFilters(); viewModel.sqftMinFilter.postValue(
                it.toInteger()
            )
            }
            rvFilterSquare.binding.etTo.onTextChanged {
                checkFilters(); viewModel.sqftMaxFilter.postValue(
                it.toInteger()
            )
            }
        }
    }

    private fun checkOnClick(view: ExpandableTextView) {
        if (lastOpenedFilter != view) lastOpenedFilter?.hideIfExpanded()
        lastOpenedFilter = view
    }

    private fun checkFilters() {
        with(binding.cvAccept) {
            if (viewModel.checkFilters()) showWithScaleIn()
            else hideWithScaleOut()
        }
    }

    override fun subscribe() {
        with(viewModel) {
            properties.observe(viewLifecycleOwner, propertiesObserver)
            recentSearches.observe(viewLifecycleOwner, recentSearchesObserver)
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

    private val recentSearchesObserver: Observer<com.nikitosii.studyrealtorapp.core.source.channel.Status<List<SalesRequest>>> =
        Observer {
            recentSearchesAdapter.submitList(it.obj)
            binding.rvRecentSearches.showWithScaleIn()
            binding.rvRecentSearches.notifyDataSetChanged()
        }

    private fun onHouseFilterClick(house: HouseType): Boolean =
        viewModel.setFilterHouse(house.apiType)

    private fun observeProperties(data: List<Property>?) {
        with(binding) {
            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({ lavLoading.hideWithScaleOut() }, 1500)
            salesPropertiesAdapter.submitList(data)
            rvSaleProperties.notifyDataSetChanged()
            rvSaleProperties.show()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                gMainContent.hideWithAnim(R.anim.scale_out)
                lavLoading.showWithAnimation(R.anim.scale_in)
            }
            btnAccept.isEnabled = !isLoading
            if (isLoading) lavLoading.playAnimation()
        }
    }

    override fun openError(errorText: String, isHiding: Boolean) {
        super.openError(errorText, isHiding)
        with(binding) {
            lavLoading.hideWithAnim(R.anim.scale_out)
            gMainContent.showWithAnimation(R.anim.scale_in)
        }
    }

    fun openPropertyDetails(property: Property) {

    }

}