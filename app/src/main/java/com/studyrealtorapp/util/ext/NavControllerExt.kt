package com.studyrealtorapp.util.ext

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavHostController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.R

fun NavController.safeNavigation(navDirections: NavDirections) {
    runCatching {
        val action = currentDestination?.getAction(navDirections.actionId)
        val options by lazy {
            navOptions {
                anim {
                    enter = R.anim.nav_default_enter_anim
                    exit = R.anim.nav_default_exit_anim
                    popEnter = R.anim.nav_default_pop_enter_anim
                    popExit = R.anim.nav_default_pop_exit_anim
                }
                launchSingleTop = action?.navOptions?.shouldLaunchSingleTop() ?: false
                popUpTo(action?.navOptions?.popUpTo ?: -1) {
                    inclusive = action?.navOptions?.isPopUpToInclusive ?: false
                }
            }
        }

        if (hasAction(navDirections)) navigate(navDirections, options)
    }
}

fun NavController.safeNavigation(actionId: Int) {
    if (hasAction(actionId)) navigate(actionId)
}

fun NavController.hasAction(navDirections: NavDirections) =
    currentDestination?.getAction(navDirections.actionId) != null

fun NavController.hasAction(actionId: Int) = currentDestination?.getAction(actionId) != null

fun AppCompatActivity.findParentNavController(@IdRes navHostFragmentResId: Int): NavHostController {
    val navHostFragment = supportFragmentManager.findFragmentById(navHostFragmentResId)
    return (NavHostFragment.findNavController(navHostFragment!!) as NavHostController)
}

fun Fragment.findChildNavController(@IdRes navHostFragmentResId: Int): NavHostController {
    val navHostFragment = childFragmentManager.findFragmentById(navHostFragmentResId)
    return (NavHostFragment.findNavController(navHostFragment!!) as NavHostController)
}