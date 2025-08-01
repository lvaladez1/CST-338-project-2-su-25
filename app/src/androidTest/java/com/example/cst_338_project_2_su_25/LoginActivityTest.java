package com.example.cst_338_project_2_su_25;

import static org.hamcrest.Matchers.*;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.anyIntent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.intent.Intents.init;




import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.ComponentNameMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.UserDao;
import com.example.cst_338_project_2_su_25.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
/**
 * This test is placed in androidTest instead of test
 * because it relies on ApplicationProvider.getApplicationContext()
 * and Room database, which require instrumentation.
 */
@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    // Rule to launch the LoginActivity before each test

    @Before
    public void setUp() {
        Intents.init();

    }
    @After
    public void tearDown() {
        Intents.release();
    }



    @Test
    public void testLoginIntent() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        scenario.onActivity(activity -> LoginActivity.TEST_MODE = true);
        onView(withId(R.id.loginButton)).check(matches(isDisplayed()));
        onView(withId(R.id.usernameInput)).perform(typeText("testuser"), closeSoftKeyboard());
        onView(withId(R.id.passwordInput)).perform(typeText("password123"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());

        intended(hasComponent(MainActivity.class.getName()));
    }



    @Test
    public void testNavigatetoSignUp() {
        ActivityScenario<LoginActivity> scenario = ActivityScenario.launch(LoginActivity.class);
        onView(withId(R.id.signupButton)).perform(click());
        intended(hasComponent(SignUpActivity.class.getName()));
    }
}
