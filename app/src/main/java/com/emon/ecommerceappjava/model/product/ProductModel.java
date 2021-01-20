package com.emon.ecommerceappjava.model.product;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.emon.ecommerceappjava.model.homepage.ImageListConverter;

import java.util.List;
@Entity(tableName = "productmodel")
@Keep
public class ProductModel {
    @PrimaryKey
    public Integer id;
    public Integer categoriId;
    public Integer subCategoriId;
    public String productName;
    public String productPrice;
    public String productDiscount;
    public String productDetails;
    public String productPicture;
    public boolean isStock;
    @TypeConverters(ImageListConverter.class)
    public List<String> productPictures;

    public ProductModel(Integer id, Integer categoriId, Integer subCategoriId, String productName, String productPrice, String productDiscount, String productDetails, String productPicture,boolean isStock, List<String> productPictures) {
        this.id = id;
        this.categoriId = categoriId;
        this.subCategoriId = subCategoriId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productDetails = productDetails;
        this.productPicture = productPicture;
        this.isStock = isStock;
        this.productPictures = productPictures;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCategoriId() {
        return categoriId;
    }

    public Integer getSubCategoriId() {
        return subCategoriId;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public String getProductDiscount() {
        return productDiscount;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public String getProductPicture() {
        return productPicture;
    }

    public List<String> getProductPictures() {
        return productPictures;
    }
}
