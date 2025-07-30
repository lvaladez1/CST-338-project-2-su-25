package com.example.cst_338_project_2_su_25.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;


@Dao
public interface ReviewDao {
    // Method to insert a review
    @Query("SELECT * FROM Review WHERE userId = :userId")
    List<Review> getReviewsForUser(int userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(Review review);

    @Update
    void update(Review review);

    @Delete
    void delete(Review review);

    @Query("SELECT * FROM Review")
    List<Review> getAllReviews();
}
