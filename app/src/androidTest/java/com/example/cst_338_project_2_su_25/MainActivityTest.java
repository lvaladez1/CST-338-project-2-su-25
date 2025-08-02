package com.example.cst_338_project_2_su_25;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


import android.content.Context;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private ActivityScenario<MainActivity> scenario;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        prefs.edit().putString("loggedInUser", "demo_user")
                .putInt("userId", 1)
                .putBoolean("isAdmin", false)
                .apply();
        Intents.init();
    }

    @After
    public void tearDown() {
        if(scenario != null) {
            scenario.close();
        }
        Intents.release();

    }


    @Test
    public void navigatesToReviewHistoryActivity() {
        scenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.btnViewReviews)).check(matches(isDisplayed()));
        onView(withId(R.id.btnViewReviews)).perform(click());
        intended(hasComponent(ReviewHistoryActivity.class.getName()));
    }

    @Test
    public void testNavigateToAddTvShow() {
        scenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.btnTvShows)).perform(click());
        intended(hasComponent(DisplayMediaActivity.class.getName()));
    }

    @Test
    public void testNavigateToAddMovie() {
        scenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.btnMovies)).perform(click());
        intended(hasComponent(DisplayMediaActivity.class.getName()));
    }

    @Test
    public void testNavigateToFavorites() {
        scenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.btnViewFavorites)).perform(click());
        intended(hasComponent(FavoritesActivity.class.getName()));
    }


    @Test
    public void testLogoutGoesToLoginActivity() {
        scenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.logoutButton)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

    @Test
    public void testAdminButtonsAreHidden() {
        Context context = ApplicationProvider.getApplicationContext();
        SharedPreferences prefs = context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE);
        prefs.edit().putString("loggedInUser", "demo_user")
                .putInt("userId", 1)
                .putBoolean("isAdmin", false) // Ensure isAdmin is false
                .putBoolean("isTestRun", true) // Set isTestRun to true for testing
                .apply();
        scenario = ActivityScenario.launch(MainActivity.class);

        onView(withId(R.id.createUserButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(withId(R.id.viewUsersButton)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }

}
