package com.example.cst_338_project_2_su_25;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cst_338_project_2_su_25.database.FavoritesDAO;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.viewHolders.FavoritesAdapter;

public class FilterMovieFavoritesActivity extends AppCompatActivity {
    private FavoritesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_movies);

        //set up the RecyclerView and the adapter
        RecyclerView rv = findViewById(R.id.favoritesRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter();
        rv.setAdapter(adapter);

        //grab the current userId
        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE)
                .getInt("userId", -1);

        //call DAO method passing in movie
        FavoritesDAO dao = RevuDatabase.getDatabase(this).favoritesDAO();
        dao.getFavoriteDisplayForUserByType(userId, "movie")
                .observe(this, list -> {
                    // repopulates adapter with  movie favorites
                    adapter.setFavorites(list);
                });
    }
}
