package com.studyrealtorapp.flow.main

import android.os.Bundle
import androidx.navigation.ui.setupWithNavController
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.databinding.ActivityMainBinding
import com.studyrealtorapp.flow.base.InjectableActivity
import com.studyrealtorapp.util.annotation.RequiresViewModel
import com.studyrealtorapp.util.ext.hide
import com.studyrealtorapp.util.ext.show

@RequiresViewModel(MainViewModel::class)
class MainActivity : InjectableActivity<ActivityMainBinding, MainViewModel>(
    { ActivityMainBinding.inflate(it) },
    navControllerId = R.id.navFragment
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController?.let {
            binding.bottomNavigation.setupWithNavController(it)
            it.addOnDestinationChangedListener { _, destination, _ ->
                val destinationId = destination.id
                tryHideBottomBar(destinationId)
            }
        }
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