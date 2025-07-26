package com.example.cst_338_project_2_su_25;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReviewDao {
    @Query("SELECT * FROM Review WHERE userId = :userId")
    List<Review> getReviewsByUserId(int userId);
}
