package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.entities.User;



public class MainActivity extends AppCompatActivity {

    private Favorites favorite;

    public MainActivity() {
    }

    public MainActivity(int contentLayoutId, Favorites favorite) {
        super(contentLayoutId);
        this.favorite = favorite;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
        String loggedInUser = (prefs.getString("loggedInUser", null));
        Log.d("MainActivity", "Logged in user: " + loggedInUser);
        if (prefs.getInt("userId", -1) == -1) {
            prefs.edit().putInt("userId", 1).apply();
        }
        if (loggedInUser == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }


        TextView adminLabel = findViewById(R.id.adminLabel);
        Button createUserButton = findViewById(R.id.createUserButton);
        boolean isAdmin = prefs.getBoolean("isAdmin", false);
        if (isAdmin) {
            adminLabel.setVisibility(View.VISIBLE);
            createUserButton.setVisibility(View.VISIBLE);
            createUserButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, AdminCreateUserActivity.class);
                startActivity(intent);
            });
        }
        Button viewUsersButton = findViewById(R.id.viewUsersButton);
        if (isAdmin) {
            viewUsersButton.setVisibility(View.VISIBLE);
            viewUsersButton.setOnClickListener(view -> {
                Intent intent = new Intent(MainActivity.this, AdminViewUsersActivity.class);
                startActivity(intent);
            });
        }

        Button logoutButton = findViewById(R.id.logoutButton);

        Button tvShowsButton = findViewById(R.id.btnTvShows);

        Button moviesButton = findViewById(R.id.btnMovies);

        Button openFavoritesButton = findViewById(R.id.btnViewFavorites);

        Button viewReviewsButton = findViewById(R.id.btnViewReviews);

        String username = getIntent().getStringExtra("username");

        TextView usernameText = findViewById(R.id.usernameTextView);
        if (username != null) {
            usernameText.setText(getString(R.string.welcome_user, username));
        }

        tvShowsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayMediaActivity.class);
                intent.putExtra("mediaTitle", "TV Shows");
                startActivity(intent);
            }
        });

        moviesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DisplayMediaActivity.class);
                intent.putExtra("mediaTitle", "Movies");
                startActivity(intent);
            }
        });

        openFavoritesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        viewReviewsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ReviewHistoryActivity.class);
            startActivity(intent);
        });


        /** * Logout button functionality
         * Clears shared preferences and navigates to LoginActivity
         */


        if (logoutButton != null) {
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences prefs = getSharedPreferences("appPrefs", MODE_PRIVATE);
                    prefs.edit().clear().apply();

                    Toast.makeText(MainActivity.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            Log.d("MainActivity", "Logout button is null");
        }


        //dummy login data
        RevuDatabase db = RevuDatabase.getDatabase(getApplicationContext());

        User testUser = new User();
        testUser.username = "demo_user";
        testUser.password = "password";
        testUser.isAdmin = false;

        RevuDatabase.databaseWriteExecutor.execute(() -> {
            User existingUser = db.userDao().getUserByUsername("demo_user");
            if (existingUser == null) {
                db.userDao().insertUser(testUser);
            }


        });


    }
}