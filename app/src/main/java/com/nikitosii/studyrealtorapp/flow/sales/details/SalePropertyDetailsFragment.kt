package com.nikitosii.studyrealtorapp.flow.sales.details

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.navigation.fragment.navArgs
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalePropertiesDetailsBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.sales.details.adapter.PropertyImageAdapter
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.model.getAllPhotos
import com.nikitosii.studyrealtorapp.util.ext.model.getName

@RequiresViewModel(SalePropertyDetailsViewModel::class)
class SalePropertyDetailsFragment :
    BaseFragment<FragmentSalePropertiesDetailsBinding, SalePropertyDetailsViewModel>(
        { FragmentSalePropertiesDetailsBinding.bind(it) },
        R.layout.fragment_sale_properties_details
    ) {

    private val args: SalePropertyDetailsFragmentArgs by navArgs()
    private val adapter = PropertyImageAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
    }

    override fun initViews() {
        with(binding) {
            rvPropertyPhotos.adapter = adapter
            val property = args.property
            val photos = args.property.getAllPhotos()
            adapter.submitList(photos)
            tvPropertyAddress.text = args.property.getName()
            tvPropertyPrice.text =
                getString(R.string.view_sale_property_description_price, args.property.listPrice)
            tvPropertyBeds.text = getString(
                if (args.property.description?.beds == 1) R.string.view_sale_property_description_bed
                else R.string.view_sale_property_description_beds,
                args.property.description?.beds
            )
            tvPropertyBaths.text =
                getString(
                    if (args.property.description?.baths == 1) R.string.view_sale_property_description_bath
                    else R.string.view_sale_property_description_baths,
                    args.property.description?.baths
                )
        }
    }

    override fun subscribe() {
    }
}