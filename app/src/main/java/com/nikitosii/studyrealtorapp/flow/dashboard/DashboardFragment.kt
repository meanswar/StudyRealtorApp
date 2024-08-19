package com.nikitosii.studyrealtorapp.flow.dashboard

import android.view.View
import android.widget.RadioButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.HouseType
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.core.source.local.model.request.RequestType
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentDashboardBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.adapter.DashboardRequestAdapter
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterAdapter
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.isNotNull
import com.nikitosii.studyrealtorapp.util.ext.model.getFullName
import com.nikitosii.studyrealtorapp.util.ext.onCheck
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import com.nikitosii.studyrealtorapp.util.view.PulseLayout
import com.nikitosii.studyrealtorapp.util.view.RangeView
import timber.log.Timber

@RequiresViewModel(DashboardViewModel::class)
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding, DashboardViewModel>(
        { FragmentDashboardBinding.bind(it) },
        R.layout.fragment_dashboard
    ) {
    private val recentSaleAdapter =
        DashboardRequestAdapter { view, data -> onSearchRequestClick(view, data) }
    private val recentRentAdapter =
        DashboardRequestAdapter { view, data -> onSearchRequestClick(view, data) }
    private val filterHousesAdapter = FilterAdapter { onHouseFilterClick(it) }
    private var isFirstRentLoad = true
    private var isFirstSaleLoad = true
    private val isEmptyDashboard = MutableLiveData(true)

    private fun onSearchRequestClick(view: View, data: SearchRequest) {
        when (view.id) {
            R.id.cvContent -> onSaleRequestClick(data)
            R.id.lavFavorite -> onFavoriteClick(data)
        }
    }

    override fun initViews() {
        with(binding) {
            rvRecentSaleSearches.adapter = recentSaleAdapter
            rvRecentRentSearches.adapter = recentRentAdapter
            filterHousesAdapter.submitList(Constants.housesList)
            svSearch.initEndAnimation(clFilters)

            with(lFilterAttributes) {
                rvFilterTypes.adapter = filterHousesAdapter
                rvRangePrice.onRangeChanged { first, second -> onPriceChanged(first, second) }
                rvRangeBaths.onRangeChanged { first, second -> onBathsChanged(first, second) }
                rvRangeBeds.onRangeChanged { first, second -> onBedsChanged(first, second) }
                rvRangeSqft.onRangeChanged { first, second -> onSqftChanged(first, second) }
            }
        }
        onClick()
        binding.clTopContent.showWithAnimation(R.anim.slide_in_anim_bottom)
        binding.clBottomContent.showWithAnimation(R.anim.slide_in_anim_top)
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

    override fun subscribe() {
        with(viewModel) {
            recentSaleRequests.observe(viewLifecycleOwner, recentSaleRequestsObserver)
            recentRentRequests.observe(viewLifecycleOwner, recentRentRequestsObserver)
            profile.observe(viewLifecycleOwner, profileObserver)
            isEmptyDashboard.observe(viewLifecycleOwner) { binding.grEmpty.show(it) }
            addressFilter.observe(viewLifecycleOwner) { binding.btnSearch.show(!it.isNullOrEmpty()) }
        }
    }

    private val recentSaleRequestsObserver: Observer<WorkResult<Status<List<SearchRequest>>>> =
        Observer {
            when (it.status) {
                SUCCESS -> processRecentSaleRequests(it.data?.obj ?: listOf())
                ERROR -> handleException(it.exception) { openError() }
                LOADING -> Timber.i("loading sale requests")
            }
        }

    private val recentRentRequestsObserver: Observer<WorkResult<Status<List<SearchRequest>>>> =
        Observer {
            when (it.status) {
                SUCCESS -> processRecentRentRequests(it.data?.obj ?: listOf())
                ERROR -> handleException(it.exception) { openError() }
                LOADING -> Timber.i("loading rent requests")
            }
        }

    private val profileObserver: Observer<WorkResult<Status<Profile>>> =
        Observer {
            when (it.status) {
                SUCCESS -> setProfileData(it.data?.obj)
                ERROR -> handleException(it.exception) { openError() }
                LOADING -> Timber.i("loading rent requests")
            }
        }

    private fun setProfileData(data: Profile?) {
        with(binding) {
            glideImage(data?.photo, ivProfile, R.drawable.ic_user_profile)
            tvUserWelcome.text =
                if (data?.name.isNotNull()) getString(
                    R.string.screen_dashboard_welcome_user,
                    data?.getFullName()
                )
                else getString(R.string.screen_dashboard_welcome_title)
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
                isEmptyDashboard.postValue(false)
                recentSaleAdapter.submitList(data)
                if (isFirstSaleLoad) {
                    isFirstSaleLoad = false
                    rvRecentSaleSearches.notifyDataSetChanged()
                }
            } else grRecentSaleContent.hide()
        }
    }

    private fun processRecentRentRequests(data: List<SearchRequest>) {
        with(binding) {
            if (data.isNotEmpty()) {
                isEmptyDashboard.postValue(false)
                recentRentAdapter.submitList(data)
                if (isFirstRentLoad) {
                    isFirstRentLoad = false
                    rvRecentRentSearches.notifyDataSetChanged()
                }
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
        DashboardFragmentDirections.openSearchScreen(request, false).navigate()
    }
}