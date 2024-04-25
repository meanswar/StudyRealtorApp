package com.nikitosii.studyrealtorapp.flow.main

import android.content.Context
import android.os.Build
import android.os.Bundle
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ActivityMainBinding
import com.nikitosii.studyrealtorapp.flow.base.InjectableActivity
import com.nikitosii.studyrealtorapp.util.annotation.RequiresViewModel
import com.nikitosii.studyrealtorapp.util.ext.hide
import com.nikitosii.studyrealtorapp.util.ext.show
import com.nikitosii.studyrealtorapp.flow.main.MainViewModel
import com.nikitosii.studyrealtorapp.util.ext.hasPushNotificationPermission
import com.nikitosii.studyrealtorapp.util.ext.requestNotificationPermission
import com.nikitosii.studyrealtorapp.util.ext.start
import np.com.susanthapa.curved_bottom_navigation.CbnMenuItem

@RequiresViewModel(MainViewModel::class)
class MainActivity : InjectableActivity<ActivityMainBinding, MainViewModel>(
    { ActivityMainBinding.inflate(it) },
    navControllerId = R.id.navFragment
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        checkNotificationPermission()
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

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            if (!hasPushNotificationPermission())
                requestNotificationPermission()
    }

    companion object {
        fun start(context: Context?, clearTop: Boolean = true) {
            context.start(MainActivity::class, clearTop)
        }
    }
}