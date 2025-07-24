package com.example.cst_338_project_2_su_25.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    public String username;
    public String password;
    public boolean isAdmin;
}
