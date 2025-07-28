package com.example.cst_338_project_2_su_25.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.entities.User;

import java.util.List;

/**
 * Data Access Object (DAO) for User entity.
 * Provides methods to interact with the User table in the database.
 */
@Dao
public interface UserDao {
    /**
     * Inserts a new user into the User table.
     *
     * @param user The User object to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    /**
     * Retrieves a user from the User table based on username and password.
     *
     * @param username The username of the user.
     * @param password The password of the user.
     * @return The User object if found, null otherwise.
     */
    @Query("SELECT * FROM user WHERE LOWER(username) = LOWER(:username) AND LOWER(password) = LOWER(:password) LIMIT 1")
    User userLogin(String username, String password);

    @Query("SELECT * FROM user WHERE username = :username LIMIT 1")
    User getUserByUsername(String username);

    @Query("SELECT * FROM user")
    List<User> getAllUsers();


}
