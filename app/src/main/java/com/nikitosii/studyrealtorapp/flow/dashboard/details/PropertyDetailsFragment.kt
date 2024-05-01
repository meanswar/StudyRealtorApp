package com.nikitosii.studyrealtorapp.flow.dashboard.details

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.databinding.FragmentPropertyDetailsBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.details.adapter.PropertyImageAdapter
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.DateExt.SERVER_YEAR_MONTH_DAY_TIME_PATTERN
import com.nikitosii.studyrealtorapp.util.ext.DateExt.UI_DATE_PATTERN_WITH_TIME_AND_SPACE
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.model.getName
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText
import com.nikitosii.studyrealtorapp.util.ext.showWithScaleIn
import com.nikitosii.studyrealtorapp.util.ext.toUiTime
import timber.log.Timber

@RequiresViewModel(PropertyDetailsViewModel::class)
class PropertyDetailsFragment :
    BaseFragment<FragmentPropertyDetailsBinding, PropertyDetailsViewModel>(
        { FragmentPropertyDetailsBinding.bind(it) },
        R.layout.fragment_property_details
    ) {

    private val args: PropertyDetailsFragmentArgs by navArgs()
    private val adapter = PropertyImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
//        enterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
//        exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        with(binding) {
            glideImage(args.property.primaryPhoto.url, ivPropertyImage)
            setPropertyDescriptionFilters(args.property.description)
            setBrandingInfo(args.property.branding?.first())
            tvPropertyAddress.text = args.property.getName()
            tvPropertyPrice.text =
                getString(R.string.view_sale_property_description_price, args.property.listPrice)
            if (args.property.lastUpdateDate != null) {
                tvSalePropertyTime.text = getString(
                    R.string.screen_sale_property_details_property_description_last_update_title,
                    args.property.lastUpdateDate.toUiTime(
                        SERVER_YEAR_MONTH_DAY_TIME_PATTERN,
                        UI_DATE_PATTERN_WITH_TIME_AND_SPACE
                    )
                )
                tvSalePropertyTime.show()
            }
        }
//        args.property.propertyId?.let { viewModel.getPropertyDetails(it) }
    }

    private fun setPropertyDescriptionFilters(data: Description?) {
        with(binding) {
            tvFilterBaths.showText(data?.baths.toString())
            tvGarage.showText(data?.garage.toString())
            tvFilterSqft.showText(data?.sqft.toString())
            tvBeds.showText(data?.beds.toString())
            tvRooms.showText(data?.rooms.toString())
        }
    }

    private fun setBrandingInfo(data: Branding?) {
        with(binding) {
            if (data?.photo?.isNotEmpty() == true) glideImage(data.photo, ivBrandingLogo)
            else tvBranding.showText(data?.name)
        }
    }

    override fun subscribe() {
        with(viewModel) {
            property.observe(viewLifecycleOwner, propertyObserver)
        }
    }

    private val propertyObserver: Observer<WorkResult<PropertyDetails>> = Observer {
        when (it.status) {
            Status.SUCCESS -> setPropertyDetailsData(it.data)
            Status.LOADING -> Timber.i("loading property details")
            Status.ERROR -> handleException(it.exception) { openError() }
        }
    }

    private fun setPropertyDetailsData(property: PropertyDetails?) {
        with(binding) {
            setPropertyDescriptionFilters(property?.description)
            tvPropertyDescription.text = property?.description?.text
            tvPropertyStatus.text = property?.status
        }
    }
}