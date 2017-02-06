package com.jordic.marvelapp;

/**
 * Created by Jordi on 06/02/2017.
 */

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.jordic.marvelapp.classes.Comic;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ComicDetailsInstrumentedTest
{

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class);

    @Before
    public void setUp() throws Exception
    {
        Comic comic = new Comic("Test comic","http://i.annihil.us/u/prod/marvel/i/mg/6/20/584b0c3ae3537",
                "Test description","http://i.annihil.us/u/prod/marvel/i/mg/6/20/584b0c3ae3537");
        mActivityRule.getActivity().loadComicDetailsFragment(comic);

    }


    @Test
    public void testClickShowFragmentButtonTestFragmentShown()
    {
        onView(withId(R.id.titleTextView)).check(matches(withText("Test comic")));
        onView(withId(R.id.descriptionTextView)).check(matches(withText("Test description")));
    }
}
