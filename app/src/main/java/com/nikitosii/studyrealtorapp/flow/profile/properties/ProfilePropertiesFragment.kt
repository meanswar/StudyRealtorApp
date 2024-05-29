package com.nikitosii.studyrealtorapp.flow.profile.properties

import android.widget.ImageView
import androidx.lifecycle.Observer
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
        }
    }

    override fun subscribe() {
        viewModel.properties.observe(viewLifecycleOwner, propertiesObserver)
    }

    private val propertiesObserver: Observer<WorkResult<Status<List<Property>>>> = Observer {
        onLoading(it.status == LOADING)
        when (it.status) {
            SUCCESS -> adapter.submitList(it.data?.obj)
            ERROR -> handleException(it.exception)
            LOADING -> Timber.i("loading agents")
        }
    }

    private fun onLoading(isLoading: Boolean) { binding.lavLoading.show(isLoading) }

    private fun openPropertyDetails(property: Property) {
        ProfileViewPagerFragmentDirections.openPropertyDetails(property).navigate()
    }

    companion object {
        const val SCREEN_TITLE = "Properties"
    }
}