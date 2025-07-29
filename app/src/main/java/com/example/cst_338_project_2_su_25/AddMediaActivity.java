package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.databinding.ActivityAddMediaBinding;
import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;
import com.example.cst_338_project_2_su_25.entities.Review;


public class AddMediaActivity extends AppCompatActivity {
    public static final String TAG = "REVU_MEDIA";
    private final String MEDIA_TYPE_TV_SHOW = "TV Shows";
    private final String MEDIA_TYPE_MOVIE = "Movies";
    private RevuRepository repository;
    String title = "";
    String type = "";
    int rating = 0;
    String genre = "";
    String review = "";
    int loggedInUserId = -1;
    boolean isFavorite = false;
    ActivityAddMediaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String addMedia = getIntent().getStringExtra("addMedia");
        binding.addMediaTextView.setText(addMedia);

        repository = RevuRepository.getRepository(getApplication());

        binding.btnCancelAddMedia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addMediaTextView.getText().toString().equals("Add TV Show")) {
                    Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                    intent.putExtra("mediaTitle", MEDIA_TYPE_TV_SHOW);
                    startActivity(intent);
                } else if (binding.addMediaTextView.getText().toString().equals("Add Movie")) {
                    Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                    intent.putExtra("mediaTitle", MEDIA_TYPE_MOVIE);
                    startActivity(intent);
                }
            }
        });

        binding.btnSaveMediaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.addMediaTextView.getText().toString().equals("Add TV Show")) {
                    type = "tvShow";
                    getInformationFromDisplay();
                    boolean wasToggled = binding.buttonAddToFavorites.isChecked();
                    insertMediaRecord(wasToggled);
                    Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                    intent.putExtra("mediaTitle", MEDIA_TYPE_TV_SHOW);
                    startActivity(intent);
                } else if (binding.addMediaTextView.getText().toString().equals("Add Movie")) {
                    type = "movie";
                    getInformationFromDisplay();
                    boolean wasToggled2 = binding.buttonAddToFavorites.isChecked();
                    insertMediaRecord(wasToggled2);
                    Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                    intent.putExtra("mediaTitle", MEDIA_TYPE_MOVIE);
                    startActivity(intent);
                }
            }
        });

        binding.buttonAddToFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isFavorite = true;
                }
            }
        });
    }

    static Intent addMediaIntentFactory(Context context) {
        return new Intent(context, AddMediaActivity.class);
    }

    private void insertMediaRecord(boolean isFavorite) {
        if (title.isEmpty()) {
            return;
        }

        MediaTitle mediaTitle = new MediaTitle(title, type, rating, genre, loggedInUserId);

        long mediaId = repository.insertMediaTitle(mediaTitle);
        if (mediaId < 1) {
            Toast.makeText(this, "Error saving media — please try again", Toast.LENGTH_SHORT).show();
            return;
        }

        String reviewComment = binding.mediaReviewEditText.getText().toString();
        Review review = new Review();
        int reviewRating = rating;
        String reviewTitle = title;
        int userId = loggedInUserId;
        //boolean reviewFavorite = isFavorite;

        review.setMediaTitleId((int) mediaId);
        review.setTitle(reviewTitle);
        review.setType(type);
        review.setReviewText(binding.mediaReviewEditText.getText().toString());
        review.setRating(reviewRating);
        review.setUserId(userId);
        review.setFavorite(isFavorite);
        repository.insertReview(review);

        /*new Thread(() -> {
            RevuDatabase db = RevuDatabase.getDatabase(getApplicationContext());
            long rid = db.reviewDao().insert(review);
            if (rid < 1) {
                Log.e("AddMedia", "Failed to insert review");
                return;
            }

            // Only if the user ticked “favorite” do we also add it to the table
            if (review.isFavorite()) {
                Favorites fav = new Favorites();
                fav.setUserId(review.getUserId());
                fav.setMediaTitleId(review.getMediaTitleId());
                db.favoritesDAO().addFavorite(fav);
            }
        }).start();*/

        // if the user checked “favorite,” use our repository API to add it
         if (isFavorite) {
             Favorites fav = new Favorites();
               fav.setUserId(review.getUserId());
               fav.setMediaTitleId(review.getMediaTitleId());
               repository.addFavorite(fav);
         }
    }

    private void getInformationFromDisplay() {
        title = binding.mediaTitleEditText.getText().toString();
        try {
            rating = Integer.parseInt(binding.mediaRatingEditText.getText().toString());
        } catch (NumberFormatException e) {
            Log.d(TAG, "Error reading value from rating edit text.");
        }
        try {
            SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
            loggedInUserId = prefs.getInt("userId", -1);
            if (loggedInUserId <= 0) {
                Toast.makeText(this, "Invalid user. Please log in again.", Toast.LENGTH_SHORT).show();
                return;
            }
        } catch (RuntimeException e) {
            Log.d(TAG, "Error reading value from rating edit text.");
        }
        try {
            genre = binding.genreSpinner.getSelectedItem().toString();
        } catch (RuntimeException e) {
            Log.d(TAG, "Error reading genre spinner");
        }
    }
}