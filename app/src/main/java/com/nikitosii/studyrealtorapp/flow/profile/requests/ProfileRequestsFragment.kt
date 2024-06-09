package com.nikitosii.studyrealtorapp.flow.profile.requests

import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
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
import com.nikitosii.studyrealtorapp.util.ext.onItemSwipe
import com.nikitosii.studyrealtorapp.util.ext.show
import timber.log.Timber

@RequiresViewModel(ProfileRequestsViewModel::class)
class ProfileRequestsFragment : BaseFragment<FragmentHistoryBinding, ProfileRequestsViewModel>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
) {

    private val adapter = SearchRequestAdapter { view, data -> onRequestClick(view, data) }

    private fun onRequestClick(view: View, data: SearchRequest) {
        when (view.id) {
            R.id.lavFavorite -> viewModel.updateRequest(data)
            R.id.cvContent -> openSearchRequestDetails(data)
            R.id.cvTrash -> viewModel.removeRequest(data.id ?: return)
        }
    }

    override fun initViews() {
        with(binding) {
            rvContent.adapter = adapter
            rvContent.onItemSwipe(ItemTouchHelper.LEFT) {
                val list = adapter.currentList.toMutableList()
                val id = it.adapterPosition
                val removedRequest = adapter.currentList[id]
                list.removeAt(id)
                adapter.submitList(list)
                viewModel.removeRequest(removedRequest.id ?: return@onItemSwipe)
            }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            requestsNetwork.observe(viewLifecycleOwner, searchRequestsObserver)
            requests.observe(viewLifecycleOwner) { adapter.submitList(it) }
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

    private fun openSearchRequestDetails(request: SearchRequest) {
        ProfileViewPagerFragmentDirections.openSearchRequestDetails(request, true).navigate()
    }

    companion object {
        const val SCREEN_TITLE = "Requests"
    }
}