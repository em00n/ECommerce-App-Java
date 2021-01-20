package com.emon.ecommerceappjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.cart.CartModel;
import com.emon.ecommerceappjava.model.cart.CartModelDao;
import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.categori.CategoriModelDao;
import com.emon.ecommerceappjava.model.homepage.HomepageModelDao;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.model.product.ProductModelDao;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModelDao;
import com.emon.ecommerceappjava.room.AppDatabase;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.view.adapter.CartListAdapter;
import com.emon.ecommerceappjava.view.adapter.ProductListAdapter;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity implements CartListAdapter.RemoveToCartListener, CartListAdapter.PlusCartSelectListener, CartListAdapter.MinusCartSelectListener {
    Context context;
    AppDatabase appDatabase;
    HomepageModelDao homepageModelDao;
    CategoriModelDao categoriModelDao;
    ProductModelDao productModelDao;
    SubCategoriModelDao subCategoriModelDao;
    CartModelDao cartModelDao;

    private CartListAdapter cartListAdapter;
    private RecyclerView cartListRV;

    LinearLayout checkOutBTNLL;
    TextView totalAmountTV, deliveryChargeTV, priceTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        context = this;

        appDatabase = AppDatabase.getAppDatabase(this);
        homepageModelDao = appDatabase.homepageModelDao();
        categoriModelDao = appDatabase.categoriModelDao();
        productModelDao = appDatabase.productModelDao();
        subCategoriModelDao = appDatabase.subCategoriModelDao();
        cartModelDao = appDatabase.cartModelDao();

        cartListRV = findViewById(R.id.cartListRV);
        checkOutBTNLL = findViewById(R.id.checkOutBTNLL);
        totalAmountTV = findViewById(R.id.totalAmountTV);
        deliveryChargeTV = findViewById(R.id.deliveryChargeTV);
        priceTV = findViewById(R.id.priceTV);


        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView titleTV = findViewById(R.id.toolbartitleTV);
        titleTV.setText("My Cart");
        RelativeLayout rl = findViewById(R.id.tcartRL);
        // rl.setVisibility(View.GONE);

        setSupportActionBar(toolbar);


        setCartItems();
    }

    List<CartModel> getcartList() {
        return cartModelDao.getAllData();
    }


    void setCartItems() {
        cartListAdapter = new CartListAdapter(context, getcartList(), this, this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        cartListRV.setLayoutManager(layoutManager);
        cartListRV.setAdapter(cartListAdapter);
        setPrice();
    }

    void setPrice() {
        int price = 0, totalAmount = 0, deliveryCharge = 100;
        if (cartModelDao.count() == 0) {
            deliveryCharge = 0;
        }
        for (CartModel cartModel : cartModelDao.getAllData()) {
            price = price + Utils.cartPrice(cartModel.size, cartModel.productDiscount, cartModel.productPrice);
        }
        priceTV.setText("৳ " + price);
        deliveryChargeTV.setText("৳ " + deliveryCharge);
        totalAmount = price + deliveryCharge;
        totalAmountTV.setText("৳ " + totalAmount);
    }

    public void backButton(View view) {
        finish();
    }

    @Override
    public void onRemoveToCart(CartModel cartModel) {
        cartModelDao.delete(cartModel);
        setPrice();
        setCartItems();
    }

    @Override
    public void onPlusCartSelected(CartModel cartModel) {
        int size = cartModel.size + 1;
        cartModelDao.update(new CartModel(cartModel.id, cartModel.productId, cartModel.categoriId, cartModel.subCategoriId, cartModel.productName, cartModel.productPrice, cartModel.productDiscount, cartModel.productDetails, cartModel.productPicture, size));
        setPrice();
        setCartItems();
    }

    @Override
    public void onMinusCartSelected(CartModel cartModel) {
        if (cartModel.size > 1) {
            cartModelDao.update(new CartModel(cartModel.id, cartModel.productId, cartModel.categoriId, cartModel.subCategoriId, cartModel.productName, cartModel.productPrice, cartModel.productDiscount, cartModel.productDetails, cartModel.productPicture, cartModel.size - 1));
            setPrice();
            setCartItems();
        }
    }
}