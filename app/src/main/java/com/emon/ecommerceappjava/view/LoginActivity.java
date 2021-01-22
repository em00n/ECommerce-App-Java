package com.emon.ecommerceappjava.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModelDao;
import com.emon.ecommerceappjava.model.login.LoginModel;
import com.emon.ecommerceappjava.model.login.LoginModelDao;
import com.emon.ecommerceappjava.room.AppDatabase;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.viewmodel.HomepageViewModel;
import com.emon.ecommerceappjava.viewmodel.LoginViewModel;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    AppDatabase appDatabase;
    LoginModelDao loginModelDao;

    EditText emailET, passwordET;
    TextView registrationTV;
    Button loginBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleTV = findViewById(R.id.toolbartitleTV);
        titleTV.setText("Login");

        setSupportActionBar(toolbar);

        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        registrationTV = findViewById(R.id.registrationTV);
        loginBTN = findViewById(R.id.loginBTN);

        appDatabase = AppDatabase.getAppDatabase(this);
        loginModelDao = appDatabase.loginModelDao();

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isConnected(LoginActivity.this)) {


                    if (Utils.emailValidation(emailET.getText().toString())) {
                        if (!TextUtils.isEmpty(passwordET.getText())) {

//                            getLoginData(emailET.getText().toString().trim(),passwordET.getText().toString().trim());

                            //test
                         LoginModel loginModel=   loginModelDao.getLoginData(emailET.getText().toString().trim(),passwordET.getText().toString().trim());
                         if (!loginModel.email.isEmpty()){
                             startActivity(new Intent(LoginActivity.this, SipmentInformationActivity.class));
                             finish();
                         }

                        } else {
                            passwordET.setError(getString(R.string.required_field));
                        }
                    } else {
                        emailET.setError(getString(R.string.invalidemail));
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Need Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        registrationTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

    }

    void getLoginData(String email, String password) {
        LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getLoginData(email, password).observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(@Nullable LoginModel loginModel) {
                loginModelDao.deleteAll();
                loginModelDao.insert(loginModel);
                startActivity(new Intent(LoginActivity.this, SipmentInformationActivity.class));
                finish();
            }
        });
    }

    public void backButton(View view) {
        finish();
    }
}