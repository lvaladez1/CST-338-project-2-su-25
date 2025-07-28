package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.databinding.ActivityAddMediaBinding;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;


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
    }

    static Intent addMediaIntentFactory(Context context) {
        return new Intent(context, AddMediaActivity.class);
    }

    private void insertMediaRecord() {
        if (title.isEmpty()) {
            return;
        }

        MediaTitle mediaTitle = new MediaTitle(title, type, rating, genre, loggedInUserId);
        repository.insertMediaTitle(mediaTitle);
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
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("userId", loggedInUserId); // assuming getUserId() returns an int
            editor.apply();
            loggedInUserId = prefs.getInt("userId", -1);
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