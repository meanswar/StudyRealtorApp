package com.nikitosii.studyrealtorapp.flow.details

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Branding
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.Description
import com.nikitosii.studyrealtorapp.core.source.local.model.property_details.PropertyDetails
import com.nikitosii.studyrealtorapp.databinding.FragmentPropertyDetailsBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.details.adapter.PropertyDetailsAdapter
import com.nikitosii.studyrealtorapp.flow.details.adapter.PropertyImageAdapter
import com.nikitosii.studyrealtorapp.flow.details.adapter.schools.SchoolInfoAdapter
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.DateExt.SERVER_YEAR_MONTH_DAY_TIME_PATTERN
import com.nikitosii.studyrealtorapp.util.ext.DateExt.UI_DATE_PATTERN_WITH_TIME_AND_SPACE
import com.nikitosii.studyrealtorapp.util.ext.attachPagerSnap
import com.nikitosii.studyrealtorapp.util.ext.callIntent
import com.nikitosii.studyrealtorapp.util.ext.emailIntent
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.model.getAddress
import com.nikitosii.studyrealtorapp.util.ext.model.getPriceStringFormat
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText
import com.nikitosii.studyrealtorapp.util.ext.toUiTime
import timber.log.Timber

@RequiresViewModel(PropertyDetailsViewModel::class)
class PropertyDetailsFragment :
    BaseFragment<FragmentPropertyDetailsBinding, PropertyDetailsViewModel>(
        { FragmentPropertyDetailsBinding.bind(it) },
        R.layout.fragment_property_details
    ), OnMapReadyCallback {

    private val args: PropertyDetailsFragmentArgs by navArgs()

    private val imageAdapter =
        PropertyImageAdapter { id, view -> openPropertyPhotosScreen(id, view) }
    private val propertyDetailsAdapter = PropertyDetailsAdapter()
    private val schoolInfoAdapter by lazy { SchoolInfoAdapter() }

    private lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
    }

    override fun initViews() {
        with(binding) {
            postponeEnterTransition()
            viewModel.setLocalProperty(args.property)
            ivProperty.transitionName = args.property.propertyId
            glideImage(args.property.primaryPhoto.url, ivProperty)
            setPropertyDescriptionInfo(args.property.description)
            setBrandingInfo(args.property.branding?.firstOrNull())
            setFavorite()
            tvPropertyAddress.text = "Address: ${args.property.getAddress()}"
            tvPropertyPrice.text = args.property.getPriceStringFormat()

            if (args.property.lastUpdateDate != null) {
                tvSalePropertyTime.text = getString(
                    R.string.screen_property_details_description_last_time_update,
                    args.property.lastUpdateDate.toUiTime(
                        SERVER_YEAR_MONTH_DAY_TIME_PATTERN,
                        UI_DATE_PATTERN_WITH_TIME_AND_SPACE
                    )
                )
                tvSalePropertyTime.show()
            }

            rvPropertyDetails.adapter = propertyDetailsAdapter
            rvNearbyPlaces.adapter = schoolInfoAdapter
            rvPropertyImage.adapter = imageAdapter
            rvPropertyImage.attachPagerSnap()
        }
        viewModel.getPropertyDetails(args.property.propertyId)
        viewModel.updateMapStatus(false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this@PropertyDetailsFragment)
        onClick()
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setFavorite() {
        with(binding) {
            with(viewModel) {
                when (localProperty.value?.favorite == true) {
                    true -> {
                        cvFavorite.setCardBackgroundColor(requireContext().getColor(R.color.peach))
                        ivFavorite.setImageDrawable(requireContext().getDrawable(R.drawable.ic_favorite_active))
                    }

                    false -> {
                        cvFavorite.setCardBackgroundColor(requireContext().getColor(R.color.primary_white))
                        ivFavorite.setImageDrawable(requireContext().getDrawable(R.drawable.ic_favorite))
                    }
                }
            }
        }
    }

    private fun onClick() {
        with(binding) {
            cvFavorite.onClick {
                viewModel.onFavoriteClick()
                setFavorite()
            }
        }
    }


    private fun setPropertyDescriptionInfo(data: Description?) {
        with(binding) {
            tvFilterBaths.showText(data?.baths)
            tvGarage.showText(data?.garage)
            tvFilterSqft.showText(data?.sqft)
            tvBeds.showText(data?.beds)
            tvRooms.showText(data?.rooms)
            tvPropertyDescription.text = data?.text
            tvPropertyName.showText(data?.name)
            tvPropertyType.showText(
                data?.type?.type,
                R.string.screen_property_details_description_type
            )
        }
    }

    private fun setBrandingInfo(data: Branding?) {
        with(binding) {
            if (data?.photo?.isNotEmpty() == true) glideImage(data.photo, ivBrandingLogo)
        }
    }

    override fun subscribe() {
        with(viewModel) {
            property.observe(viewLifecycleOwner, propertyObserver)
            isMapReady.observe(viewLifecycleOwner, isMapReadyObserver)
            coordinates.observe(viewLifecycleOwner, coordinatesObserver)
            updateStatus.observe(viewLifecycleOwner, updateStatusObserver)
        }
    }

    private val propertyObserver: Observer<WorkResult<PropertyDetails>> = Observer {
        when (it.status) {
            Status.SUCCESS -> setPropertyDetailsData(it.data)
            Status.LOADING -> Timber.i("loading property details")
            Status.ERROR -> handleException(it.exception) { openError() }
        }
    }

    private val isMapReadyObserver: Observer<Boolean> = Observer {
        if (it) {
            viewModel.coordinates.value?.let { moveToMapPosition(it) }
        }
    }

    private val coordinatesObserver: Observer<Coordinate> = Observer {
        if (viewModel.isMapReady.value == true) {
            moveToMapPosition(it)
        }
    }

    private val updateStatusObserver: Observer<WorkResult<Unit>> = Observer {
        when (it.status) {
            Status.SUCCESS -> {}
            Status.LOADING -> Timber.i("loading property details")
            Status.ERROR -> handleException(it.exception) { openError() }
        }

    }

    private fun setPropertyDetailsData(property: PropertyDetails?) {
        with(binding) {
            setViewModelData(property)
            setPropertyDescriptionInfo(property?.description)
            setAdaptersData(property)
            setAgentData(property)
            tvPropertyStatus.text = property?.status
        }
    }

    private fun setAdaptersData(data: PropertyDetails?) {
        with(binding) {
            imageAdapter.submitList(data?.photos)
            schoolInfoAdapter.submitList(data?.schools?.schools)
            propertyDetailsAdapter.submitList(data?.details)
            Handler(Looper.getMainLooper()).postDelayed(
                { ivProperty.hide(); rvPropertyImage.show() },
                ANIMATION_TIME
            )
            rvPropertyDetails.notifyDataSetChanged()
        }
    }


    private fun setViewModelData(data: PropertyDetails?) {
        with(viewModel) {
            propertyAddressDetails.postValue(data?.location?.address?.line)
            photos.postValue(data?.photos)
            data?.location?.address?.coordinate?.let { coordinates.postValue(it) }
        }
    }

    private fun moveToMapPosition(data: Coordinate) {
        val latLng = LatLng(data.latitude, data.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 17f)
        googleMap.animateCamera(cameraUpdate)
        googleMap.addMarker(MarkerOptions().position(latLng))
    }

    private fun setAgentData(data: PropertyDetails?) {
        with(binding) {
            data?.let {
                cvPhone.onClick { callIntent(it.branding?.firstOrNull()?.phone) }
                cvEmail.onClick { emailIntent(EMAIL) }
                tvAgentName.text = it.branding?.firstOrNull()?.name
                tvAgentType.text = it.branding?.firstOrNull()?.type
                glideImage(
                    it.branding?.firstOrNull()?.photo,
                    ivAgentImage,
                    R.drawable.ic_menu_agent_not_active
                )
                tvAgencyName.showText(it.branding?.getOrNull(1)?.name)
            }
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMapClickListener { openGoogleMaps() }
        viewModel.updateMapStatus(true)
        settingsCamera()
    }

    private fun settingsCamera() {
        this.googleMap.uiSettings.apply {
            isMyLocationButtonEnabled = false
            isMapToolbarEnabled = false
        }
    }

    private fun openGoogleMaps() {
        val coordinate = viewModel.coordinates.value
        val address = viewModel.propertyAddressDetails.value
        val intentUri = Uri.parse("geo:${coordinate?.latitude},${coordinate?.longitude}?q=$address")
        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        intent.setPackage(MAP_PACKAGE)
        startActivity(intent)
    }

    private fun openPropertyPhotosScreen(id: Int, view: View) {
        val extras = FragmentNavigatorExtras(
            view to view.transitionName
        )
        val data = viewModel.buildPhotoContainer(id)
        PropertyDetailsFragmentDirections.openPropertyPhotosScreen(data).navigate(extras)
    }

    companion object {
        private const val MAP_PACKAGE = "com.google.android.apps.maps"
        private const val EMAIL = "randomEmail@Gmail.com"
        private const val ANIMATION_TIME = 500L
    }
}