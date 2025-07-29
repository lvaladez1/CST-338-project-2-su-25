package com.example.cst_338_project_2_su_25;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
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

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule = new ActivityScenarioRule<>(LoginActivity.class);
    @Before
    public void setUp() {
        Intents.init();
        Context context = ApplicationProvider.getApplicationContext();
        context.getSharedPreferences("appPrefs", Context.MODE_PRIVATE).edit().clear().commit();

        RevuDatabase db = RevuDatabase.getDatabase(context);
        UserDao userDao = db.userDao();
        User testUser = new User("testuser", "password123", false);
        userDao.insertUser(testUser);
    }
    @After
    public void tearDown() {
        Intents.release();
    }

    @Test
    public void testLoginIntent() {

        onView(withId(R.id.usernameInput)).perform(typeText("testuser"), closeSoftKeyboard());
        onView(withId(R.id.passwordInput)).perform(typeText("password123"), closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}
