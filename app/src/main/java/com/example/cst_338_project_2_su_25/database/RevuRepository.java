package com.example.cst_338_project_2_su_25.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.cst_338_project_2_su_25.entities.Favorites;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RevuRepository {

    private final FavoritesDAO favoritesDAO;
    private final ExecutorService executorService;

    private static RevuRepository repository;

    /**
     * Private constructor for RevuRepository.
     * Initializes the Room database and obtains an instance of FavoritesDAO.
     * Also sets up a single-threaded executor for background operations.
     *
     * @param application The application context used to initialize the Room database.
     */
    private RevuRepository(Application application) {
        RevuDatabase db = RevuDatabase.getDatabase(application);
        this.favoritesDAO = db.favoritesDAO();
        executorService = Executors.newSingleThreadExecutor();
    }

    /**
     * Adds a favorite title to the Room database using a background thread.
     *
     * @param favorite The Favorites object to be inserted into the database.
     */
    //will switch to livedata later so UI can auto update
    public void addFavorite(Favorites favorite) {
        executorService.execute(() -> favoritesDAO.addFavorite(favorite));
    }

    /**
     * Removes a favorite title from the Room database using a background thread.
     *
     * @param favorite The Favorites object to be deleted from the database.
     */
    //will switch to livedata later so UI can auto update
    public void removeFavorite(Favorites favorite) {
        executorService.execute(() -> favoritesDAO.removeFavorite(favorite));
    }

    /**
     * Retrieves a LiveData list of Favorites for the specified user ID.
     * This allows the UI to observe changes to the user's favorites in real time.
     *
     * @param userId The ID of the user whose favorites are being requested.
     * @return A LiveData list of the user's favorite titles from the Room database.
     */
    public LiveData<List<Favorites>> getFavoritesForUser(int userId) {
        return favoritesDAO.getFavoritesForUser(userId);
    }
}
