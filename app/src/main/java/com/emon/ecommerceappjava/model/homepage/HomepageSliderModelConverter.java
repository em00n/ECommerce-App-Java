package com.emon.ecommerceappjava.model.homepage;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class HomepageSliderModelConverter {

        @TypeConverter
        public static List<HomepageSliderModel> fromString(String value) {
            Type listType = new TypeToken<List<HomepageSliderModel>>() {}.getType();
            List<HomepageSliderModel> notifications = new Gson().fromJson(value,listType);
            return notifications;
        }

        @TypeConverter
        public static String listToString(List<HomepageSliderModel> list) {
            Gson gson = new Gson();
            return gson.toJson(list);
        }

}
