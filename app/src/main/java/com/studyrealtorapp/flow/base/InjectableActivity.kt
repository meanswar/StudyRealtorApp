package com.studyrealtorapp.flow.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.addCallback
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding
import com.studyrealtorapp.util.ext.findParentNavController
import com.studyrealtorapp.util.ext.isNotNull
import timber.log.Timber

abstract class InjectableActivity<B : ViewBinding, VM : ViewModel>(
    actionBind: (LayoutInflater) -> B,
    navControllerId: Int? = null
) : BaseActivity<B, VM>(actionBind) {

    val navController by lazy {
        if (navControllerId != null) findParentNavController(navControllerId) else null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (navController.isNotNull()) {
            val callback = onBackPressedDispatcher.addCallback(this) {
                Timber.d("onBackPressedDispatcher")
                if (navController?.navigateUp() == false) {
                    isEnabled = false
                    onBackPressedDispatcher.onBackPressed()
                }
            }
            navController?.addOnDestinationChangedListener { _, _, _ ->
                callback.isEnabled = true
            }
        }
    }
}