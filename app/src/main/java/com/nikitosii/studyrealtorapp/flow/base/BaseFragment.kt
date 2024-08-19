package com.nikitosii.studyrealtorapp.flow.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.annotation.LayoutRes
import androidx.core.view.doOnPreDraw
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigator.Extras
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.core.source.net.exceptions.WrongTokenException
import com.nikitosii.studyrealtorapp.util.annotation.AnnotationUtil
import com.nikitosii.studyrealtorapp.util.ext.safeNavigation
import com.nikitosii.studyrealtorapp.util.ext.string
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerFragment
import timber.log.Timber
import java.io.EOFException
import java.io.InterruptedIOException
import java.net.UnknownHostException
import javax.inject.Inject

abstract class BaseFragment<B: ViewBinding, VM : BaseViewModel>(
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

    fun NavDirections.navigate(extras: Extras) {
        navController.safeNavigation(this, extras)
    }

    open fun openError(errorText: String = string(R.string.description_error), isHiding: Boolean = true) {
        (requireActivity() as? AbsActivity)?.openError(errorText, isHiding)
    }


    fun handleException(e: Throwable?, elseBlock: ((Throwable?) -> Unit)? = null) {
        when (e) {
            is UnknownHostException,
            is InterruptedIOException,
            is EOFException -> showDialogNoConnection()
            else -> elseBlock?.invoke(e)
        }
    }

    fun showDialogNoConnection() = (requireActivity() as? AbsActivity)?.openDialogNoConnection()

    fun logi(text: String) { Timber.i(text) }
    fun loge(text: String) { Timber.e(text) }

    fun postDelay(delay: Long = 500L, action: () -> Unit) {
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(action, delay)
    }

    abstract fun initViews()

    abstract fun subscribe()
}