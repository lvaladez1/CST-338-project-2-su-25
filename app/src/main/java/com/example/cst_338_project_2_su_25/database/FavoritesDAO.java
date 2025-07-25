package com.example.cst_338_project_2_su_25.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
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
    @Insert
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
     * @param userId The ID of the user whose favorites should be returned.
     * @return LiveData list of the user's favorite titles.
     */
    @Query("SELECT * FROM FAVORITES_TABLE WHERE userId = :userId")
    LiveData<List<Favorites>> getFavoritesForUser(int userId);

}
