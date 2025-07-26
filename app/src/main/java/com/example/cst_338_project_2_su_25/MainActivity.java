package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.entities.User;
import com.example.cst_338_project_2_su_25.ReviewHistoryActivity;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private Favorites favorite;

    public MainActivity() {}

    public MainActivity(int contentLayoutId, Favorites favorite) {
        super(contentLayoutId);
        this.favorite = favorite;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button openFavoritesButton = findViewById(R.id.btnViewFavorites);

        Button viewReviewsButton = findViewById(R.id.btnViewReviews);

        openFavoritesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

        viewReviewsButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ReviewHistoryActivity.class);
            startActivity(intent);
        });

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