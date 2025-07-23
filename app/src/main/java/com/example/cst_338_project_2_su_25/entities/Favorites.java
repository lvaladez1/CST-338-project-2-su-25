package com.example.cst_338_project_2_su_25.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "FAVORITES_TABLE")
public class Favorites {

    @PrimaryKey(autoGenerate = true)
    private int id;
}
