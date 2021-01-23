package com.emon.ecommerceappjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.cart.CartModel;
import com.emon.ecommerceappjava.model.cart.CartModelDao;
import com.emon.ecommerceappjava.model.login.LoginModel;
import com.emon.ecommerceappjava.model.login.LoginModelDao;
import com.emon.ecommerceappjava.room.AppDatabase;
import com.emon.ecommerceappjava.utils.Utils;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCCustomerInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCProductInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCShipmentInfoInitializer;
import com.sslwireless.sslcommerzlibrary.model.initializer.SSLCommerzInitialization;
import com.sslwireless.sslcommerzlibrary.model.response.SSLCTransactionInfoModel;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCCurrencyType;
import com.sslwireless.sslcommerzlibrary.model.util.SSLCSdkType;
import com.sslwireless.sslcommerzlibrary.view.singleton.IntegrateSSLCommerz;
import com.sslwireless.sslcommerzlibrary.viewmodel.listener.SSLCTransactionResponseListener;

import java.util.Random;

public class PaymentActivity extends AppCompatActivity implements SSLCTransactionResponseListener {
    AppDatabase appDatabase;
    CartModelDao cartModelDao;
    LoginModelDao loginModelDao;
    Context context;
    TextView paymentTV;
    ImageView successIV, faildIV;

    String customerName, customerEmail;
    int totalAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleTV = findViewById(R.id.toolbartitleTV);
        titleTV.setText("Payment");
        setSupportActionBar(toolbar);

        appDatabase = AppDatabase.getAppDatabase(this);
        cartModelDao = appDatabase.cartModelDao();
        loginModelDao = appDatabase.loginModelDao();


        context = this;
        paymentTV = findViewById(R.id.paymentTV);
        successIV = findViewById(R.id.successIv);
        faildIV = findViewById(R.id.unsuccessIv);

        getPriceAndNameEmail();
        doPay();
    }

    void getPriceAndNameEmail() {
        int price = 0, deliveryCharge = 100;

        for (CartModel cartModel : cartModelDao.getAllData()) {
            price = price + Utils.cartPrice(cartModel.size, cartModel.productDiscount, cartModel.productPrice);
        }
        totalAmount = price + deliveryCharge;

        for (LoginModel loginModel : loginModelDao.getData()) {
            customerName = loginModel.username;
            customerEmail = loginModel.email;
        }
    }

    void doPay() {
        int rand = new Random().nextInt();
        final SSLCommerzInitialization sslCommerzInitialization = new SSLCommerzInitialization("store_id", "store_password", totalAmount, SSLCCurrencyType.BDT, "trans_" + rand, "yourProductType", SSLCSdkType.TESTBOX);

        final SSLCCustomerInfoInitializer customerInfoInitializer = new SSLCCustomerInfoInitializer(customerName, customerEmail,
                "address", "dhaka", "1214", "Bangladesh", "01000000000");

        final SSLCProductInitializer productInitializer = new SSLCProductInitializer("food", "food",
                new SSLCProductInitializer.ProductProfile.TravelVertical("Travel", "10",
                        "A", "12", "Dhk-Syl"));

        final SSLCShipmentInfoInitializer shipmentInfoInitializer = new SSLCShipmentInfoInitializer("Courier",
                2, new SSLCShipmentInfoInitializer.ShipmentDetails("AA", "Address 1",
                "Dhaka", "1000", "BD"));

        IntegrateSSLCommerz
                .getInstance(context)
                .addSSLCommerzInitialization(sslCommerzInitialization)
                .addCustomerInfoInitializer(customerInfoInitializer)
                .addProductInitializer(productInitializer)
                .buildApiCall(this);


    }

    @Override
    public void transactionSuccess(SSLCTransactionInfoModel sslcTransactionInfoModel) {
        faildIV.setVisibility(View.GONE);
        successIV.setVisibility(View.VISIBLE);
        paymentTV.setVisibility(View.VISIBLE);
        paymentTV.setText("Payment Successful");
        paymentTV.setTextColor(getResources().getColor(R.color.green));

        cartModelDao.deleteAll();
    }


    @Override
    public void transactionFail(String s) {
        successIV.setVisibility(View.GONE);
        faildIV.setVisibility(View.VISIBLE);
        paymentTV.setVisibility(View.VISIBLE);
        paymentTV.setText("Payment Failed");
        paymentTV.setTextColor(getResources().getColor(R.color.red));
    }

    @Override
    public void merchantValidationError(String s) {
        successIV.setVisibility(View.GONE);
        faildIV.setVisibility(View.VISIBLE);
        paymentTV.setVisibility(View.VISIBLE);
        paymentTV.setText("Payment Failed");
        paymentTV.setTextColor(getResources().getColor(R.color.red));
    }

    public void backButton(View view) {
        finish();
    }
}