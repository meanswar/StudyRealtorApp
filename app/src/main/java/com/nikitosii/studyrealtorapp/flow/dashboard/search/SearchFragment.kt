package com.nikitosii.studyrealtorapp.flow.dashboard.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.navArgs
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.domain.Status
import com.nikitosii.studyrealtorapp.core.domain.WorkResult
import com.nikitosii.studyrealtorapp.core.source.local.model.Property
import com.nikitosii.studyrealtorapp.databinding.FragmentSearchBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.SalesAdapter
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import timber.log.Timber

@RequiresViewModel(SearchViewModel::class)
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>({
    FragmentSearchBinding.bind(it)
}, R.layout.fragment_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.explode)
    }

    val args: SearchFragmentArgs by navArgs()
    private val salesPropertiesAdapter by lazy {
        SalesAdapter { data, view ->
            openPropertyDetails(
                data,
                view
            )
        }
    }
    private val isPropertiesLoaded = MutableLiveData(true)

    override fun initViews() {
        with(binding) {
            rvSaleProperties.adapter = salesPropertiesAdapter
            rvSaleProperties.setItemViewCacheSize(5)
            rvSaleProperties.recycledViewPool.setMaxRecycledViews(R.layout.item_sales, 5)
            isPropertiesLoaded.observe(viewLifecycleOwner) {
                if (it) rvSaleProperties.show()
            }
        }
        getStartingData()
    }

    private fun getStartingData() {
        if (viewModel.isDataAlreadyUploaded.value == false) {
            if (args.localRequest) viewModel.getLocalSaleProperties(args.saleRequest)
            else viewModel.getSaleProperties(args.saleRequest)
            viewModel.isDataAlreadyUploaded.postValue(true)
        }
    }

    override fun subscribe() {
        with(viewModel) {
            saleProperties.observe(viewLifecycleOwner, propertiesObserver)
        }
    }

    private val propertiesObserver: Observer<WorkResult<List<Property>>> = Observer {
        showLoading(it.status == Status.LOADING)
        when (it.status) {
            Status.LOADING -> Timber.i("loading properties")
            Status.SUCCESS -> observeProperties(it.data)
            Status.ERROR -> handleException(it.exception) { openError() }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) lavLoading.showWithAnimation(R.anim.scale_in)
            btnAccept.isEnabled = !isLoading
            if (isLoading) lavLoading.playAnimation()
        }
    }

    private fun observeProperties(data: List<Property>?) {
        with(binding) {
            isPropertiesLoaded.postValue(true)
            lavLoading.hide()
            salesPropertiesAdapter.submitList(data)
            rvSaleProperties.notifyDataSetChanged()
        }
    }

    fun openPropertyDetails(property: Property, view: ImageView) {
        view.transitionName = TRANSITION_NAME + "result"
        val extras = FragmentNavigatorExtras(
            view to TRANSITION_NAME
        )
        SearchFragmentDirections.openPropertyDetails(property).navigate(extras)
    }

    companion object {
        private const val TRANSITION_NAME = "image_view"
    }
}