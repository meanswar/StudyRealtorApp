package com.nikitosii.studyrealtorapp.flow.dashboard

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.profile.Profile
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentDashboardBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.adapter.DashboardRequestAdapter
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.isNotNull
import com.nikitosii.studyrealtorapp.util.ext.model.getFullName
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
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
        }
        onClick()
    }

    private fun onClick() {
        with(binding) {
            btnSearch.onClick { openSearchScreen() }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            recentSaleRequests.observe(viewLifecycleOwner, recentSaleRequestsObserver)
            recentRentRequests.observe(viewLifecycleOwner, recentRentRequestsObserver)
            profile.observe(viewLifecycleOwner, profileObserver)
            isEmptyDashboard.observe(viewLifecycleOwner) { binding.grEmpty.show(it) }
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

    private fun processRecentSaleRequests(data: List<SearchRequest>) {
        with(binding) {
            if (data.isNotEmpty()) {
                isEmptyDashboard.postValue(false)
                recentSaleAdapter.submitList(data)
            } else grRecentSaleContent.hide()
        }
    }

    private fun processRecentRentRequests(data: List<SearchRequest>) {
        with(binding) {
            if (data.isNotEmpty()) {
                isEmptyDashboard.postValue(false)
                recentRentAdapter.submitList(data)
            } else grRecentRentContent.hide()
        }
    }

    private fun onSaleRequestClick(filter: SearchRequest) {
        DashboardFragmentDirections.openSearchScreen(filter).navigate()
    }

    private fun onFavoriteClick(data: SearchRequest) {
        viewModel.updateRequest(data.copy(favorite = !data.favorite))
    }

    private fun openSearchScreen() {
        DashboardFragmentDirections.openPropertySearchFilterScreen().navigate()
    }
}