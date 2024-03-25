package com.studyrealtorapp.flow.rents

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.studyrealtorapp.flow.base.BaseFragment
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.dividerHorizontal

@RequiresViewModel(RentsViewModel::class)
class RentsFragment : BaseFragment<FragmentSalesBinding, RentsViewModel>(
    { FragmentSalesBinding.bind(it) },
    R.layout.fragment_sales
) {

    override fun initViews() {
        with(binding) {
            rvRecent.dividerHorizontal(R.drawable.divider_horizontal)
            rvFavorites.dividerHorizontal(R.drawable.divider_horizontal)
        }
    }

    private fun requestFocus(view: EditText) {
        view.requestFocus()
        val manager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun findFlats(view: EditText) {
        view.clearFocus()
    }

    override fun subscribe() {

    }
}