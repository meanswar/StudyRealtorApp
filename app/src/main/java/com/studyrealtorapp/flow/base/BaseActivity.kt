package com.studyrealtorapp.flow.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.studyrealtorapp.util.annotation.AnnotationUtil
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<VM : ViewModel>(
    private val actionBind: (LayoutInflater) -> ViewBinding
): DaggerAppCompatActivity() {
    lateinit var binding: ViewBinding
    private var viewModel: VM? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        inject()
        super.onCreate(savedInstanceState, persistentState)
        initContentView()
        setViewModel()
    }

    private fun inject() {
        if (AnnotationUtil.hasViewModel(this)) {
            AndroidInjection.inject(this)
        } else if (AnnotationUtil.hasInject(this)) {
            AndroidInjection.inject(this)
        }
    }

    private fun initContentView() {
        binding = actionBind(layoutInflater)
        setContentView(binding.root)
    }

    @Suppress("UNCHECKED_CAST")
    private fun setViewModel() {
        val viewModelClass = AnnotationUtil.findViewModelClass(this)
        viewModel = ViewModelProvider(this, factory)[viewModelClass] as VM
    }

    fun getViewModel(): VM {
        if (viewModel == null)
            throw IllegalStateException("ViewModel not found")
        return viewModel as VM
    }


}