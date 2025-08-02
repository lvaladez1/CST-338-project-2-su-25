package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.databinding.ActivityReviewsBinding;
import com.example.cst_338_project_2_su_25.entities.Review;

public class ReviewsActivity extends AppCompatActivity {
    private RevuRepository repository;
    ActivityReviewsBinding binding;
    String title, type, reviewText;
    int reviewId, mediaTitleId, userId;
    float newRating;
    boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReviewsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = RevuRepository.getRepository(getApplication());

        String reviewText = getIntent().getStringExtra("reviewTitle");
        binding.reviewHistoryTitleTextView.setText(reviewText);
        
        String reviewEditText = getIntent().getStringExtra("reviewText");
        binding.reviewTextEditText.setText(reviewEditText);

        newRating = getIntent().getFloatExtra("rating", 0);

        binding.btnCancelAddMedia.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), ReviewHistoryActivity.class);
            startActivity(intent);
        });

        binding.mediaRatingBar.setRating(newRating);
        
        binding.mediaRatingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            // 'rating' is the new rating value
            newRating = rating;
            // 'fromUser' indicates if the change was initiated by the user
            Toast.makeText(ReviewsActivity.this, "Rating: " + rating, Toast.LENGTH_SHORT).show();
        });
        
        binding.btnSaveMediaTitle.setOnClickListener(v -> {
            getNewReviewInformation();
            updateReview();

            Intent intent = new Intent(getApplicationContext(), ReviewHistoryActivity.class);
            startActivity(intent);
        });

        binding.btnDeleteReview.setOnClickListener(v -> createDialogBox());
    }

    private void getNewReviewInformation() {
        reviewId = getIntent().getIntExtra("reviewId", -1);
        mediaTitleId = getIntent().getIntExtra("mediaTitleId", -1);
        userId = getIntent().getIntExtra("userId", -1);
        title = getIntent().getStringExtra("reviewTitle");
        type = getIntent().getStringExtra("type");
        reviewText = binding.reviewTextEditText.getText().toString();
        isFavorite = getIntent().getBooleanExtra("isFavorite", false);
    }

    private void updateReview() {
        if (reviewText.isEmpty()) {
            return;
        }

        Review review = new Review(userId, title, type, newRating, reviewText, isFavorite);
        review.setReviewId(reviewId);
        review.setMediaTitleId(mediaTitleId);

        repository.updateReview(review);
    }

    private void createDialogBox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReviewsActivity.this);
        builder.setTitle("Delete Review");
        builder.setMessage("Are you sure you want to proceed?");
        builder.setCancelable(false); // Prevents dismissal by tapping outside

        builder.setPositiveButton("Yes", (dialog, which) -> {
            Review review = new Review();
            review.setReviewId(getIntent().getIntExtra("reviewId", -1));

            repository.deleteMediaTitleById(getIntent().getIntExtra("mediaTitleId", -1));
            repository.deleteReview(review);

            dialog.dismiss();

            Intent intent = new Intent(getApplicationContext(), ReviewHistoryActivity.class);
            startActivity(intent);
        });

        builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    static Intent reviewsIntentFactory(Context context) {
        return new Intent(context, ReviewsActivity.class);
    }
}