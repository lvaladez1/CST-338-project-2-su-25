package com.example.cst_338_project_2_su_25.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.Favorites;

import java.util.List;

@Dao
public interface FavoritesDAO {

    /**
     * Inserts a new favorite into the FAVORITES_TABLE.
     *
     * @param favorite The Favorites object to be added.
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addFavorite(Favorites favorite);

    /**
     * Removes a favorite entry from the FAVORITES_TABLE.
     *
     * @param favorite The Favorites object to be deleted.
     */
    @Delete
    void removeFavorite(Favorites favorite);

    /**
     * Retrieves the list of favorite titles for a specific user as LiveData.
     *
     * @param userId user whose favorites should be returned.
     * @return LiveData list of the user's favorite titles.
     */
    @Query("SELECT * FROM FAVORITES_TABLE WHERE userId = :userId")
    LiveData<List<Favorites>> getFavoritesForUser(int userId);

    /**
     * Returns a LiveData list of favorites for the given user,
     * listed with the username and the media title.
     *
     * @param userId the ID of the user whose favorites are returned
     * @return a LiveData list of Favorites objects, populated with
     * the username and media title
     */
    @Query("SELECT f.*, u.username AS username, m.title AS title " +
            "FROM FAVORITES_TABLE AS f " +
            "JOIN USER AS u ON f.userId = u.userId " +
            "JOIN MEDIA_TABLE AS m ON f.mediaTitleId = m.mediaTitleId " +
            "WHERE f.userId = :userId " +
            "ORDER BY f.favoritesId ASC")
    LiveData<List<Favorites>> getFavoriteDisplayForUser(int userId);

    /**
     * Gets only the favorites of the given mediaType...tvShow or movie
     */
    @Query("SELECT f.* " +
                    "FROM FAVORITES_TABLE AS f  " +
                    " JOIN MEDIA_TABLE AS m ON f.mediaTitleId = m.mediaTitleId " +
                    "WHERE f.userId = :userId  " +
                    "  AND m.type   = :mediaType " +
                    "ORDER BY f.favoritesId ASC")
    LiveData<List<Favorites>> getFavoriteDisplayForUserByType(int userId, String mediaType);

    /**
     * Returns a LiveData list of mediaTitleIds user has marked as favorites.
     *
     * @param userId the ID of the user whose favorite media IDs are retrieved
     * @return a LiveData list of Integer IDs of the user’s favorite media titles
     */
    @Query("SELECT mediaTitleId FROM favorites_table WHERE userId = :userId")
    LiveData<List<Integer>> getFavoriteMediaIdsForUser(int userId);

}
