package com.example.cst_338_project_2_su_25.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.User;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM User WHERE username = :username AND password = :password LIMIT 1")
    User userLogin(String username, String password);
}
