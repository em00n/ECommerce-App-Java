package com.emon.ecommerceappjava.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.repository.CategoriRepository;
import com.emon.ecommerceappjava.repository.HomepageRepository;

import java.util.List;

public class CategoriViewModel extends AndroidViewModel {
    LiveData<List<CategoriModel>> dataObsarvable;
    private CategoriRepository categoriRepository;


    public CategoriViewModel(@NonNull Application application) {
        super(application);

        categoriRepository = new CategoriRepository(application);
        dataObsarvable=categoriRepository.getCategoriData();
    }

    public LiveData<List<CategoriModel>> getCategoriData(){

        return dataObsarvable;
    }


}