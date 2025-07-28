package com.example.cst_338_project_2_su_25.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Review {
    /* * Represents a review for a media title.
     * Contains the review ID, user ID, title of the review, rating, review text, and a favorite flag.
     */


    @PrimaryKey (autoGenerate = true)
    public int reviewId;
    public int userId;
    public String title;
    public float rating;
    public String reviewText;
    public boolean isFavorite;


}
