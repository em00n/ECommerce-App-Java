package com.emon.ecommerceappjava.model.homepage;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.List;
@Entity(tableName = "homepagemodel")
@Keep
public class HomepageModel {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @TypeConverters(HomepageSliderModelConverter.class)
    public List<HomepageSliderModel> homepageSlideList;

    @TypeConverters(HomepageCategoriModelConverter.class)
    public List<HomepageCategoriModel> homepageCategoriList;


    public HomepageModel(List<HomepageSliderModel> homepageSlideList, List<HomepageCategoriModel> homepageCategoriList) {
        this.homepageSlideList = homepageSlideList;
        this.homepageCategoriList = homepageCategoriList;
    }

    public List<HomepageSliderModel> getHomepageSlidePictures() {
        return homepageSlideList;
    }

    public void setHomepageSlidePictures(List<HomepageSliderModel> homepageSlidePictures) {
        this.homepageSlideList = homepageSlidePictures;
    }

    public List<HomepageCategoriModel> getHomepageCategoriList() {
        return homepageCategoriList;
    }

    public void setHomepageCategoriList(List<HomepageCategoriModel> homepageCategoriList) {
        this.homepageCategoriList = homepageCategoriList;
    }

}
