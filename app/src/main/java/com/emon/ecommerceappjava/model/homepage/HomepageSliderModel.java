package com.emon.ecommerceappjava.model.homepage;

public class HomepageSliderModel {
   public int id;
    public String categoriId;
    public String subCategoriId;
    public String slidePicture;
    public String discount;

    public HomepageSliderModel(int id, String categoriId, String subCategoriId, String slidePicture, String discount) {
        this.id = id;
        this.categoriId = categoriId;
        this.subCategoriId = subCategoriId;
        this.slidePicture = slidePicture;
        this.discount = discount;
    }
}
