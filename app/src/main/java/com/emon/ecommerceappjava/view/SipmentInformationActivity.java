package com.emon.ecommerceappjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

public class SipmentInformationActivity extends AppCompatActivity {
    AppDatabase appDatabase;
    LoginModelDao loginModelDao;

    EditText nameET, cityET, areaET, addressET,numberET,alternativenumberET;
    Button continueToPaymentBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sipment_information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleTV = findViewById(R.id.toolbartitleTV);
        titleTV.setText("Sipment Information");
        setSupportActionBar(toolbar);

        nameET = findViewById(R.id.usernameET);
        cityET = findViewById(R.id.cityET);
        areaET = findViewById(R.id.areaET);
        addressET = findViewById(R.id.addressET);
        numberET = findViewById(R.id.numberET);
        alternativenumberET = findViewById(R.id.alternativenumberET);
        continueToPaymentBTN = findViewById(R.id.continueTopaymentBTN);

        appDatabase = AppDatabase.getAppDatabase(this);
        loginModelDao = appDatabase.loginModelDao();

      for (LoginModel loginModel: loginModelDao.getData()){
          nameET.setText(loginModel.username);
      }
        continueToPaymentBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isConnected(SipmentInformationActivity.this)) {
                    if (!TextUtils.isEmpty(nameET.getText())) {
                        if (!TextUtils.isEmpty(cityET.getText())) {
                            if (!TextUtils.isEmpty(areaET.getText())) {
                                if (!TextUtils.isEmpty(addressET.getText())) {
                                    if (!TextUtils.isEmpty(numberET.getText())) {
                                        if (!TextUtils.isEmpty(alternativenumberET.getText())) {

                                            String name = nameET.getText().toString().trim();
                                            String city = cityET.getText().toString().trim();
                                            String area = areaET.getText().toString().trim();
                                            String address = addressET.getText().toString().trim();
                                            String number = numberET.getText().toString().trim();
                                            String alternativenumber = alternativenumberET.getText().toString().trim();


                                            startActivity(new Intent(SipmentInformationActivity.this, PaymentActivity.class));


                                        } else {
                                            alternativenumberET.setError(getString(R.string.required_field));
                                        }
                                    } else {
                                        numberET.setError(getString(R.string.required_field));
                                    }
                                }else {
                                        addressET.setError(getString(R.string.required_field));
                                    }
                            } else {
                                areaET.setError(getString(R.string.invalidemail));
                            }
                        } else {
                            cityET.setError(getString(R.string.required_field));
                        }
                    } else {
                        nameET.setError(getString(R.string.required_field));
                    }

                }else {
                    Toast.makeText(SipmentInformationActivity.this, "Need Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void backButton(View view) {
        finish();
    }
}