package com.emon.ecommerceappjava.network;

import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.product.ProductModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("homepageItem")
    Call<List<HomepageModel>> getHomepageData();

    @GET("categori")
    Call<List<CategoriModel>> getCategoriData();

    @GET("itemList")
    Call<List<ProductModel>> getProductData();

//    @GET ("photos")
//    Call<List<Model>> getModel(@QueryMap Map<String,String> peramiters);
//
//    @GET("photos")
//    Call<List<Model>> getModel(
//            @Query("albumId") Integer[] userId,
//            @Query("_sort") String sort,
//            @Query("_order") String order
//    );
}
