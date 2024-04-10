package com.nikitosii.studyrealtorapp.flow.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.nikitosii.studyrealtorapp.util.annotation.AnnotationUtil
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<B : ViewBinding, VM : ViewModel>(
    private val actionBind: (LayoutInflater) -> B
) : AbsActivity() {
    lateinit var binding: B
    private var viewModel: VM? = null

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        initContentView()
        setViewModel()
    }

    private fun inject() {
        if (AnnotationUtil.hasViewModel(this) || AnnotationUtil.hasInject(this))
            AndroidInjection.inject(this)
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