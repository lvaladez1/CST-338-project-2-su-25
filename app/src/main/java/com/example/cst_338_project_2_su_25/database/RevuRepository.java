package com.example.cst_338_project_2_su_25.database;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;


import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RevuRepository {

    private final MediaTitleDAO mediaTitleDAO;

    private final FavoritesDAO favoritesDAO;

    private final ExecutorService executorService;

    private static RevuRepository repository;

    private final ReviewDao reviewDao;

    /**
     * Private constructor for RevuRepository.
     * Initializes the Room database and obtains an instance of FavoritesDAO.
     * Also sets up a single-threaded executor for background operations.
     *
     * @param application The application context used to initialize the Room database.
     */
    public RevuRepository(Application application) {
        RevuDatabase db = RevuDatabase.getDatabase(application);

        this.mediaTitleDAO = db.mediaTitleDAO();
        this.reviewDao = db.reviewDao();
        this.favoritesDAO = db.favoritesDAO();

        executorService = Executors.newSingleThreadExecutor();
    }

    public static RevuRepository getRepository(Application application) {
        if (repository != null) {
            return repository;
        }
        Future<RevuRepository> future = RevuDatabase.databaseWriteExecutor.submit(
                () -> new RevuRepository(application)
        );
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            Log.d("Help!", "Problem getting RevuRepository, thread error.");
        }
        return null;
    }

    public static void setRepository(RevuRepository repository) {
        RevuRepository.repository = repository;
    }

    /**
     +     * Inserts a MediaTitle on a background thread and returns its new row ID.
     +     * This blocks until the insert completes.
     +     */
   public long insertMediaTitle(MediaTitle mediaTitle) {
       try {
           // submit the DAO.insert() and then wait for its return value
           return RevuDatabase.databaseWriteExecutor.submit(() ->
                           mediaTitleDAO.insert(mediaTitle)).get();
       } catch (Exception e) {
           Log.e("RevuRepository", "Failed to insert MediaTitle", e);
           return -1;
       }
   }

    public void insertReview(Review review) {
        Executors.newSingleThreadExecutor().execute(() -> reviewDao.insert(review));
    }

    public void updateReview(Review review) {
        Executors.newSingleThreadExecutor().execute(() -> reviewDao.update(review));
    }

    public void deleteReview(Review review) {
        Executors.newSingleThreadExecutor().execute(() -> reviewDao.delete(review));
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


    public LiveData<List<MediaTitle>> getAllTvShowsByUserId(int userId) {
        return mediaTitleDAO.getAllTvShows(userId, "tvShow");
    }

    public LiveData<List<MediaTitle>> getAllMoviesByUserId(int userId) {
        return mediaTitleDAO.getAllMovies(userId, "movie");
    }


    public MediaTitle getMediaTitleById ( int mediaTitleId){

        return mediaTitleDAO.getMediaTitleById(mediaTitleId);

    }

    public LiveData<List<Favorites>> getFavoriteDisplayForUser(int userId) {
        return favoritesDAO.getFavoriteDisplayForUser(userId);
    }

    public LiveData<List<Review>> getAllReviews() {
        return reviewDao.getAllReviews();
    }

    public LiveData<List<Review>> getReviewsByUserId(int userId) {
        return reviewDao.getLiveReviewsForUser(userId);
    }
}

