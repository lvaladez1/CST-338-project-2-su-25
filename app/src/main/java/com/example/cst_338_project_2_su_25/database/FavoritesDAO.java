package com.example.cst_338_project_2_su_25.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.Favorites;

import java.util.List;

@Dao
public interface FavoritesDAO {
    @Insert
    void addFavorite(Favorites favorite);
    @Delete
    void removeFavorite(Favorites favorite);

    //TODO: wait for sami to create user table so I can integrate it
    //correct query below...update later to return live date
    //@Query("SELECT * FROM FAVORITES_TABLE WHERE userId = :userId")
    //List<Favorites> getFavoritesForUser(int userId);

}
