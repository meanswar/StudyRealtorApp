package com.nikitosii.studyrealtorapp.flow.profile.properties

import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.lifecycle.Observer
import com.google.android.material.tabs.TabLayout
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
import com.nikitosii.studyrealtorapp.util.ext.addTabs
import com.nikitosii.studyrealtorapp.util.ext.onTabClick
import com.nikitosii.studyrealtorapp.util.ext.show
import timber.log.Timber

@RequiresViewModel(ProfilePropertiesViewModel::class)
class ProfilePropertiesFragment : BaseFragment<FragmentHistoryBinding, ProfilePropertiesViewModel>(
    { FragmentHistoryBinding.bind(it) }, R.layout.fragment_history
) {

    private val onFavoriteClick: (Property) -> Unit = { viewModel.updateProperty(it) }

    private val onPropertyClick: (Property, ImageView) -> Unit =
        { data, view -> openPropertyDetails(data) }

    private val adapter = PropertyAdapter(onPropertyClick, onFavoriteClick)

    override fun initViews() {
        with(binding) {
            rvContent.adapter = adapter
            tlSortingFilters.onTabClick({ onTabClick(it) }, { onTabReselectedClick() })
            tlSortingFilters.addTabs(TABS)
        }
    }

    private fun onTabClick(tab: TabLayout.Tab) {
        val properties = viewModel.properties.value ?: return
        viewModel.properties.postValue(
            when (tab.text.toString()) {
                TAB_NAME -> properties.sortedBy { it.description?.name }
                TAB_TYPE -> properties.sortedBy { it.description?.type }
                TAB_PRICE -> properties.sortedBy { it.listPrice }
                TAB_BEDS -> properties.sortedBy { it.description?.beds }
                TAB_SQFT -> properties.sortedBy { it.description?.sqft }
                TAB_FAVORITE -> properties.sortedByDescending { it.favorite }
                else -> return
            }
        )
    }

    private fun onTabReselectedClick() {
        val properties = viewModel.properties.value?.reversed() ?: return
        viewModel.properties.postValue(properties)
    }

    override fun subscribe() {
        with(viewModel) {
            propertiesNetwork.observe(viewLifecycleOwner, propertiesNetworkObserver)
            properties.observe(viewLifecycleOwner) { setProperties(it) }
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

    private fun setProperties(data: List<Property>) {
        adapter.submitList(data)
        Handler(Looper.getMainLooper()).postDelayed({ binding.rvContent.scrollToPosition(0) },
            500)
    }

    private fun onLoading(isLoading: Boolean) {
        binding.lavLoading.show(isLoading)
        binding.tlSortingFilters.show(!isLoading)
    }

    private fun openPropertyDetails(property: Property) {
        ProfileViewPagerFragmentDirections.openPropertyDetails(property).navigate()
    }

    companion object {
        const val SCREEN_TITLE = "Properties"
        private const val TAB_NAME = "Name"
        private const val TAB_TYPE = "Type"
        private const val TAB_PRICE = "Price"
        private const val TAB_BEDS = "Beds"
        private const val TAB_FAVORITE = "Favorite"
        private const val TAB_SQFT = "Sqft"

        private val TABS = listOf(
            TAB_NAME,
            TAB_TYPE,
            TAB_PRICE,
            TAB_BEDS,
            TAB_SQFT,
            TAB_FAVORITE
        )
    }
}