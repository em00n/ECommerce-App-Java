package com.emon.ecommerceappjava.model.login;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emon.ecommerceappjava.model.product.ProductModel;

import java.util.List;

@Dao
public interface LoginModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LoginModel loginModel);

    @Query("SELECT * FROM login")
    List<LoginModel> getData();

    @Query("SELECT * FROM login WHERE email =:email and password=:password")
   LoginModel getLoginData(String email, String password);


    @Query("SELECT COUNT(*) FROM login")
    int count();

    @Query("Delete from login")
    void deleteAll();

    @Delete
    void delete(LoginModel loginModel);

    @Update
    void update(LoginModel loginModel);
}
