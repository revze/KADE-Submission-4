package io.revze.footballmatchschedule

import android.os.SystemClock
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeDown
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import io.revze.footballmatchschedule.R.id.*
import io.revze.footballmatchschedule.view.matchlist.MatchActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MatchActivity::class.java)

    @Test
    fun testAppBehaviour() {
        onView(withId(tab_layout)).check(matches(isDisplayed()))
        val nextMatchMatcher = Matchers.allOf(withText("Next Match"), isDescendantOfA(withId(R.id.tab_layout)))
        onView(nextMatchMatcher).perform(click())
        Thread.sleep(1000)

        onView(withId(rv_next_match)).check(matches(isDisplayed()))
        onView(withId(rv_next_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(9))
        onView(withId(rv_next_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(9, click()))

        Thread.sleep(1000)
        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText(R.string.success_added_favorite_match)).check(matches(isDisplayed()))

        onView(withContentDescription(R.string.abc_action_bar_up_description)).check(matches(isDisplayed()))
        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())

        val favoriteMatcher = Matchers.allOf(withText("Favorites"), isDescendantOfA(withId(R.id.tab_layout)))
        onView(favoriteMatcher).perform(click())

        onView(withId(rv_favorite_match)).check(matches(isDisplayed()))
        onView(withId(rv_favorite_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(withText(R.string.success_removed_favorite_match)).check(matches(isDisplayed()))

        pressBack()

        Thread.sleep(1000)
    }
}