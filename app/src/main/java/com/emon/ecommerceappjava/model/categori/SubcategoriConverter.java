package com.emon.ecommerceappjava.model.categori;

import androidx.room.TypeConverter;

import com.emon.ecommerceappjava.model.subcate.SubCategoriModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class SubcategoriConverter {

        @TypeConverter
        public static List<SubCategoriModel> fromString(String value) {
            Type listType = new TypeToken<List<SubCategoriModel>>() {}.getType();
            List<SubCategoriModel> notifications = new Gson().fromJson(value,listType);
            return notifications;
        }

        @TypeConverter
        public static String listToString(List<SubCategoriModel> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }

}
