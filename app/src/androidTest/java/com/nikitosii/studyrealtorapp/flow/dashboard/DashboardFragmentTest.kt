package com.nikitosii.studyrealtorapp.flow.dashboard

import android.os.Bundle
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import androidx.test.platform.app.InstrumentationRegistry
import com.nikitosii.studyrealtorapp.R
import com.nikitosii.studyrealtorapp.flow.dashboard.filter.FilterViewHolder
import com.nikitosii.studyrealtorapp.flow.main.MainActivity
import com.nikitosii.studyrealtorapp.util.view.AnimatedFilterImageView
import com.nikitosii.studyrealtorapp.util.view.SearchView
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
@ExperimentalCoroutinesApi
class DashboardFragmentTest {

    @get:Rule
    val activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    private lateinit var navController: TestNavHostController

    @Before
    fun setup() {
        navController = TestNavHostController(
            InstrumentationRegistry.getInstrumentation().targetContext
        )

        activityRule.scenario.onActivity { activity ->
            // Set the navigation graph for the TestNavHostController
            navController.setGraph(R.navigation.main_nav_graph)

            // Link the TestNavHostController with the activity
            Navigation.setViewNavController(
                activity.findViewById(R.id.navFragment),
                navController
            )
        }
    }

    @Test
    fun checkDisplayedElements() {
        onView(withId(R.id.rvRecentSaleSearches)).check(matches(isDisplayed()))
        onView(withId(R.id.rvRecentRentSearches)).check(matches(not(isDisplayed())))
        onView(withId(R.id.cvProfileImage)).check(matches(isDisplayed()))
    }

    @Test
    fun testSearchButtonFunctionality() {
        activityRule.scenario.onActivity { activity ->
            val view = activity
                .findViewById<FragmentContainerView>(R.id.navFragment)
                .findViewById<SearchView>(R.id.svSearch)

            view.setText("Los Angelos")
        }
        onView(withId(R.id.btnSearch)).perform(click())
        activityRule.scenario.onActivity { activity ->
            assertEquals(R.id.searchFragment, activity.navController?.currentDestination?.id)
        }
    }

    @Test
    fun testHouseFilterClick() {
        val filtersView = MutableLiveData<AnimatedFilterImageView>()
        activityRule.scenario.onActivity { activity ->
            val view = activity
                .findViewById<FragmentContainerView>(R.id.navFragment)
                .findViewById<SearchView>(R.id.svSearch)

            val filterView = view.findViewById<AnimatedFilterImageView>(R.id.ivEnd)
            filterView.performClick()
            filtersView.postValue(view.findViewById(R.id.ivEnd))
        }
//        onView(withId(R.id.rvFilterTypes))
//            .perform(RecyclerViewActions.actionOnItemAtPosition<FilterViewHolder>(0, click()))
    }
//
//    @Test
//    fun testFavoriteClick() {
//        // Assuming there is at least one item in the RecyclerView
//        onView(withId(R.id.rvRecentSaleSearches))
//            .perform(RecyclerViewActions.actionOnItemAtPosition<RequestViewHolder>(0, click()))
//
//        onView(withId(R.id.lavFavorite))
//            .perform(click())
//
//        // Verify the expected outcome, e.g., favorite state changed
//    }
//
////    @Test
////    fun testPriceRangeChange() {
////        onView(withId(R.id.rvRangePrice))
////            .perform(setRange(100, 500)) // Assuming you have a custom action to set range
////
////        // Verify the expected outcome, e.g., price range changed
////    }
//
//    @Test
//    fun testAnimationOnLoad() {
//        onView(withId(R.id.clTopContent))
//            .check(matches(isDisplayed()))
//
//        onView(withId(R.id.clBottomContent))
//            .check(matches(isDisplayed()))
//    }

    @After
    fun tearDown() {
        // Clean up resources or reset states here
    }
}