package com.example.cst_338_project_2_su_25.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.cst_338_project_2_su_25.entities.MediaTitle;

import java.util.List;

@Dao
public interface MediaTitleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(MediaTitle mediaTitle);

    @Query("SELECT * FROM " + RevuDatabase.MEDIA_TABLE)
    List<MediaTitle> getAllMediaTitles();

}
