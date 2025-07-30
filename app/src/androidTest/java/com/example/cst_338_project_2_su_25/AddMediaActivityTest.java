package com.example.cst_338_project_2_su_25;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddMediaActivityTest {
    private ActivityScenario<AddMediaActivity> scenario;

    @Before
    public void setUp() {
        Intents.init();
        scenario = ActivityScenario.launch(AddMediaActivity.class);
    }
    @After
    public void tearDown() {
        if (scenario != null) {
            scenario.close();
            Intents.release();
        }
    }

    @Test
    public void testNavigatetoAddReviewActivity() {
        ActivityScenario.launch(AddMediaActivity.class);
        onView(withId(R.id.btnSaveMediaTitle)).perform(click());
    }

    @Test
    public void testCancelNavigatesBack() {
        ActivityScenario<AddMediaActivity> scenario = ActivityScenario.launch(AddMediaActivity.class);
        onView(withId(R.id.btnCancelAddMedia)).perform(click());
        scenario.onActivity(activity -> {
            // Verify that the activity finishes and returns to the previous screen
            assertTrue(activity.isFinishing());
        });
    }
}
