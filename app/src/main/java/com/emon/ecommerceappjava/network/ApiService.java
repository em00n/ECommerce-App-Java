package com.emon.ecommerceappjava.network;

import java.util.Base64;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

        public static final String BaseUrl="https://my-json-server.typicode.com/em00n/ContactManagementSystem/";
        private static Retrofit retrofit=null;

        public static Retrofit getClient(){
        if (retrofit == null) {
            retrofit=new retrofit2.Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
