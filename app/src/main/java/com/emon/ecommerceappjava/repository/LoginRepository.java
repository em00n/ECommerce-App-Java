package com.emon.ecommerceappjava.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.login.LoginModel;
import com.emon.ecommerceappjava.network.ApiInterface;
import com.emon.ecommerceappjava.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private MutableLiveData<LoginModel> loginListMutableLiveData;

    private Application application;
    public ApiInterface apiInterface;

    public LoginRepository(Application application) {
        this.application = application;
        if (loginListMutableLiveData == null) {

            loginListMutableLiveData = new MutableLiveData<>();

        }

        apiInterface = ApiService.getClient().create(ApiInterface.class);

    }


    public LiveData<LoginModel> getLoginData(String email,String password) {

        apiInterface.getLoginData(email, password).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                loginListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                loginListMutableLiveData.setValue(null);
            }
        });
        return loginListMutableLiveData;
    }
    public LiveData<LoginModel> registrationUser(LoginModel loginModel) {

        apiInterface.registrationUser(loginModel).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                loginListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<LoginModel> call, Throwable t) {
                loginListMutableLiveData.setValue(null);
            }
        });
        return loginListMutableLiveData;
    }
}