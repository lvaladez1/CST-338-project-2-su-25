package com.example.cst_338_project_2_su_25;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.ReviewDao;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReviewHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReviewDao reviewDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_history);

        recyclerView = findViewById(R.id.reviewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewDao = RevuDatabase.getDatabase(getApplicationContext()).reviewDao();

        SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
        int userId = prefs.getInt("userId", -1);
        boolean isAdmin = prefs.getBoolean("isAdmin", false);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Review> reviews;
            if(isAdmin) {
                reviews = reviewDao.getAllReviews();
            } else {
                reviews = reviewDao.getReviewsByUserId(userId);
            }
            runOnUiThread(() -> {
                recyclerView.setAdapter(new ReviewAdapter(reviews));
            });
        });
    }
}
