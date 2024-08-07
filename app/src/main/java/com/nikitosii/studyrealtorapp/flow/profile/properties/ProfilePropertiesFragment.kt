package com.nikitosii.studyrealtorapp.flow.profile.properties

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status.*
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.channel.Status
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.FragmentHistoryBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.PropertyAdapter
import com.nikitosii.studyrealtorapp.flow.profile.ProfileViewPagerFragmentDirections
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.onItemSwipe
import com.nikitosii.studyrealtorapp.util.ext.show
import timber.log.Timber

@RequiresViewModel(ProfilePropertiesViewModel::class)
class ProfilePropertiesFragment : BaseFragment<FragmentHistoryBinding, ProfilePropertiesViewModel>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
) {

    private val onFavoriteClick: (Property) -> Unit = { viewModel.updateProperty(it) }

    private val onPropertyClick: (Property) -> Unit = { openPropertyDetails(it) }

    private val adapter = PropertyAdapter(onPropertyClick, onFavoriteClick)

    override fun initViews() {
        with(binding) {
            rvContent.adapter = adapter
            rvContent.onItemSwipe(ItemTouchHelper.LEFT) {
                val list = adapter.currentList.toMutableList()
                val id = it.adapterPosition
                val removedProperty = adapter.currentList[id]
                list.removeAt(id)
                adapter.submitList(list)
                viewModel.removePropertyById(removedProperty.propertyId)
            }
        }
    }

    override fun subscribe() {
        with(viewModel) {
            propertiesNetwork.observe(viewLifecycleOwner, propertiesNetworkObserver)
            properties.observe(viewLifecycleOwner) { adapter.submitList(it) }
        }
    }

    private val propertiesNetworkObserver: Observer<WorkResult<Status<List<Property>>>> = Observer {
        onLoading(it.status == LOADING)
        when (it.status) {
            SUCCESS -> viewModel.properties.postValue(it.data?.obj)
            ERROR -> handleException(it.exception)
            LOADING -> Timber.i("loading agents")
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.lavLoading.show(isLoading)
    }

    private fun openPropertyDetails(property: Property) {
        ProfileViewPagerFragmentDirections.openPropertyDetails(property).navigate()
    }

    companion object {
        const val SCREEN_TITLE = "Properties"
    }
}