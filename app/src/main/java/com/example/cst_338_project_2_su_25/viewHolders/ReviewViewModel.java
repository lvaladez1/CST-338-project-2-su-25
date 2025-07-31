package com.example.cst_338_project_2_su_25.viewHolders;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.entities.Review;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {
    private final RevuRepository repository;
    private final LiveData<List<Review>> allReviews;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        repository = new RevuRepository(application);
        allReviews = repository.getAllReviews();
    }
    public LiveData<List<Review>> getAllReviews() {
        return repository.getAllReviews();
    }
}
