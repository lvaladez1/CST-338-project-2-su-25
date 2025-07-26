package com.example.cst_338_project_2_su_25;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Review {
    @PrimaryKey(autoGenerate = true)
    public int reviewId;
    public int userId;
    public String title;
    public float rating;
    public String reviewText;
    public boolean isFavorite;
}
