package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.viewHolders.ReviewAdapter;
import com.example.cst_338_project_2_su_25.viewHolders.ReviewViewModel;

import java.util.ArrayList;

public class AdminViewReviewsActivity extends AppCompatActivity {
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boolean isAdmin = getIntent().getBooleanExtra("isAdmin", false);
        if(!isAdmin) {
            Toast.makeText(this, "Access denied. Admins only.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        setContentView(R.layout.activity_admin_view_reviews);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewReviews);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button backToMenuButton = findViewById(R.id.backToMenuButton);
        backToMenuButton.setOnClickListener(v -> {
            Intent intent = new Intent(AdminViewReviewsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        reviewAdapter = new ReviewAdapter(new ArrayList<>());
        recyclerView.setAdapter(reviewAdapter);
        ReviewViewModel reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);
        reviewViewModel.getAllReviews().observe(this, reviews -> reviewAdapter.setReviews(reviews));

    }
    static Intent adminViewReviewsIntentFactory(Context context) {
        return new Intent(context, AdminViewReviewsActivity.class);
    }
}
