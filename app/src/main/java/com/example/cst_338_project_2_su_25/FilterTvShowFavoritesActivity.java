package com.example.cst_338_project_2_su_25;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.cst_338_project_2_su_25.database.FavoritesDAO;
import com.example.cst_338_project_2_su_25.database.RevuDatabase;
import com.example.cst_338_project_2_su_25.viewHolders.FavoritesAdapter;

public class FilterTvShowFavoritesActivity extends AppCompatActivity {
    private FavoritesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_tvshows);

        //set up the RecyclerView and the adapter
        RecyclerView rv = findViewById(R.id.favoritesRecyclerView);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FavoritesAdapter();
        rv.setAdapter(adapter);

        //grab the current userId
        int userId = getSharedPreferences("appPrefs", MODE_PRIVATE)
                .getInt("userId", -1);

        //call DAO method passing in tvShow
        FavoritesDAO dao = RevuDatabase.getDatabase(this).favoritesDAO();
        dao.getFavoriteDisplayForUserByType(userId, "tvShow")
                .observe(this, list -> {
                    // this will clear and repopulate the adapter with only the TV show favorites
                    adapter.setFavorites(list);
                });
    }
}
