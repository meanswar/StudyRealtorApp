package com.studyrealtorapp.flow.rents

import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.EditorInfo.IME_ACTION_UNSPECIFIED
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.FragmentSalesBinding
import com.studyrealtorapp.flow.base.BaseFragment
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.dividerHorizontal
import com.studyrealtorapp.util.ext.dividerVertical
import com.studyrealtorapp.util.ext.onFocus
import com.studyrealtorapp.util.ext.onTouchEvent
import com.studyrealtorapp.util.ext.toast
import timber.log.Timber

@RequiresViewModel(RentsViewModel::class)
class RentsFragment : BaseFragment<FragmentSalesBinding, RentsViewModel>(
    { FragmentSalesBinding.bind(it) },
    R.layout.fragment_sales
) {

    override fun initViews() {
        with(binding) {
            rvRecent.dividerHorizontal(R.drawable.divider_horizontal)
            rvFavorites.dividerHorizontal(R.drawable.divider_horizontal)
            (elSearch.secondLayout.findViewById(R.id.rvRecentSearches) as RecyclerView).apply {
                dividerVertical(R.drawable.divider_horizontal)
            }
            (elSearch.secondLayout.findViewById(R.id.rvFavorites) as RecyclerView).apply {
                dividerVertical(R.drawable.divider_horizontal)
            }


            elSearch.showSpinner = false
            val etSearch = (elSearch.parentLayout.findViewById(R.id.etSearch) as EditText)
            etSearch.onFocus { elSearch.toggleLayout() }
            etSearch.onTouchEvent { view, event ->
                when (event.action) {
                    EditorInfo.IME_ACTION_DONE -> findFlats(view as EditText)
                    IME_ACTION_UNSPECIFIED -> requestFocus(etSearch)
                    else -> toast("motion event: ${event.action}")
                }
                true
            }
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