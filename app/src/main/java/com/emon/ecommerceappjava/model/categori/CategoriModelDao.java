package com.emon.ecommerceappjava.model.categori;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.emon.ecommerceappjava.model.homepage.HomepageModel;

import java.util.List;

@Dao
public interface CategoriModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(CategoriModel categoriModel);

    @Query("SELECT * FROM categorimodel")
    List<CategoriModel> getAllData();


//    @Query("SELECT * FROM homepagemodel WHERE `homepageSlidePictures`")
//    List<String> gethomepageSlidePictures();
//
//    @Query("SELECT * FROM homepagemodel WHERE `homepageCategoriList` ")
//    List<String> gethomepageCategoriList();

    @Query("SELECT COUNT(*) FROM categorimodel")
    int count();

    @Query("Delete from categorimodel")
    void deleteAll();

    @Delete
    void delete(CategoriModel categoriModel);

    @Update
   void update(CategoriModel categoriModel);
}
