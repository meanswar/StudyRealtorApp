package com.nikitosii.studyrealtorapp.flow.dashboard.details

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.navigation.fragment.navArgs
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalePropertiesDetailsBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.details.adapter.PropertyImageAdapter
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.DateExt.SERVER_YEAR_MONTH_DAY_TIME_PATTERN
import com.nikitosii.studyrealtorapp.util.ext.DateExt.UI_DATE_PATTERN_WITH_TIME_AND_SPACE
import com.nikitosii.studyrealtorapp.util.ext.model.getAllPhotos
import com.nikitosii.studyrealtorapp.util.ext.model.getName
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithScaleIn
import com.nikitosii.studyrealtorapp.util.ext.toUiTime

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
            adapter.submitList(args.property.getAllPhotos())
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
            val descriptionText = StringBuilder().apply {
                args.property.description?.let {
                    it.apply {
                        sqft?.let {
                            append(
                                getString(
                                    R.string.screen_sale_property_details_property_description_sqft,
                                    it
                                )
                            )
                        }
                        yearBuilt?.let {
                            insert(
                                length,
                                ", " + getString(
                                    R.string.screen_sale_property_details_property_description_build_year,
                                    it
                                )
                            )
                        }
                        beds?.let {
                            insert(
                                length, " " + getString(
                                    if (it == 1) R.string.screen_sale_property_details_property_description_bed
                                    else R.string.screen_sale_property_details_property_description_beds,
                                    it
                                )
                            )
                        }

                        when {
                            bathsFull != null -> {
                                bathsFull?.let {
                                    insert(
                                        length, ", " + getString(
                                            if (it == 1) R.string.screen_sale_property_details_property_description_full_bath
                                            else R.string.screen_sale_property_details_property_description_full_baths,
                                            it
                                        )
                                    )
                                }
                                bathsHalf?.let {
                                    insert(
                                        length, ", " + getString(
                                            if (it == 1) {
                                                R.string.screen_sale_property_details_property_description_half_bath
                                            } else R.string.screen_sale_property_details_property_description_half_baths,
                                            it
                                        )
                                    )
                                }
                            }

                            bathsHalf != null && bathsFull == null -> {
                                bathsHalf.let {
                                    insert(
                                        length, ", " + getString(
                                            if (it == 1) {
                                                R.string.screen_sale_property_details_property_description_only_half_bath
                                            } else R.string.screen_sale_property_details_property_description_only_half_baths,
                                            it
                                        )
                                    )
                                }
                            }

                            bathsHalf == null && bathsFull == null -> {
                                baths?.let {
                                    insert(
                                        length, getString(
                                            if (it == 1) R.string.screen_sale_property_details_property_description_bath
                                            else R.string.screen_sale_property_details_property_description_baths,
                                            it
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }
            tvPropertyDescription.text = descriptionText

            if (args.property.branding?.isEmpty() == false) {
                tvBranding.text = args.property.branding?.first()?.name
                clBrandingContent.showWithScaleIn()
            }
        }
    }

    override fun subscribe() {
    }
}