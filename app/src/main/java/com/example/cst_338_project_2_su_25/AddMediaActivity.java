package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.databinding.ActivityAddMediaBinding;
import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;
import com.example.cst_338_project_2_su_25.entities.Review;

/**
 * AddMediaActivity
 *
 * <p> THis class is responsible for allowing a user to add a new TV show or movie,
 * optionally mark it as a favorite, and leave a review for it.
 *
 * <p> The activity retrieves input from the UI, stores it in the database using {@link RevuRepository},
 * and navigates back to the display screen for media items.
 *
 * <p> User input includes title, genre, rating, and whether it is marked as a favorite.
 *
 * @author Faith, Luis, Sami
 * @version 1.0
 */
public class AddMediaActivity extends AppCompatActivity {
    /** Log tag for debugging. */
    public static final String TAG = "REVU_MEDIA";
    /** Constant representing TV Show media type. */
    private final String MEDIA_TYPE_TV_SHOW = "TV Shows";
    /** Constant representing Movie media type. */
    private final String MEDIA_TYPE_MOVIE = "Movies";
    /** Repository for database operations. */
    private RevuRepository repository;
    /** Title of the media item being added. */
    String title = "";
    /** Type of media ("tvShow" or "movie"). */
    String type = "";
    /** User-provided rating for the media item. */
    float rating = 0;
    /** Selected genre of the media. */
    String genre = "";
    /** ID of the currently logged-in user. */
    int loggedInUserId = -1;
    /** ID of the media item after it is inserted into the database. */
    int mediaId;
    /** Flag indicating whether the media is marked as a favorite. */
    boolean isFavorite = false;
    /** View binding for the add media activity. */
    ActivityAddMediaBinding binding;
    /** MediaTitle object representing the current media entry. */
    MediaTitle mediaTitle;
    /** Review object for the current media entry. */
    Review review;

    SharedPreferences prefs;

    /**
     * Called when the activity is starting. Initializes UI components,
     * sets up listeners, and prepares the repository for database access.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMediaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String addMedia = getIntent().getStringExtra("addMedia");
        binding.addMediaTextView.setText(addMedia);

        prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
        loggedInUserId = prefs.getInt("userId", -1);

        repository = RevuRepository.getRepository(getApplication());

        // Handle cancel button to return to media display screen
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

        // Listen for rating changes from the user
        binding.mediaRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float ratingUser, boolean fromUser) {
                // 'rating' is the new rating value
                rating = ratingUser;
                // 'fromUser' indicates if the change was initiated by the user
                Toast.makeText(AddMediaActivity.this, "Rating: " + ratingUser, Toast.LENGTH_SHORT).show();
            }
        });

        // Handle save button to store media, review, and favorite data
        binding.btnSaveMediaTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (binding.addMediaTextView.getText().toString().equals("Add TV Show")) {
                    type = "tvShow";

                    getInformationFromDisplay();
                    insertMediaRecord();

                    Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                    intent.putExtra("mediaTitle", MEDIA_TYPE_TV_SHOW);
                    startActivity(intent);
                } else if (binding.addMediaTextView.getText().toString().equals("Add Movie")) {
                    type = "movie";

                    getInformationFromDisplay();
                    insertMediaRecord();

                    Intent intent = new Intent(getApplicationContext(), DisplayMediaActivity.class);
                    intent.putExtra("mediaTitle", MEDIA_TYPE_MOVIE);
                    startActivity(intent);
                }
            }
        });

        // Listen for favorite checkbox
        binding.buttonAddToFavorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    isFavorite = true;
                }
            }
        });
    }

    /**
     * Creates an Intent to launch AddMediaActivity.
     *
     * @param context The context from which the intent is started.
     * @return A configured intent for launching this activity.
     */
    static Intent addMediaIntentFactory(Context context) {
        return new Intent(context, AddMediaActivity.class);
    }

    /**
     * Inserts a new media title and associated review into the database.
     * If the media is marked as a favorite, also creates a Favorites entry.
     */
    private void insertMediaRecord() {
        if (title.isEmpty()) {
            Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mediaTitle = new MediaTitle(title, type, rating, genre, loggedInUserId);
        mediaId = (int) repository.insertMediaTitle(mediaTitle);

        if (mediaId < 1) {
            Toast.makeText(this, "Error saving media — please try again", Toast.LENGTH_SHORT).show();
            return;
        }

        review = new Review();
        review.setTitle(title);
        review.setType(type);
        review.setReviewText(binding.mediaReviewEditText.getText().toString());
        review.setRating(rating);
        review.setUserId(loggedInUserId);
        review.setFavorite(isFavorite);
        review.setMediaTitleId(mediaId);
        repository.insertReview(review);

        // if the user checked “favorite,” use our repository API to add it
        if (isFavorite) {
            Favorites fav = new Favorites();
            fav.setUserId(loggedInUserId);
            fav.setTitle(title);
            fav.setMediaTitleId(mediaId);
            fav.setUserName(prefs.getString("loggedInUser", String.valueOf(loggedInUserId)));
            repository.addFavorite(fav);
        }

    }

    /**
     * Retrieves user input from UI components, including title, genre,
     * and logged-in user ID from SharedPreferences.
     */
    private void getInformationFromDisplay() {
        title = binding.mediaTitleEditText.getText().toString();

        try {
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