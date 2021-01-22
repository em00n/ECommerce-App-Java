package com.emon.ecommerceappjava.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.login.LoginModel;
import com.emon.ecommerceappjava.repository.HomepageRepository;
import com.emon.ecommerceappjava.repository.LoginRepository;

import java.util.List;

public class LoginViewModel extends AndroidViewModel {
    LiveData<LoginModel> dataObsarvable;
    private LoginRepository loginRepository;


    public LoginViewModel(@NonNull Application application) {
        super(application);

        loginRepository = new LoginRepository(application);

    }

    public LiveData<LoginModel> getLoginData(String email, String password) {

        return dataObsarvable = loginRepository.getLoginData(email, password);
    }

    public LiveData<LoginModel> registrationUser(LoginModel loginModel) {

        return dataObsarvable = loginRepository.registrationUser(loginModel);
    }

}