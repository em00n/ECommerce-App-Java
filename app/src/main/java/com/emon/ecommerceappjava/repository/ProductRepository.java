package com.emon.ecommerceappjava.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.network.ApiInterface;
import com.emon.ecommerceappjava.network.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private MutableLiveData<List<ProductModel>> productListMutableLiveData;

    private Application application;
    public ApiInterface apiInterface;

    public ProductRepository(Application application) {
        this.application = application;
        if (productListMutableLiveData == null) {

            productListMutableLiveData = new MutableLiveData<>();

        }

        apiInterface = ApiService.getClient().create(ApiInterface.class);

    }


    public LiveData<List<ProductModel>> getProductData() {

        apiInterface.getProductData().enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                productListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                productListMutableLiveData.setValue(null);
            }
        });
        return productListMutableLiveData;
    }
}
