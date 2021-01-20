package com.emon.ecommerceappjava.view;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.cart.CartModel;
import com.emon.ecommerceappjava.model.cart.CartModelDao;
import com.emon.ecommerceappjava.model.categori.CategoriModelDao;
import com.emon.ecommerceappjava.model.homepage.HomepageCategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModelDao;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.model.product.ProductModelDao;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModelDao;
import com.emon.ecommerceappjava.room.AppDatabase;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.view.adapter.HomepageAdapter2;
import com.emon.ecommerceappjava.view.adapter.ProductListAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductListActivity extends AppCompatActivity implements ProductListAdapter.ProductAddToCartListener, SearchView.OnQueryTextListener {
    Context context;
    AppDatabase appDatabase;
    HomepageModelDao homepageModelDao;
    CategoriModelDao categoriModelDao;
    ProductModelDao productModelDao;
    SubCategoriModelDao subCategoriModelDao;
    CartModelDao cartModelDao;

    private ProductListAdapter productListAdapter;
    private RecyclerView productListRV;
    SearchView searchView;
    Toolbar toolbar;
    TextView titleTV;
    String categorisearch = "all", subcategorisearch = "null", title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        context = this;

        appDatabase = AppDatabase.getAppDatabase(this);
        homepageModelDao = appDatabase.homepageModelDao();
        categoriModelDao = appDatabase.categoriModelDao();
        productModelDao = appDatabase.productModelDao();
        subCategoriModelDao = appDatabase.subCategoriModelDao();
        cartModelDao = appDatabase.cartModelDao();

        productListRV = findViewById(R.id.productListRV);
        toolbar = findViewById(R.id.toolbar);
        titleTV = findViewById(R.id.toolbartitleTV);
        searchView = findViewById(R.id.searchview);

        setSupportActionBar(toolbar);


        categorisearch = getIntent().getStringExtra("categoriId");
        title = getIntent().getStringExtra("categoriName");

        if (title.equalsIgnoreCase("null")) {
            title = "Search Here";
        }
        try {
            subcategorisearch = getIntent().getStringExtra("subcategoriId");
        } catch (Exception e) {
            subcategorisearch = "null";
        }

        setProductItems();
        cartListSize();

        titleTV.setText(title);
        searchView.setQueryHint(title);
        searchView.setOnQueryTextListener(this);
        int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        EditText editText = (EditText) searchView.findViewById(id);
        editText.setTextColor(Color.WHITE);
        editText.setHintTextColor(Color.WHITE);
    }

    @Override
    protected void onResume() {
        cartListSize();
        super.onResume();
    }

    List<ProductModel> getProductList() {
        List<ProductModel> productListActivityList = new ArrayList<>();
        if (categorisearch.equalsIgnoreCase("all") && subcategorisearch.equalsIgnoreCase("null")) {
            return productModelDao.getAllData();
        } else if (!subcategorisearch.equalsIgnoreCase("null")) {
            for (ProductModel productModel : productModelDao.getAllData()) {
                if (categorisearch.equalsIgnoreCase(String.valueOf(productModel.categoriId)) && subcategorisearch.equalsIgnoreCase(String.valueOf(productModel.subCategoriId))) {
                    productListActivityList.add(productModel);
                }
            }
            return productListActivityList;
        } else {
            for (ProductModel productModel : productModelDao.getAllData()) {
                if (categorisearch.equalsIgnoreCase(String.valueOf(productModel.categoriId))) {
                    productListActivityList.add(productModel);
                }
            }
            return productListActivityList;
        }
    }


    void setProductItems() {
        productListAdapter = new ProductListAdapter(context, getProductList(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        productListRV.setLayoutManager(layoutManager);
        productListRV.setAdapter(productListAdapter);
    }


    public void backButton(View view) {
        finish();
    }

    public void cartButton(View view) {
        startActivity(new Intent(context, CartActivity.class));
    }

    void cartListSize() {
        int size = cartModelDao.count();

        TextView cartSizeTV = findViewById(R.id.cartSizeTV);
        cartSizeTV.setText("" + size);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (!newText.equals("")) {
            newText = newText.toLowerCase();
            List<ProductModel> productModelList = new ArrayList<>();

            for (ProductModel productModel : getProductList()) {
                String productName;
                productName = productModel.getProductName().toLowerCase();
                if (productName.contains(newText)) {
                    productModelList.add(productModel);
                }
            }
            productListAdapter.setFilter(productModelList);
        } else {
            productListAdapter.setFilter(getProductList());
        }

        return true;
    }

    @Override
    public void onProductAddToCart(ProductModel productModel) {
        if (productModel.isStock) {
            int id = Utils.cartLastId(cartModelDao.getAllData());
            cartModelDao.insert(new CartModel(id, productModel.id, productModel.categoriId, productModel.subCategoriId, productModel.productName, productModel.productPrice, productModel.productDiscount, productModel.productDetails, productModel.productPicture, 1));
            cartListSize();
        }
    }
}