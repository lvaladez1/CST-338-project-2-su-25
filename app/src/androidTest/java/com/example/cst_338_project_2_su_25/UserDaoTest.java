package com.example.cst_338_project_2_su_25;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.UserDao;
import com.example.cst_338_project_2_su_25.entities.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserDaoTest {
    private UserDao userDao;
    private RevuDatabase db;

    /**
     * This class tests the UserDao methods for inserting and retrieving users.
     * It checks if the user can be inserted and logged in with correct credentials,
     * and verifies that incorrect credentials return null or throw an exception.
     * It also checks that a user is not an admin by default.
     */
    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, RevuDatabase.class)
                .allowMainThreadQueries()
                .build();
        userDao = db.userDao();
    }

    /**
     * Tests the insertion of a user and verifies that the user can log in with correct credentials.
     */
    @Test
    public void insertUserAndCorrectLogin() {
        User user = new User("testuser", "password123");
        userDao.insertUser(user);
        User result = userDao.userLogin("testuser", "password123");
        assertNotNull(result);
    }

    /**
     * Tests the insertion of a user and verifies that the user cannot log in with incorrect credentials.
     */
    @Test
    public void loginWithIncorrectCredentials() {
        User user = new User("wrongpassuser", "correctpassword");
        userDao.insertUser(user);
        User result = userDao.userLogin("wrongpassuser", "incorrectpassword");
        assertNull(result); // Should return null or throw an exception, depending on implementation
    }

    @Test
    public void userIsNotAdminByDefault() {
        User user = new User("defaultUser", "randomPassword");
        userDao.insertUser(user);
        User result = userDao.userLogin("defaultUser", "randomPassword");
        assertNotNull(result);
        assertFalse(result.isAdmin);
    }

    @After
    public void closeDb() {
        db.close();
    }
}
