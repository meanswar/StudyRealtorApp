package com.nikitosii.studyrealtorapp.flow.base

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigator
import androidx.navigation.Navigator.Extras
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.nikitosii.studyrealtorapp.util.ext.safeNavigation

abstract class BaseDialogFragment(
    @LayoutRes private val layoutId: Int = 0
) : DialogFragment() {

    abstract fun initBinding(view: View)

    private val root by lazy {
        LayoutInflater.from(context).inflate(layoutId, null, false)
    }
    protected val navController by lazy { findNavController() }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setView(root)
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initBinding(view)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? = root

    fun navigateUp() = navController.navigateUp()

    fun NavDirections.navigate() {
        navController.safeNavigation(this)
    }

    fun NavDirections.navigate(extras: Extras) {
        navController.safeNavigation(this, extras)
    }
}