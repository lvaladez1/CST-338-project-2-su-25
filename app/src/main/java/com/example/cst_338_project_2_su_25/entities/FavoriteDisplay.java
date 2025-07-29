package com.example.cst_338_project_2_su_25.entities;

import android.content.Context;
import android.content.Intent;

import com.example.cst_338_project_2_su_25.FavoritesActivity;

public class FavoriteDisplay{
    public int favoritesId;
    public String username;
    public String title;

    public FavoriteDisplay(int favoritesId, String username, String title) {
        this.favoritesId = favoritesId;
        this.username = username;
        this.title = title;
    }

    static Intent FavoriteDisplayIntentFactory(Context context) {
        return new Intent(context, FavoriteDisplay.class);
    }

    
}
