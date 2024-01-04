package com.studyrealtorapp.flow.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.studyrealtorapp.util.annotation.AnnotationUtil
import dagger.android.AndroidInjection
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<B: ViewBinding, VM : ViewModel>(private val actionBind: (View) -> B): DaggerFragment() {
    lateinit var binding: B
    private lateinit var viewModel: VM

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = actionBind(view)
        initViews()
        subscribe()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        inject()
        super.onCreate(savedInstanceState)
        setViewModel()
    }

    private fun inject() {
        if (AnnotationUtil.hasViewModel(this)) {
            AndroidInjection.inject(this)
        } else if (AnnotationUtil.hasInject(this)) {
            AndroidInjection.inject(this)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun setViewModel() {
        val viewModelClass = AnnotationUtil.findViewModelClass(this)
        viewModel = ViewModelProvider(this, factory)[viewModelClass] as VM
    }

    fun getViewModel(): VM {
        return viewModel
    }

    abstract fun initViews()

    abstract fun subscribe()
}