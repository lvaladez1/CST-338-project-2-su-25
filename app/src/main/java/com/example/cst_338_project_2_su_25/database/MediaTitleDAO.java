package com.example.cst_338_project_2_su_25.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.MediaTitle;

import java.util.List;

/**
 * MediaTitleDAO.java
 *
 * <p> Data Access Object (DAO) for performing database operations on the
 * MediaTitle entity.
 *
 * <p> This interface defines methods for inserting and retrieving media titles
 * from the Room database. Room will automatically generate the implementation
 * for this interface at compile time.
 *
 * <p> Each method is annotated with Room-specific annotations that determine how
 * SQL operations should be handled.
 */
@Dao
public interface MediaTitleDAO {

    /**
     * Inserts a MediaTitle object into the MEDIA_TABLE.
     *
     * <p> If a conflict occurs the existing entry will be replaced with the new one.
     *
     * @param mediaTitle the MediaTitle object to insert or replace.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MediaTitle mediaTitle);

    /**
     * Retrieves all media titles from the MEDIA_TABLE.
     *
     * @return a List containing all MediaTitle objects stored in the database.
     */
    @Query("SELECT * FROM " + RevuDatabase.MEDIA_TABLE)
    List<MediaTitle> getAllMediaTitles();

    @Query("SELECT * FROM " + RevuDatabase.MEDIA_TABLE + " WHERE type = :tvShow & userId = :userId ORDER BY title DESC")
    LiveData<List<MediaTitle>> getAllTvShows(int userId, String tvShow);

    @Query("SELECT * FROM " + RevuDatabase.MEDIA_TABLE + " WHERE type = :movie & userId = :userId ORDER BY title DESC")
    LiveData<List<MediaTitle>> getAllMovies(int userId, String movie);

}
