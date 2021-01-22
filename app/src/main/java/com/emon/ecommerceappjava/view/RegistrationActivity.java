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
import com.emon.ecommerceappjava.model.login.LoginModel;
import com.emon.ecommerceappjava.model.login.LoginModelDao;
import com.emon.ecommerceappjava.room.AppDatabase;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.viewmodel.LoginViewModel;

public class RegistrationActivity extends AppCompatActivity {
    AppDatabase appDatabase;
    LoginModelDao loginModelDao;

    EditText nameET, numberET, emailET, passwordET;
    TextView loginTV;
    Button registrationBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleTV = findViewById(R.id.toolbartitleTV);
        titleTV.setText("Registration");

        setSupportActionBar(toolbar);

        nameET = findViewById(R.id.usernameET);
        numberET = findViewById(R.id.numberET);
        emailET = findViewById(R.id.emailET);
        passwordET = findViewById(R.id.passwordET);
        loginTV = findViewById(R.id.loginTV);
        registrationBTN = findViewById(R.id.registrationBTN);

        appDatabase = AppDatabase.getAppDatabase(this);
        loginModelDao = appDatabase.loginModelDao();

        registrationBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isConnected(RegistrationActivity.this)) {
                    if (!TextUtils.isEmpty(nameET.getText())) {
                        if (!TextUtils.isEmpty(numberET.getText())) {
                            if (Utils.emailValidation(emailET.getText().toString())) {
                                if (!TextUtils.isEmpty(passwordET.getText())) {

                                    String name=nameET.getText().toString().trim();
                                    String number=numberET.getText().toString().trim();
                                    String email=emailET.getText().toString().trim();
                                    String password=passwordET.getText().toString().trim();

//                                    registrationUser(new LoginModel(name,number,email,password));

                                    //test
                                    loginModelDao.deleteAll();
                                    loginModelDao.insert(new LoginModel(name,number,email,password));
                                    startActivity(new Intent(RegistrationActivity.this, SipmentInformationActivity.class));
                                    finish();

                                } else {
                                    passwordET.setError(getString(R.string.required_field));
                                }
                            } else {
                                emailET.setError(getString(R.string.invalidemail));
                            }
                        } else {
                            numberET.setError(getString(R.string.required_field));
                        }
                    } else {
                        nameET.setError(getString(R.string.required_field));
                    }

                }else {
                    Toast.makeText(RegistrationActivity.this, "Need Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    void registrationUser(LoginModel loginModel) {
        LoginViewModel loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.registrationUser(loginModel).observe(this, new Observer<LoginModel>() {
            @Override
            public void onChanged(@Nullable LoginModel loginModel) {
                loginModelDao.deleteAll();
                loginModelDao.insert(loginModel);
                startActivity(new Intent(RegistrationActivity.this, SipmentInformationActivity.class));
                finish();
            }
        });
    }

    public void backButton(View view) {
        finish();
    }
}