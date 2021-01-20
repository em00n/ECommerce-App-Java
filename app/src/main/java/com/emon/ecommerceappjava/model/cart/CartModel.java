package com.emon.ecommerceappjava.model.cart;

import androidx.annotation.Keep;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "cartmodel")
@Keep
public class CartModel {
    @PrimaryKey
    public Integer id;
    public Integer productId;
    public Integer categoriId;
    public Integer subCategoriId;
    public String productName;
    public String productPrice;
    public String productDiscount;
    public String productDetails;
    public String productPicture;
    public Integer size;

    public CartModel(Integer id,Integer productId, Integer categoriId, Integer subCategoriId, String productName, String productPrice, String productDiscount, String productDetails, String productPicture, Integer size) {
        this.id = id;
        this.productId = productId;
        this.categoriId = categoriId;
        this.subCategoriId = subCategoriId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productDiscount = productDiscount;
        this.productDetails = productDetails;
        this.productPicture = productPicture;
        this.size = size;
    }
}
