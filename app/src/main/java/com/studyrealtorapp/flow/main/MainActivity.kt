package com.studyrealtorapp.flow.main

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.ui.setupWithNavController
import com.android.installreferrer.api.InstallReferrerClient
import com.android.installreferrer.api.InstallReferrerStateListener
import com.android.installreferrer.api.ReferrerDetails
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.PendingDynamicLinkData
import com.google.firebase.dynamiclinks.ktx.dynamicLinks
import com.google.firebase.ktx.Firebase
import com.nikitosii.studyrealtorapp.BuildConfig
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ActivityMainBinding
import com.studyrealtorapp.flow.base.InjectableActivity
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.hide
import com.studyrealtorapp.util.ext.show
import com.studyrealtorapp.util.ext.toast
import com.studyrealtorapp.util.link.DynamicLink
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem
import timber.log.Timber

@RequiresViewModel(MainViewModel::class)
class MainActivity : InjectableActivity<ActivityMainBinding, MainViewModel>(
    { ActivityMainBinding.inflate(it) },
    navControllerId = R.id.navFragment
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        with(binding) {
            bottomNavigation.setMenuItems(getMenuItems())

            navController?.let {
                bottomNavigation.setupWithNavController(it)
                it.addOnDestinationChangedListener { _, destination, _ ->
                    val destinationId = destination.id
                    tryHideBottomBar(destinationId)
                }
            }
        }
    }

    private fun getMenuItems(): Array<CbnMenuItem> {
        return arrayOf(
            CbnMenuItem(
                R.drawable.ic_menu_sale_black,
                R.drawable.avd_ic_menu_sales_peach,
                R.id.salesFragment
            ),
            CbnMenuItem(
                R.drawable.ic_menu_rent_black,
                R.drawable.avd_menu_rent_peach,
                R.id.rents_nav_graph
            ),
            CbnMenuItem(
                R.drawable.ic_menu_agent_black,
                R.drawable.avd_menu_agent_peach,
                R.id.agentsFragment
            )
        )
    }


    private fun tryHideBottomBar(destinationId: Int) {
        binding.run {
            when (destinationId) {
                R.id.salesFragment,
                R.id.rentsFragment,
                R.id.agentsFragment -> bottomNavigation.show(true)

                else -> bottomNavigation.hide()
            }
        }
    }
}