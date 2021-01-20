package com.emon.ecommerceappjava.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.network.ApiInterface;
import com.emon.ecommerceappjava.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageRepository {
    private MutableLiveData<List<HomepageModel>> homeListMutableLiveData;

    private Application application;
    public ApiInterface apiInterface;

    public HomepageRepository(Application application) {
        this.application = application;
        if (homeListMutableLiveData == null) {

            homeListMutableLiveData = new MutableLiveData<>();

        }

        apiInterface = ApiService.getClient().create(ApiInterface.class);

    }


    public LiveData<List<HomepageModel>> getHomepageData() {

        apiInterface.getHomepageData().enqueue(new Callback<List<HomepageModel>>() {
            @Override
            public void onResponse(Call<List<HomepageModel>> call, Response<List<HomepageModel>> response) {
                homeListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<HomepageModel>> call, Throwable t) {
                homeListMutableLiveData.setValue(null);
            }
        });
        return homeListMutableLiveData;
    }
}
