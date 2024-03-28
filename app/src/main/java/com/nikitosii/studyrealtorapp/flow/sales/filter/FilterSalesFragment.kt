package com.nikitosii.studyrealtorapp.flow.sales.filter

import android.transition.TransitionInflater
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentFilterBinding
import com.nikitosii.studyrealtorapp.flow.base.BaseFragment
import com.nikitosii.studyrealtorapp.util.Constants
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.hideWithAnim
import com.nikitosii.studyrealtorapp.util.ext.isHidden
import com.nikitosii.studyrealtorapp.util.ext.onAnimCompleted
import com.nikitosii.studyrealtorapp.util.ext.onClick
import com.nikitosii.studyrealtorapp.util.ext.openKeyboard
import com.nikitosii.studyrealtorapp.util.ext.showWithAnimation
import java.lang.ref.WeakReference

@RequiresViewModel(FilterSalesViewModel::class)
class FilterSalesFragment : BaseFragment<FragmentFilterBinding, FilterSalesViewModel>(
    { FragmentFilterBinding.bind(it) }, R.layout.fragment_filter
) {

    private val adapter = WeakReference(FilterAdapter { onHouseFilterClick(it) })

    override fun initViews() {
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        with(binding) {
            adapter.get()?.submitList(Constants.housesList.map { it.type })

            vFilter.rvContent.adapter = adapter.get()
            lFilter.etSearch.openKeyboard()
        }
        onClick()
    }

    private fun onClick() {
        with(binding) {
            btnBack.onClick { btnBack.playAnimation() }
            tvRecentTitle.onClick { }
            btnBack.onAnimCompleted { navController.navigateUp() }
            tvHouses.onClick { toggleHouseFilters() }
        }
    }

    override fun subscribe() {
        with(viewModel) {

        }
    }

    private fun onHouseFilterClick(house: String): Boolean = viewModel.setFilterHouse(house)

    private fun toggleHouseFilters() {
        with(binding.vFilter.clContent) {
            if (isHidden()) showWithAnimation(R.anim.item_fall_down_animation) else hideWithAnim(R.anim.item_dissappear_up_anim)
        }
    }
}