package com.example.cst_338_project_2_su_25.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;


@Dao
public interface ReviewDao {
    // Method to insert a review
    @Query("SELECT * FROM Review WHERE userId = :userId")
    List<Review> getReviewsForUser(int userId);
}
