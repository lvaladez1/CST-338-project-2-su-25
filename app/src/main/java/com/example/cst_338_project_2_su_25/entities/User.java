package com.example.cst_338_project_2_su_25.entities;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    /** The user's display name for login purposes. */
    public String username;
    /**The user's password for login purposes. */
    public String password;
    /** Flag for identifying admin users. */
    public boolean isAdmin;
    public int getUserId() {
        return userId;
    }

    @SuppressWarnings("unused")
    public User(){
        // Default constructor for Room
    }
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.isAdmin = false; // Default to non-admin
    }
}

