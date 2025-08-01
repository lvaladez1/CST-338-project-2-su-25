package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cst_338_project_2_su_25.database.FavoritesDAO;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.databinding.ActivityDisplayFavoritesBinding;
import com.example.cst_338_project_2_su_25.viewHolders.FavoritesAdapter;

/**
 * FavoritesActivity
 *
 * <p> This is an AppCompatActivity that displays the media
 * titles that are made favorites by the currently logged-in user.
 *
 * <p> This activity grabs favorite entries from the Room database using the FavoritesDAO,
 * displays them in a RecyclerView, and also has a back button that goes back to the login/main screen.
 *
 * @author Faith
 * @version 1.0
 */
public class FavoritesActivity extends AppCompatActivity {
    /** Adapter that binds favorite data to the RecyclerView. */
    private FavoritesAdapter adapter;

    /**
     * Called when the activity is starting. Initializes the RecyclerView, adapter,
     * and retrieves the favorites list from the database for the currently logged-in user.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     * this Bundle contains the data it most recently had. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDisplayFavoritesBinding binding = ActivityDisplayFavoritesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Use the binding’s RecyclerView
        // Setup adapter
        binding.favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter();
        binding.favoritesRecyclerView.setAdapter(adapter);

        // Retrieve logged-in user ID from SharedPreferences
        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE).getInt("userId", -1);

        // Access the database DAO
        FavoritesDAO dao = RevuDatabase.getDatabase(this).favoritesDAO();

        // Observe favorite data for the current user and update the RecyclerView
        dao.getFavoriteDisplayForUser(userId)
                .observe(this, favorites -> {
                    adapter.setFavorites(favorites);
                    boolean empty = favorites == null || favorites.isEmpty();
                    binding.emptyFavoritesMessage.setVisibility(empty ? View.VISIBLE : View.GONE);
                    binding.favoritesRecyclerView.setVisibility(empty ? View.GONE : View.VISIBLE);
                });

        // Handles a click event for floating action button to return to the main activity
        binding.backToLoginFAB.setOnClickListener(v ->
                startActivity(new Intent(this, MainActivity.class)));
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
