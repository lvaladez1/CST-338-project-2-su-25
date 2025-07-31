package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.FavoritesDAO;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.databinding.ActivityDisplayFavoritesBinding;
import com.example.cst_338_project_2_su_25.viewHolders.FavoritesAdapter;

/**
 * FavoritesActivity
 *
 * <p> This is an AppCompatActivity that displays a list of media
 * titles marked as favorites by the currently logged-in user.
 *
 * <p> This activity fetches favorite entries from the Room database using FavoritesDAO,
 * displays them in a RecyclerView, and provides navigation back to the login/main screen.
 *
 * @author Faith
 * @version 1.0
 */
public class FavoritesActivity extends AppCompatActivity {
    /** Adapter that binds favorite data to the RecyclerView. */
    private FavoritesAdapter adapter;

    /**
     * Called when the activity is starting. Initializes the RecyclerView, adapter,
     * and retrieves the favorites list from the database for the logged-in user.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied.
     *                           Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // View binding for the favorites display layout.
        com.example.cst_338_project_2_su_25.databinding.ActivityDisplayFavoritesBinding binding = ActivityDisplayFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setup RecyclerView and adapter
        // RecyclerView that displays the list of favorite media titles.
        RecyclerView favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new FavoritesAdapter();
        favoritesRecyclerView.setAdapter(adapter);

        // Retrieve logged-in user ID from SharedPreferences
        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE).getInt("userId", -1);

        // Access the database DAO
        // Data access object for querying favorite entries from the database.
        FavoritesDAO favoritesDAO = RevuDatabase.getDatabase(getApplicationContext()).favoritesDAO();

        // Observe favorite data for the current user and update the RecyclerView
        favoritesDAO.getFavoriteDisplayForUser(userId)
                .observe(this, favorites -> adapter.setFavorites(favorites));

        // Handle click event for floating action button to return to main activity
        binding.backToLoginFAB.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });
    }

    /**
     * Creates an Intent to launch the FavoritesActivity.
     *
     * @param context The context from which the intent is launched.
     * @return A configured Intent that starts FavoritesActivity.
     */
    static Intent FavoritesActivityIntentFactory(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }
}
