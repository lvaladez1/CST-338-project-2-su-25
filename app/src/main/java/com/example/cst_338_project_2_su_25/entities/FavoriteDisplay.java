package com.example.cst_338_project_2_su_25.entities;

import android.content.Context;
import android.content.Intent;

import com.example.cst_338_project_2_su_25.FavoritesActivity;

public class FavoriteDisplay{
    public int favoritesId;
    public String username;
    public int mediaTitleId;
    public String title;

    public FavoriteDisplay(int favoritesId, String username, int mediaTitleId, String title) {
        this.favoritesId = favoritesId;
        this.username = username;
        this.mediaTitleId = mediaTitleId;
        this.title = title;
    }


    public int getFavoritesId() {
        return favoritesId;
    }

    public void setFavoritesId(int favoritesId) {
        this.favoritesId = favoritesId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getMediaTitleId() {
        return mediaTitleId;
    }

    public void setMediaTitleId(int mediaTitleId) {
        this.mediaTitleId = mediaTitleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    static Intent FavoriteDisplayIntentFactory(Context context) {
        return new Intent(context, FavoriteDisplay.class);
    }

    
}
