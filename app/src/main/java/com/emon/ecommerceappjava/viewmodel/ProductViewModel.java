package com.emon.ecommerceappjava.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.repository.HomepageRepository;
import com.emon.ecommerceappjava.repository.ProductRepository;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    LiveData<List<ProductModel>> dataObsarvable;
    private ProductRepository productRepository;


    public ProductViewModel(@NonNull Application application) {
        super(application);

        productRepository = new ProductRepository(application);
        dataObsarvable=productRepository.getProductData();
    }

    public LiveData<List<ProductModel>> getProductData(){

        return dataObsarvable;
    }


}