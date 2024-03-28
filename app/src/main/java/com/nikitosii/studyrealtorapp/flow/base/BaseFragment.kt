package com.nikitosii.studyrealtorapp.flow.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.nikitosii.studyrealtorapp.util.annotation.AnnotationUtil
import com.nikitosii.studyrealtorapp.util.ext.safeNavigation
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<B: ViewBinding, VM : ViewModel>(
    private val actionBind: (View) -> B,
    @LayoutRes layoutRes: Int
    ): DaggerFragment(layoutRes) {
    lateinit var binding: B
    private lateinit var _viewModel: VM

    protected val viewModel: VM
        get() = _viewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    protected val navController by lazy { findNavController() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = actionBind(view)
        setViewModel()
        initViews()
        subscribe()
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setViewModel() {
        val viewModelClass = AnnotationUtil.findViewModelClass(this)
        _viewModel = ViewModelProvider(this, factory)[viewModelClass] as VM
    }

    fun NavDirections.navigate() {
        navController.safeNavigation(this)
    }

    abstract fun initViews()

    abstract fun subscribe()
}