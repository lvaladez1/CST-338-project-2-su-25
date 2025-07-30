package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.FavoritesDAO;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.viewHolders.FavoritesAdapter;

public class FavoritesActivity extends AppCompatActivity {

    private RecyclerView favoritesRecyclerView;
    private FavoritesAdapter adapter;
    private FavoritesDAO favoritesDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_list);

        favoritesRecyclerView = findViewById(R.id.favoritesRecyclerView);
        favoritesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE).getInt("userId", -1);

        favoritesDAO = RevuDatabase.getDatabase(getApplicationContext()).favoritesDAO();

        favoritesDAO
                .getFavoriteDisplayForUser(userId)
                .observe(this, favoriteDisplays -> {
                    adapter = new FavoritesAdapter(favoriteDisplays);
                    favoritesRecyclerView.setAdapter(adapter);
                });

    }

    static Intent FavoritesActivityIntentFactory(Context context) {
        return new Intent(context, FavoritesActivity.class);
    }
}
