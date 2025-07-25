package com.example.cst_338_project_2_su_25.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.cst_338_project_2_su_25.entities.Favorites;
import com.example.cst_338_project_2_su_25.database.FavoritesDAO;
import com.example.cst_338_project_2_su_25.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {User.class, Favorites.class}, version = 1, exportSchema = false)
public abstract class RevuDatabase extends RoomDatabase{

    private static final String DATABASE_NAME = "Revudatabase";
    private static volatile RevuDatabase INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static RevuDatabase getDatabase(final Context context ){
        if(INSTANCE ==null){
            synchronized (RevuDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    RevuDatabase.class,
                                    DATABASE_NAME
                            )
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
    public abstract FavoritesDAO favoritesDAO();

    public abstract UserDao userDao();

}
