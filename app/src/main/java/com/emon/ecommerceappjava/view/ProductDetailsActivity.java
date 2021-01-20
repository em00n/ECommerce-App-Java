package com.emon.ecommerceappjava.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


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
import com.emon.ecommerceappjava.view.adapter.HomepageItemAdapter;
import com.emon.ecommerceappjava.view.adapter.ImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {
    Context context;
    AppDatabase appDatabase;
    HomepageModelDao homepageModelDao;
    CategoriModelDao categoriModelDao;
    ProductModelDao productModelDao;
    SubCategoriModelDao subCategoriModelDao;
    CartModelDao cartModelDao;

    TextView productNameTV, cPriceTV, realPriceTV, discountTV, detailsTV, seeMoreCategoriTV, addToCartTV;
    RecyclerView productRelativeItemRV;
    LinearLayout addToCartLL;
    TextView titleTV;

    int id, categoriId, subcategoriId;

    ViewPager viewPager;

    List<String> imageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        context = this;
        Toolbar toolbar = findViewById(R.id.toolbar);
        titleTV = findViewById(R.id.toolbartitleTV);
        setSupportActionBar(toolbar);

        productNameTV = findViewById(R.id.productNameTV);
        cPriceTV = findViewById(R.id.cPriceTV);
        realPriceTV = findViewById(R.id.realPriceTV);
        discountTV = findViewById(R.id.discountTV);
        detailsTV = findViewById(R.id.productDetailsTV);
        seeMoreCategoriTV = findViewById(R.id.seeMorecategoriTV);
        productRelativeItemRV = findViewById(R.id.productRelativeItemRV);
        addToCartLL = findViewById(R.id.addToCartLL);
        addToCartTV = findViewById(R.id.addToCartTV);

        viewPager = findViewById(R.id.viewPager);
        imageList = new ArrayList<>();


        appDatabase = AppDatabase.getAppDatabase(this);
        homepageModelDao = appDatabase.homepageModelDao();
        categoriModelDao = appDatabase.categoriModelDao();
        productModelDao = appDatabase.productModelDao();
        subCategoriModelDao = appDatabase.subCategoriModelDao();
        cartModelDao = appDatabase.cartModelDao();

        id = getIntent().getIntExtra("id", 0);
        categoriId = getIntent().getIntExtra("categoriId", 0);
        subcategoriId = getIntent().getIntExtra("subCategoriId", 0);

        setData();
        cartListSize();

    }

    @Override
    protected void onResume() {
        cartListSize();
        super.onResume();
    }

    void setData() {
        int colorblack = Color.BLACK;
        for (ProductModel productModel : productModelDao.getAllData()) {
            if (id == productModel.id && categoriId == productModel.categoriId && subcategoriId == productModel.subCategoriId) {
                if (productModel.productDiscount.equalsIgnoreCase("0")) {
                    cPriceTV.setVisibility(View.GONE);
                    discountTV.setVisibility(View.GONE);
                    realPriceTV.setTextColor(colorblack);
                } else {
                    realPriceTV.setPaintFlags(realPriceTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                }

                titleTV.setText(productModel.productName);
                productNameTV.setText(productModel.productName);
                cPriceTV.setText("৳ " + Utils.afterDiscountPrice(productModel.productDiscount, productModel.productPrice));
                realPriceTV.setText("৳ " + productModel.productPrice);
                discountTV.setText("(" + productModel.productDiscount + "% off)");
                detailsTV.setText(productModel.productDetails);

                imageList.addAll(productModel.productPictures);
                ViewPager mViewPager = (ViewPager) findViewById(R.id.viewPager);
                ImageAdapter adapterView = new ImageAdapter(this, imageList);
                mViewPager.setAdapter(adapterView);

                if (!productModel.isStock) {
                    addToCartTV.setText("Stock Out");
                    addToCartLL.setClickable(false);
                }
                if (productModel.isStock) {
                    addToCartLL.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = Utils.cartLastId(cartModelDao.getAllData());
                            cartModelDao.insert(new CartModel(id, productModel.id, productModel.categoriId, productModel.subCategoriId, productModel.productName, productModel.productPrice, productModel.productDiscount, productModel.productDetails, productModel.productPicture, 1));
                            cartListSize();
                        }
                    });
                }
            }
        }

        HomepageItemAdapter homepageItemAdapter = new HomepageItemAdapter(context, getRelatedCategoriProductList());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        productRelativeItemRV.setLayoutManager(layoutManager);
        // holder.homepageItemRV.setItemAnimator(RecyclerView.ItemAnimator.DefaultItemAnimator());
        productRelativeItemRV.setAdapter(homepageItemAdapter);

        for (CategoriModel categoriModel : categoriModelDao.getAllData()) {
            if (categoriId == categoriModel.id) {
                seeMoreCategoriTV.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProductListActivity.class);
                        intent.putExtra("categoriId", String.valueOf(categoriId));
                        intent.putExtra("subcategoriId", String.valueOf(subcategoriId));
                        intent.putExtra("categoriName", String.valueOf(categoriModel.categoriName));

                        context.startActivity(intent);
                        // ((Activity) context).finish();
                    }
                });
            }
        }


    }


    List<ProductModel> getRelatedCategoriProductList() {
        List<ProductModel> productModelList = new ArrayList<>();
        for (ProductModel productModel : productModelDao.getAllData()) {
            if (categoriId == productModel.categoriId && subcategoriId == productModel.subCategoriId) {
                if (productModelList.size() < 10) {
                    productModelList.add(productModel);
                }
            }
        }
        return productModelList;
    }


    public void cartButton(View view) {
        startActivity(new Intent(context, CartActivity.class));
    }

    public void backButton(View view) {
        finish();
    }

    void cartListSize() {
        int size = cartModelDao.count();

        TextView cartSizeTV = findViewById(R.id.cartSizeTV);
        cartSizeTV.setText("" + size);
    }


}
