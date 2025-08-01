package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * FilterFavoritesActivity allows the user to filter their list of favorite titles.
 * This screen is intended to display options for narrowing favorites
 * by type (movies or TV shows). The filtering logic can be added later.
 */
public class FilterFavoritesActivity extends AppCompatActivity {

    /**
     * Called when the FilterFavoritesActivity is created.
     * Sets the layout to activity_filter_favorites.
     *
     * @param savedInstanceState Bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_favorites);

        Button tvButton    = findViewById(R.id.btnFilterTvFavorites);
        Button movieButton = findViewById(R.id.btnFilterMovieFavorites);

        // when TV Shows button is tapped, go to the tv show filter screen
        tvButton.setOnClickListener(v -> {
            Intent i = new Intent(this, FilterTvShowFavoritesActivity.class);
            startActivity(i);
        });

        // when Movies button is tapped, go to the movie filter screen
        movieButton.setOnClickListener(v -> {
            Intent i = new Intent(this, FilterMovieFavoritesActivity.class);
            startActivity(i);
        });
    }

    static Intent FilterFavoritesActivityIntentFactory(Context context) {
        return new Intent(context, FilterFavoritesActivity.class);
    }
}
