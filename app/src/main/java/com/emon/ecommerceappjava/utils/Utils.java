package com.emon.ecommerceappjava.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.emon.ecommerceappjava.model.cart.CartModel;

import java.util.List;

public class Utils {


    public static String afterDiscountPrice(String percentage, String price) {
        if (Integer.parseInt(percentage) <= 0) {
            return price;
        } else {
            int discountPrice = Integer.parseInt(price) * Integer.parseInt(percentage) / 100;
            int afterDiscountPrices = Integer.parseInt(price) - discountPrice;
            return String.valueOf(afterDiscountPrices);
        }
    }

    public static int cartPrice(int size, String percentage, String price) {
        if (Integer.parseInt(percentage) <= 0) {
            return Integer.parseInt(price) * size;
        } else {
            int discountPrice = Integer.parseInt(price) * Integer.parseInt(percentage) / 100;
            int afterDiscountPrices = Integer.parseInt(price) - discountPrice;
            return afterDiscountPrices * size;
        }
    }

    public static int cartLastId(List<CartModel> cartModelList) {
        if (cartModelList.size() != 0) {
            int id = cartModelList.get(cartModelList.size() - 1).id;
            return id + 1;
        } else return 1;
    }

    public static boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public static boolean emailValidation(String email){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(emailPattern))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

}
