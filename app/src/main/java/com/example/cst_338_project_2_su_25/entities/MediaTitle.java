package com.example.cst_338_project_2_su_25.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cst_338_project_2_su_25.database.RevuDatabase;

import java.util.Objects;

@Entity(tableName = RevuDatabase.MEDIA_TABLE)
public class MediaTitle {
    @PrimaryKey(autoGenerate = true)
    private int showMovieId;
    private String title;
    private String type;
    private String genre;

    public MediaTitle(String title, String type, String genre) {
        this.title = title;
        this.type = type;
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MediaTitle that = (MediaTitle) o;
        return showMovieId == that.showMovieId && Objects.equals(title, that.title) && Objects.equals(type, that.type) && Objects.equals(genre, that.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(showMovieId, title, type, genre);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getShowMovieId() {
        return showMovieId;
    }

    public void setShowMovieId(int showMovieId) {
        this.showMovieId = showMovieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
