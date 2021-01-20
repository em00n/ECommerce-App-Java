package com.emon.ecommerceappjava.model.subcate;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;

import java.util.List;

@Dao
public interface SubCategoriModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(SubCategoriModel subCategoriModel);

    @Query("SELECT * FROM subcategorimodel")
    List<SubCategoriModel> getAllData();

//    @Query("SELECT * FROM subcategorimodel WHERE `homepageSlidePictures` ")
//    List<String> gethomepageSlidePictures();
//
//    @Query("SELECT * FROM homepagemodel WHERE `homepageCategoriList` ")
//    List<String> gethomepageCategoriList();

    @Query("SELECT COUNT(*) FROM subcategorimodel")
    int count();

    @Query("Delete from subcategorimodel")
    void deleteAll();

    @Delete
    void delete(SubCategoriModel subCategoriModel);

    @Update
   void update(SubCategoriModel subCategoriModel);
}
