package com.emon.ecommerceappjava.model.homepage;

import androidx.room.TypeConverter;

import com.emon.ecommerceappjava.model.subcate.SubCategoriModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ImageListConverter {


    @TypeConverter
    public static List<String> fromString(String value) {
        Type listType = new TypeToken<List<String>>() {}.getType();
        List<String> notifications = new Gson().fromJson(value,listType);
        return notifications;
    }

    @TypeConverter
    public static String listToString(List<String> list) {
        Gson gson = new Gson();
        return gson.toJson(list);
    }
}
