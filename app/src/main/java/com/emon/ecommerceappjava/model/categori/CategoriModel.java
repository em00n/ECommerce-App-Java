package com.emon.ecommerceappjava.model.categori;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emon.ecommerceappjava.model.subcate.SubCategoriModel;

import java.util.List;
@Entity(tableName = "categorimodel")
@Keep
public class CategoriModel {
    @PrimaryKey
   public Integer id;
    public String categoriName;
    public String categoriPicture;
    @TypeConverters(SubcategoriConverter.class)
    public List<SubCategoriModel> subCategori;

    public CategoriModel(Integer id, String categoriName, String categoriPicture, List<SubCategoriModel> subCategori) {
        this.id = id;
        this.categoriName = categoriName;
        this.categoriPicture = categoriPicture;
        this.subCategori = subCategori;
    }

    public Integer getId() {
        return id;
    }

    public String getCategoriName() {
        return categoriName;
    }

    public String getCategoriPicture() {
        return categoriPicture;
    }

    public List<SubCategoriModel> getSubCategori() {
        return subCategori;
    }
}
