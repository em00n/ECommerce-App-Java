package com.emon.ecommerceappjava.model.product;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;

import java.util.List;

@Dao
public interface ProductModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProductModel productModel);

    @Query("SELECT * FROM productmodel")
    List<ProductModel> getAllData();

//    @Query("SELECT * FROM productmodel WHERE `categoriId` LIKE:categoriID")
//    List<String> getDataByCategoriID(int categoriID);
//
//    @Query("SELECT * FROM productmodel WHERE `subCategoriId` LIKE:subcategoriID")
//    List<String> getDataBySubcategoriID(int subcategoriID);


    @Query("SELECT COUNT(*) FROM productmodel")
    int count();

    @Query("Delete from productmodel")
    void deleteAll();

    @Delete
    void delete(ProductModel productModel);

    @Update
   void update(ProductModel productModel);
}
