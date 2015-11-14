package com.swanson.octodroid.test;

import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.runner.AndroidJUnit4;
import android.test.ActivityInstrumentationTestCase2;

import com.swanson.octodroid.MainActivity;
import com.swanson.octodroid.R;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest extends
        ActivityInstrumentationTestCase2<MainActivity>
{
    private ProgressIdlingResource progressResource;

    public MainActivityTest()
    {
        super(MainActivity.class);
    }

    @Before
    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        getActivity();
        Espresso.registerIdlingResources(new ProgressIdlingResource((MainActivity) getActivity()));
    }

    @Test
    public void testGetUserReturnsExpected()
    {
        String user = "swanson";
        onView(withId(R.id.editText)).perform(typeText(user));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("abe"))));
    }

    @Test
    public void testGetUserReturnsExpected_Bad()
    {
        String user = "hhhh";
        onView(withId(R.id.editText)).perform(typeText(user));
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.textView)).check(matches(withText(containsString("No repos :("))));
    }

    @Ignore
    @Test
    public void testGetUserReturnsExpected_Wiremock()
    {

    }

    @Ignore
    @Test
    public void testGetUserReturnsExpected_Retromock()
    {

    }
}
