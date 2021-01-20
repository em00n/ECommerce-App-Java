package com.emon.ecommerceappjava.model.subcate;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "subcategorimodel")
@Keep
public class SubCategoriModel {
    @PrimaryKey
    public Integer id;
    public Integer categoriId;
    public String subCategoriName;
    public String subCategoriPicture;

    public SubCategoriModel(Integer id, Integer categoriId, String subCategoriName, String subCategoriPicture) {
        this.id = id;
        this.categoriId = categoriId;
        this.subCategoriName = subCategoriName;
        this.subCategoriPicture = subCategoriPicture;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCategoriId() {
        return categoriId;
    }

    public String getSubCategoriName() {
        return subCategoriName;
    }

    public String getSubCategoriPicture() {
        return subCategoriPicture;
    }
}
