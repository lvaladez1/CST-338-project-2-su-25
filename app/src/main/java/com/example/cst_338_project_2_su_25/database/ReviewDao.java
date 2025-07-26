package com.example.cst_338_project_2_su_25.database;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

@Dao
public interface ReviewDao {
    /**
     * Inserts a new review into the database.
     *
     * @param userId The review to be inserted.
     */
    @Query("SELECT * FROM Review WHERE userId = :userId")
    List<Review> getReviewsByUserId(int userId);
}
