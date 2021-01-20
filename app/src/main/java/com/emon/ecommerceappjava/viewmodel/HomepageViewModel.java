package com.emon.ecommerceappjava.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.repository.HomepageRepository;

import java.util.List;

public class HomepageViewModel extends AndroidViewModel {
    LiveData<List<HomepageModel>> dataObsarvable;
    private HomepageRepository homepageRepository;


    public HomepageViewModel(@NonNull Application application) {
        super(application);

        homepageRepository = new HomepageRepository(application);
        dataObsarvable=homepageRepository.getHomepageData();
    }

    public LiveData<List<HomepageModel>> getHomepageData(){

        return dataObsarvable;
    }


}