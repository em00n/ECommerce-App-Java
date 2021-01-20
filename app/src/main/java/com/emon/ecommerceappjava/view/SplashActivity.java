package com.emon.ecommerceappjava.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.categori.CategoriModelDao;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModelDao;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.model.product.ProductModelDao;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModel;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModelDao;
import com.emon.ecommerceappjava.network.ApiInterface;
import com.emon.ecommerceappjava.network.ApiService;
import com.emon.ecommerceappjava.room.AppDatabase;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.viewmodel.CategoriViewModel;
import com.emon.ecommerceappjava.viewmodel.HomepageViewModel;
import com.emon.ecommerceappjava.viewmodel.ProductViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    AppDatabase appDatabase;
    HomepageModelDao homepageModelDao;
    CategoriModelDao categoriModelDao;
    ProductModelDao productModelDao;
    SubCategoriModelDao subCategoriModelDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appDatabase = AppDatabase.getAppDatabase(this);
        homepageModelDao = appDatabase.homepageModelDao();
        categoriModelDao = appDatabase.categoriModelDao();
        productModelDao = appDatabase.productModelDao();
        subCategoriModelDao = appDatabase.subCategoriModelDao();

        if (!Utils.isConnected(SplashActivity.this)) {
            buildDialog(SplashActivity.this).show();
        } else {
            homepageModelDao.deleteAll();
            categoriModelDao.deleteAll();
            productModelDao.deleteAll();
            subCategoriModelDao.deleteAll();

            getCategoriData();
            getHomepageData();
            getProductData();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
            }, 5000);
        }

    }


    void getHomepageData() {
        HomepageViewModel homepageViewModel = ViewModelProviders.of(this).get(HomepageViewModel.class);
        homepageViewModel.getHomepageData().observe(this, new Observer<List<HomepageModel>>() {
            @Override
            public void onChanged(@Nullable List<HomepageModel> homepageModelLists) {
                List<HomepageModel> homepageModelList = homepageModelLists;
                for (HomepageModel homepageModel : homepageModelList) {
                    homepageModelDao.insert(homepageModel);

                }
            }
        });
    }

    void getCategoriData() {
        CategoriViewModel categoriViewModel = ViewModelProviders.of(this).get(CategoriViewModel.class);
        categoriViewModel.getCategoriData().observe(this, new Observer<List<CategoriModel>>() {
            @Override
            public void onChanged(@Nullable List<CategoriModel> categoriModelLists) {
                List<CategoriModel> categoriModelList = categoriModelLists;
                for (CategoriModel categoriModel : categoriModelList) {
                    categoriModelDao.insert(categoriModel);
                    for (SubCategoriModel subCategoriModel : categoriModel.getSubCategori()) {
                        subCategoriModelDao.insert(subCategoriModel);
                    }
                }
            }
        });
    }

    void getProductData() {
        ProductViewModel productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getProductData().observe(this, new Observer<List<ProductModel>>() {
            @Override
            public void onChanged(@Nullable List<ProductModel> productModelLists) {
                List<ProductModel> productModelList = productModelLists;
                for (ProductModel productModel : productModelList) {
                    productModelDao.insert(productModel);

                }
            }
        });
    }


    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Need Mobile Data or wifi to access this.");

        builder.setPositiveButton("REFRESH", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                refresh();

            }
        });

        return builder;
    }

    private void refresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!Utils.isConnected(SplashActivity.this)) {
                    buildDialog(SplashActivity.this).show();
                } else {
                    homepageModelDao.deleteAll();
                    categoriModelDao.deleteAll();
                    productModelDao.deleteAll();
                    subCategoriModelDao.deleteAll();

                    getCategoriData();
                    getHomepageData();
                    getProductData();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        }
                    }, 5000);
                }
            }
        }, 1000);

    }


}