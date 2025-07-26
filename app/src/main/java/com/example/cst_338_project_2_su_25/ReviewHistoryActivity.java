package com.example.cst_338_project_2_su_25;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.ReviewDao;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

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
        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE)
                .getInt("userId", -1);

        List<Review> reviews = reviewDao.getReviewsForUser(userId);
        recyclerView.setAdapter(new ReviewAdapter(reviews));
    }
}
