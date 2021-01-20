package com.emon.ecommerceappjava.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.emon.ecommerceappjava.model.cart.CartModel;
import com.emon.ecommerceappjava.model.cart.CartModelDao;
import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.categori.CategoriModelDao;
import com.emon.ecommerceappjava.model.categori.SubcategoriConverter;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModelDao;
import com.emon.ecommerceappjava.model.homepage.HomepageSliderModelConverter;
import com.emon.ecommerceappjava.model.homepage.ImageListConverter;
import com.emon.ecommerceappjava.model.homepage.HomepageCategoriModelConverter;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.model.product.ProductModelDao;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModel;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModelDao;


@Database(entities = {CategoriModel.class, SubCategoriModel.class, HomepageModel.class, ProductModel.class, CartModel.class}, version = 1, exportSchema = false)
@TypeConverters({SubcategoriConverter.class, ImageListConverter.class, HomepageCategoriModelConverter.class, HomepageSliderModelConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract CategoriModelDao categoriModelDao();

    public abstract SubCategoriModelDao subCategoriModelDao();

    public abstract HomepageModelDao homepageModelDao();

    public abstract ProductModelDao productModelDao();

    public abstract CartModelDao cartModelDao();

    public static AppDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ECommerceApp")
                            // allow queries on the main thread.
                            // Don't do this on a real app! See PersistenceBasicSample for an example.
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}