package com.emon.ecommerceappjava.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.network.ApiInterface;
import com.emon.ecommerceappjava.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriRepository {
    private MutableLiveData<List<CategoriModel>> categoriListMutableLiveData;

    private Application application;
    public ApiInterface apiInterface;

    public CategoriRepository(Application application) {
        this.application = application;
        if (categoriListMutableLiveData == null) {

            categoriListMutableLiveData = new MutableLiveData<>();

        }

        apiInterface = ApiService.getClient().create(ApiInterface.class);

    }


    public LiveData<List<CategoriModel>> getCategoriData() {

        apiInterface.getCategoriData().enqueue(new Callback<List<CategoriModel>>() {
            @Override
            public void onResponse(Call<List<CategoriModel>> call, Response<List<CategoriModel>> response) {
                categoriListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<CategoriModel>> call, Throwable t) {
                categoriListMutableLiveData.setValue(null);
            }
        });
        return categoriListMutableLiveData;
    }
}
