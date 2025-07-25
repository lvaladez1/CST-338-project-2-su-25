package com.example.cst_338_project_2_su_25;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cst_338_project_2_su_25.entities.Favorites;


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

        openFavoritesButton.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(intent);
        });

    }
}