package com.emon.ecommerceappjava.model.homepage;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface HomepageModelDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(HomepageModel homepageModel);

    @Query("SELECT * FROM homepagemodel")
    List<HomepageModel> getAllData();

//    @Query("SELECT * FROM homepagemodel WHERE `homepageSlidePictures` ")
//    List<String> gethomepageSlidePictures();
//
//    @Query("SELECT * FROM homepagemodel WHERE `homepageCategoriList` ")
//    List<String> gethomepageCategoriList();

    @Query("SELECT COUNT(*) FROM homepagemodel")
    int count();

    @Query("Delete from homepagemodel")
    void deleteAll();

    @Delete
    void delete(HomepageModel homepageModel);

    @Update
   void update(HomepageModel homepageModel);
}
