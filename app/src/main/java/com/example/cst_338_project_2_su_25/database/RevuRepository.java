package com.example.cst_338_project_2_su_25.database;

import android.app.Application;

import com.example.cst_338_project_2_su_25.entities.Favorites;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RevuRepository {

    private final FavoritesDAO favoritesDAO;
    private final ExecutorService executorService;

    private static RevuRepository repository;

    private RevuRepository(Application application) {
        RevuDatabase db = RevuDatabase.getDatabase(application);
        this.favoritesDAO = db.favoritesDAO();
        executorService = Executors.newSingleThreadExecutor();
    }
    //will switch to livedata later so UI can auto update
    public void addFavorite(Favorites favorite) {
        executorService.execute(() -> favoritesDAO.addFavorite(favorite));
    }
    //will switch to livedata later so UI can auto update
    public void removeFavorite(Favorites favorite) {
        executorService.execute(() -> favoritesDAO.removeFavorite(favorite));
    }
   /* update to livedata later....integrate this when sami creates user entity
    public List<Favorites> getFavoritesForUser(int userId) {
        return favoritesDAO.getFavoritesForUser(userId);
    }*/
}
