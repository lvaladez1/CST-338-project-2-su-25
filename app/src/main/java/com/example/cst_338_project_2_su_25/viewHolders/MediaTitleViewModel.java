package com.example.cst_338_project_2_su_25.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.cst_338_project_2_su_25.database.RevuRepository;
import com.example.cst_338_project_2_su_25.entities.MediaTitle;

import java.util.List;

public class MediaTitleViewModel extends AndroidViewModel {
    private final RevuRepository repository;

    public MediaTitleViewModel(Application application) {
        super(application);
        repository = RevuRepository.getRepository(application);
    }

    public LiveData<List<MediaTitle>> getAllLiveDataTvShowsByUserId(int userId) {
        return repository.getAllTvShowsByUserId(userId);
    }

    public void insert(MediaTitle title) {
        repository.insertMediaTitle(title);
    }
}
