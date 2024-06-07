package com.nikitosii.studyrealtorapp.flow.profile.requests

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.ERROR
import com.nikitosii.studyrealtorapp.core.domain.Status.LOADING
import com.nikitosii.studyrealtorapp.core.domain.Status.SUCCESS
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.request.SearchRequest
import com.nikitosii.studyrealtorapp.databinding.FragmentHistoryBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.SearchRequestAdapter
import com.nikitosii.studyrealtorapp.flow.profile.ProfileViewPagerFragmentDirections
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.show
import timber.log.Timber

@RequiresViewModel(ProfileRequestsViewModel::class)
class ProfileRequestsFragment : BaseFragment<FragmentHistoryBinding, ProfileRequestsViewModel>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
) {

    private val adapter = SearchRequestAdapter({ view, data -> onRequestClick(view, data) }, true)

    private fun onRequestClick(view: View, data: SearchRequest) {
        when (view.id) {
            R.id.cvFavorite -> viewModel.updateRequest(data)
            R.id.cvContent -> openSearchRequestDetails(data)
            R.id.cvTrash -> viewModel.removeRequest(data.id ?: return)
            R.id.mlContent -> binding.rvContent.apply { isLayoutFrozen = !isLayoutFrozen }
        }
    }

    override fun initViews() {
        with(binding) {
            rvContent.adapter = adapter
        }
    }

    override fun subscribe() {
        with(viewModel) {
            requestsNetwork.observe(viewLifecycleOwner, searchRequestsObserver)
            requests.observe(viewLifecycleOwner) { onDataSet(it) }
        }
    }

    private val searchRequestsObserver: Observer<WorkResult<Status<List<SearchRequest>>>> =
        Observer {
            onLoading(it.status == LOADING)
            when (it.status) {
                SUCCESS -> viewModel.requests.postValue(it.data?.obj)
                ERROR -> handleException(it.exception)
                LOADING -> Timber.i("loading agents")
            }
        }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            lavLoading.show(isLoading)
        }
    }

    private fun onDataSet(data: List<SearchRequest>) {
        adapter.submitList(data)
        Handler(Looper.getMainLooper()).postDelayed({ binding.rvContent.scrollToPosition(0) }, 500)
    }

    private fun openSearchRequestDetails(request: SearchRequest) {
        ProfileViewPagerFragmentDirections.openSearchRequestDetails(request, true).navigate()
    }

    companion object {
        const val SCREEN_TITLE = "Requests"
    }
}