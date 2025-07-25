package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * FavoritesActivity is responsible for displaying the user's list of favorite titles.
 * Also includes a button that allows the user to navigate to a filter screen
 * where they can filter favorites by category (movies or TV shows).
 */
public class FavoritesActivity extends AppCompatActivity {

    /**
     * Initializes the FavoritesActivity when it is created.
     * Sets the layout to activity_favorites_list and sets up a button that opens the
     * FilterFavoritesActivity when clicked.
     *
     * @param savedInstanceState Bundle containing the activity's previously saved state, if any.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        Button openFilterFavoritesButton = findViewById(R.id.btnFilterFavorites);

        openFilterFavoritesButton.setOnClickListener(view -> {
            Intent intent = new Intent(FavoritesActivity.this, FilterFavoritesActivity.class);
            startActivity(intent);
        });
    }
}
