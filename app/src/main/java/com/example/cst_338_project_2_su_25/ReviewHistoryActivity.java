package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.ReviewDao;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.databinding.ActivityReviewHistoryBinding;
import com.example.cst_338_project_2_su_25.entities.Review;
import com.example.cst_338_project_2_su_25.viewHolders.ReviewAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReviewHistoryActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ReviewDao reviewDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.example.cst_338_project_2_su_25.databinding.ActivityReviewHistoryBinding binding = ActivityReviewHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = findViewById(R.id.reviewRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        reviewDao = RevuDatabase.getDatabase(getApplicationContext()).reviewDao();
        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE).getInt("userId", -1);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            List<Review> reviews = reviewDao.getReviewsForUser(userId);
            for (Review review : reviews) {
                Log.d("DEBUG REVIEW", "Title: " + review.getTitle() +
                        ", UserId: " + review.getUserId() +
                        ", Rating: " + review.getRating());
            }
            runOnUiThread(() -> recyclerView.setAdapter(new ReviewAdapter(reviews)));
        });

        binding.backToLoginFAB.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    static Intent reviewHistoryIntentFactory(Context context) {
        return new Intent(context, ReviewHistoryActivity.class);
    }
}