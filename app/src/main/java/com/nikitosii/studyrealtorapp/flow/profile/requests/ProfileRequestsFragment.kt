package com.nikitosii.studyrealtorapp.flow.profile.requests

import androidx.lifecycle.Observer
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.*
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

    private val onFavoriteClick: (SearchRequest) -> Unit = { viewModel.updateRequest(it) }

    private val onRequestClick: (SearchRequest) -> Unit = { data -> openSearchRequestDetails(data) }

    private val adapter = SearchRequestAdapter(onRequestClick, onFavoriteClick, true)

    override fun initViews() {
        with(binding) {
            rvContent.adapter = adapter
        }
    }

    override fun subscribe() {
        viewModel.properties.observe(viewLifecycleOwner, searchRequestsObserver)
    }

    private val searchRequestsObserver: Observer<WorkResult<Status<List<SearchRequest>>>> = Observer {
        onLoading(it.status == LOADING)
        when (it.status) {
            SUCCESS -> adapter.submitList(it.data?.obj)
            ERROR -> handleException(it.exception)
            LOADING -> Timber.i("loading agents")
        }
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            lavLoading.show(isLoading)
        }
    }

    private fun openSearchRequestDetails(request: SearchRequest) {
        ProfileViewPagerFragmentDirections.openSearchRequestDetails(request, true).navigate()
    }

    companion object {
        const val SCREEN_TITLE = "Requests"
    }
}