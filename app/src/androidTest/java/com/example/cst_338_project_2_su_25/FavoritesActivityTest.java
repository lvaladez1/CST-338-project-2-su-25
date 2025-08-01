package com.example.cst_338_project_2_su_25;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Tests for FavoritesActivity.
 */
@RunWith(AndroidJUnit4.class)
public class FavoritesActivityTest {

    @Rule
    public ActivityScenarioRule<FavoritesActivity> activityRule =
            new ActivityScenarioRule<>(FavoritesActivity.FavoritesActivityIntentFactory(
                    ApplicationProvider.getApplicationContext()
            ));

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        Intents.release();
    }

    /**
     * Filter button should open FilterFavoritesActivity.
     */
    @Test
    public void filterButton_opensFilterFavoritesActivity() {
        onView(withId(R.id.btnFilterFavorites)).perform(click());
        intended(hasComponent(FilterFavoritesActivity.class.getName()));
    }

    /**
     *  back-to-login FAB should open MainActivity.
     */
    @Test
    public void backFab_opensMainActivity() {
        onView(withId(R.id.backToLoginFAB)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}
