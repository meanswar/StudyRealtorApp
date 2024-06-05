package com.nikitosii.studyrealtorapp.flow.agent.details

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialContainerTransform
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Coordinate
import com.nikitosii.studyrealtorapp.core.source.local.model.agent.AgentDetails
import com.nikitosii.studyrealtorapp.databinding.FragmentAgentDetailsBinding
import com.nikitosii.studyrealtorapp.flow.agent.details.adapter.language.LanguageAdapter
import com.nikitosii.studyrealtorapp.flow.agent.details.adapter.marketing.MarketingAreaAdapter
import com.nikitosii.studyrealtorapp.flow.agent.details.adapter.serving_area.ServedAreaAdapter
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.callIntent
import com.nikitosii.studyrealtorapp.util.ext.formatPrice
import com.nikitosii.studyrealtorapp.util.ext.glideImage
import com.nikitosii.studyrealtorapp.util.ext.model.getAddress
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.openWebsite
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showText
import com.nikitosii.studyrealtorapp.util.ext.toUiTime
import timber.log.Timber

@RequiresViewModel(AgentDetailsViewModel::class)
class AgentDetailsFragment : BaseFragment<FragmentAgentDetailsBinding, AgentDetailsViewModel>(
    { FragmentAgentDetailsBinding.bind(it) }, R.layout.fragment_agent_details
) {

    private val args: AgentDetailsFragmentArgs by navArgs()
    private val marketingAreasAdapter by lazy { MarketingAreaAdapter() }
    private val languagesAdapter by lazy { LanguageAdapter() }
    private val servedAreaAdapter by lazy { ServedAreaAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            // Scope the transition to a view in the hierarchy so we know it will be added under
            // the bottom app bar but over the elevation scale of the exiting HomeFragment.
            drawingViewId = R.id.navFragment
            duration = 400L
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(Color.TRANSPARENT)
        }
    }

    override fun initViews() {
        setLocalAgentData()
        onClick()

        viewModel.agent.postValue(args.agent)
        viewModel.getAgentDetails(args.agent.id)

        with(binding) {
            rvMarketingAreas.adapter = marketingAreasAdapter
            rvLanguages.adapter = languagesAdapter
            rvServedAreas.adapter = servedAreaAdapter
        }
        initFavoriteView(args.agent.favorite)
    }

    private fun initFavoriteView(isFavorite: Boolean) {
        with(binding) {
            ivFavorite.setImageResource(
                if (isFavorite) R.drawable.ic_favorite_active
                else R.drawable.ic_favorite
            )
        }
    }

    private fun setLocalAgentData() {
        val agent = args.agent
        with(binding) {
            tvProfileName.text = agent.fullName
            tvProfileRole.text = agent.title
            tvProfileAddress.text = agent.address?.getAddress()
            glideImage(agent.photoUrl, ivProfile, R.drawable.agent)
            agent.salePrice?.lastListingDate?.let {
                tvPriceTitle.text = getString(
                    R.string.screen_agent_details_about_price_last_time_update,
                    it.toUiTime()
                )
            }
            tvAgentPrice.text = getString(
                R.string.screen_agent_details_about_price_range,
                agent.salePrice?.min?.toString()?.formatPrice(),
                agent.salePrice?.max?.toString()?.formatPrice()
            )
        }
    }

    override fun subscribe() {
        with(viewModel) {
            agentDetails.observe(viewLifecycleOwner, agentDetailsObserver)
        }
    }

    private val agentDetailsObserver: Observer<WorkResult<AgentDetails>> = Observer {
        onLoading(it.status == Status.LOADING)
        when (it.status) {
            Status.SUCCESS -> setAgentDetails(it.data)
            Status.ERROR -> handleException(it.exception) { openError() }
            Status.LOADING -> Timber.i("loading data")
        }
    }

    private fun onLoading(isLoading: Boolean) {
        with(binding) {
            grContent.show(!isLoading)
            lavLoading.show(isLoading)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAgentDetails(data: AgentDetails?) {
        with(binding) {
            tvAboutTitle.show(!data?.description.isNullOrEmpty())
            tvAbout.showText(data?.description)
            glideImage(data?.photo?.url, ivProfile, R.drawable.ic_user_profile)

            servedAreaAdapter.submitList(data?.servedAreas)
            marketingAreasAdapter.submitList(data?.marketingArea)
            rvMarketingAreas.notifyDataSetChanged()

            languagesAdapter.submitList(data?.languages)
            rvLanguages.notifyDataSetChanged()

            tvProfileRole.showText(data?.broker?.name)
            tvAgentEmail.showText(data?.email)
            tvAgentPhone.showText(data?.phones?.firstOrNull()?.number)
            tvAgentWebsite.showText(data?.website)
            tvAddress.showText(data?.address?.line)
            tvAddressCity.text = "${data?.address?.city}, ${data?.address?.state}"
            tvAddressPostalCode.text = data?.address?.postalCode

            tvReview.text = data?.reviewCount.toString()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun onClick() {
        with(binding) {
            tvProfileAddress.onClick {
                openMap(tvProfileAddress.text.toString(), null)
            }
            tvAgentWebsite.onClick { openWebsite(tvAgentWebsite.text.toString()) }
            tvAgentPhone.onClick { callIntent(tvAgentPhone.text.toString()) }
            clMapContent.onClick { openMap(tvProfileAddress.text.toString(), null) }
            cvFavorite.onClick { onFavoriteClick() }
        }
    }

    private fun onFavoriteClick() {
        val agent = viewModel.agent.value?.let { it.copy(favorite = !it.favorite) } ?: return
        viewModel.agent.postValue(agent)
        initFavoriteView(agent.favorite)
    }

    private fun openMap(address: String, coord: Coordinate?) {
        val intentUri =
            Uri.parse("geo:${coord?.latitude},${coord?.longitude}?q=$address")
        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        intent.setPackage(MAP_PACKAGE)
        startActivity(intent)
    }

    companion object {
        private const val MAP_PACKAGE = "com.google.android.apps.maps"
    }
}