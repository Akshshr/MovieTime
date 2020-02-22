package com.kotlinplay.app.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kotlinplay.R
import com.kotlinplay.api.model.response.Show
import com.kotlinplay.app.main.adpater.ShowsAdapter
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class AdapterTest{

    @get : Rule
    val activityRule =ActivityScenarioRule(MainActivity::class.java)

    val SHOWS_ITEM = 10
    val SHOW_NULL = Show(null, null,null,null,null,null,null,
        null,null,null,null,null,null,null,null,null,null, null,null,null)

    @Test
    fun check_adapter_is_visible() {
        onView(withId(R.id.showsRecyclerView)).check(matches(isDisplayed()))
    }


    @Test
    fun check_on_show_click() {
        onView(withId(R.id.showsRecyclerView)).perform(actionOnItemAtPosition<ShowsAdapter.ShowViewHolder>(SHOWS_ITEM, click()))
    }


}