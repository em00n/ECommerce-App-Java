package com.emon.ecommerceappjava.model.homepage;

public class HomepageCategoriModel {
   public int id;
    public int categoriId;
    public String categoriName;

    public HomepageCategoriModel(int id, int categoriId, String categoriName) {
        this.id = id;
        this.categoriId = categoriId;
        this.categoriName = categoriName;
    }
}
