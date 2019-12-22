package com.example.postfetchermvvm.view

import android.os.Looper
import androidx.test.InstrumentationRegistry.getTargetContext
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.postfetchermvvm.R
import com.example.postfetchermvvm.viewmodel.CustomViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.hamcrest.CoreMatchers.anything
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith





@RunWith(AndroidJUnit4::class)
/**
 * Thread.sleep(1000) is used because my application takes extra time to do data analysis before
 * populating the data to the recyclerview (any verfication test would fail if recyclverview is empty)
 */
class MainActivityTest{

    @Test
    fun appLaunchesSuccessfully() {
        ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun onLaunchCheckMainNoticeIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.tv_main_notice))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchCheckRecyclerviewIsDisplayed(){
        ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(1000)
        onView(withId(R.id.rvContainer))
            .check(matches(isDisplayed()))
    }

    @Test
    fun onLaunchAppNameIsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)

        onView(withText(R.string.app_name))
            .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewScrollsSuccessful(){
        Looper.prepare()
        ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(1000)
        onView(withId(R.id.rvContainer))
            .perform(RecyclerViewActions.scrollToPosition<CustomViewHolder>(1))
    }

    @Test
    fun listViewInRecyclerviewClick(){
        ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(1000)
        onView(withId(R.id.rvContainer))
            .perform(RecyclerViewActions.scrollToPosition<CustomViewHolder>(1))
            .check(matches(hasDescendant(withText("Ervin Howell"))))
    }

    @Test
    fun checkPostDetailActivityIsLaunchedAfterClickOnListviewItem(){
        Intents.init()
        ActivityScenario.launch(MainActivity::class.java)
        Thread.sleep(1000)
//        onView(withId(R.id.rvContainer))
//            .perform(RecyclerViewActions.scrollToPosition<CustomViewHolder>(1))
        onView(allOf(withId(R.id.list_item),  withText("et ea vero quia laudantium autem")))
            .perform(click())
//        onData(anything()).inAdapterView(withId(R.id.list_item)).atPosition(0).perform(click())
        intended(hasComponent(PostDetailActivity::class.java!!.name))
    }
}
