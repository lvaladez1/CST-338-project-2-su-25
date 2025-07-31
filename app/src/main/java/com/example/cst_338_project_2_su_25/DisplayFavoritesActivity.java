package com.example.cst_338_project_2_su_25;

import android.content.Context;
import android.content.Intent;

public class DisplayFavoritesActivity {

    static Intent FavoriteDisplayIntentFactory(Context context) {
        return new Intent(context, DisplayFavoritesActivity.class);
    }

}
