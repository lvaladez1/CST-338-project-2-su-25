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
    public String type;
    public float rating;
    public String reviewText;
    public boolean isFavorite;

    public Review(){}

    public Review(int userId, String title, String type, float rating, String reviewText, boolean isFavorite) {
        this.isFavorite = isFavorite;
        this.rating = rating;
        this.reviewText = reviewText;
        this.title = title;
        this.type = type;
        this.userId = userId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
