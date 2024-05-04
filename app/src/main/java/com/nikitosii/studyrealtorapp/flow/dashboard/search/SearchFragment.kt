package com.nikitosii.studyrealtorapp.flow.dashboard.search

import android.os.Bundle
import android.transition.TransitionInflater
import android.widget.ImageView
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
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import timber.log.Timber

@RequiresViewModel(SearchViewModel::class)
class SearchFragment : BaseFragment<FragmentSearchBinding, SearchViewModel>({
    FragmentSearchBinding.bind(it)
}, R.layout.fragment_search) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(activity).inflateTransition(android.R.transition.move)
    }

    val args: SearchFragmentArgs by navArgs()
    private val propertiesAdapter by lazy {
        SalesAdapter { data, view ->
            openPropertyDetails(
                data,
                view
            )
        }
    }

    override fun initViews() {
        with(binding) {
            rvProperties.adapter = propertiesAdapter
        }
        getPropertiesData()
    }

    private fun getPropertiesData() {
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
        }
    }

    private fun observeProperties(data: List<Property>?) {
        with(binding) {
            lavLoading.hide()
            propertiesAdapter.submitList(data)
            rvProperties.notifyDataSetChanged()
        }
    }

    private fun openPropertyDetails(property: Property, view: ImageView) {
        val extras = FragmentNavigatorExtras(
            view to view.transitionName
        )
        SearchFragmentDirections.openPropertyDetails(property).navigate(extras)
    }
}