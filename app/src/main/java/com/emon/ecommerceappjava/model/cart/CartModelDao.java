package com.emon.ecommerceappjava.model.cart;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emon.ecommerceappjava.model.product.ProductModel;

import java.util.List;

@Dao
public interface CartModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CartModel cartModel);

    @Query("SELECT * FROM cartmodel")
    List<CartModel> getAllData();

//    @Query("SELECT * FROM productmodel WHERE `categoriId` LIKE:categoriID")
//    List<String> getDataByCategoriID(int categoriID);
//
//    @Query("SELECT * FROM productmodel WHERE `subCategoriId` LIKE:subcategoriID")
//    List<String> getDataBySubcategoriID(int subcategoriID);


    @Query("SELECT COUNT(*) FROM cartmodel")
    int count();

    @Query("Delete from cartmodel")
    void deleteAll();

    @Delete
    void delete(CartModel cartModel);

    @Update
   void update(CartModel cartModel);
}
