package com.example.cst_338_project_2_su_25;
import com.example.cst_338_project_2_su_25.ReviewHistoryActivity;

import static android.view.Gravity.apply;
import static androidx.test.espresso.Espresso.onIdle;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.manipulation.Ordering;

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
    public void navigatesToReviewsActivity() {
        scenario = ActivityScenario.launch(MainActivity.class);
        onView(withId(R.id.btnViewReviews)).check(matches(isDisplayed()));
        onView(withId(R.id.btnViewReviews)).perform(click());
        intended(hasComponent(ReviewHistoryActivity.class.getName()));
    }
}
