package com.kotlinplay.app.main

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.kotlinplay.R
import kotlinx.android.synthetic.main.activity_main.view.*
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Test
    fun testActivityView() {
       val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.mainParent)).check(matches(isDisplayed()))

        onView(withId(R.id.mainCollapsingToolbar)).check(matches(isDisplayed()))

    }




}