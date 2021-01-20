package com.emon.ecommerceappjava.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ExpandedMenuView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.cart.CartModelDao;
import com.emon.ecommerceappjava.model.categori.CategoriModelDao;
import com.emon.ecommerceappjava.model.homepage.HomepageCategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModelDao;
import com.emon.ecommerceappjava.model.homepage.HomepageSliderModel;
import com.emon.ecommerceappjava.model.product.ProductModelDao;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModel;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModelDao;
import com.emon.ecommerceappjava.room.AppDatabase;
import com.emon.ecommerceappjava.view.adapter.HomepageAdapter;
import com.emon.ecommerceappjava.view.adapter.HomepageAdapter2;
import com.emon.ecommerceappjava.view.adapter.HomepageItemAdapter;
import com.emon.ecommerceappjava.view.adapter.NavigationListAdapter;
import com.emon.ecommerceappjava.viewmodel.HomepageViewModel;
import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageModel;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.network.ApiInterface;
import com.emon.ecommerceappjava.network.ApiService;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    Context context;
    AppDatabase appDatabase;
    HomepageModelDao homepageModelDao;
    CategoriModelDao categoriModelDao;
    ProductModelDao productModelDao;
    SubCategoriModelDao subCategoriModelDao;
    CartModelDao cartModelDao;

    private HomepageAdapter2 homepageAdapter;
    private RecyclerView homepageItemRV;

    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;
    List<String> navigationCategoriList;
    HashMap<String, List<SubCategoriModel>> navigationSubCategoriList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        Toolbar toolbar = findViewById(R.id.toolbar1);
        TextView title = findViewById(R.id.toolbartitleTV);
        title.setText(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        appDatabase = AppDatabase.getAppDatabase(this);
        homepageModelDao = appDatabase.homepageModelDao();
        categoriModelDao = appDatabase.categoriModelDao();
        productModelDao = appDatabase.productModelDao();
        subCategoriModelDao = appDatabase.subCategoriModelDao();
        cartModelDao = appDatabase.cartModelDao();

        homepageItemRV = findViewById(R.id.homepageListRV);

        setHomepageProductItems();
        setNavigationList();

        cartListSize();


        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        //  navView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        cartListSize();
        super.onResume();
    }

    List<ProductModel> getProductList() {

        return productModelDao.getAllData();
    }

    List<HomepageCategoriModel> getHomepageCategoriList() {
        List<HomepageCategoriModel> homepageCategoriModelList = new ArrayList<>();
        homepageCategoriModelList.add(new HomepageCategoriModel(0, 0, " "));
        for (HomepageModel homepageModel : homepageModelDao.getAllData()) {
            // Log.d("TST", "hL" + homepageCategoriModel.categoriName);
            homepageCategoriModelList.addAll(homepageModel.homepageCategoriList);
        }
        return homepageCategoriModelList;
    }


    List<HomepageSliderModel> homepageSlideingList() {
        List<HomepageSliderModel> homepageSliderModelList = new ArrayList<>();
        for (HomepageModel homepageModel : homepageModelDao.getAllData()) {
            // Log.d("TST", "hL" + homepageCategoriModel.categoriName);
            homepageSliderModelList.addAll(homepageModel.homepageSlideList);
        }
        return homepageSliderModelList;
    }

    List<CategoriModel> getCategoriModelList() {
        return categoriModelDao.getAllData();
    }

    HashMap<String, List<SubCategoriModel>> getNavigationSubCategori() {
        HashMap<String, List<SubCategoriModel>> expandableListDetail = new HashMap<String, List<SubCategoriModel>>();

        for (CategoriModel categoriModel : categoriModelDao.getAllData()) {

            expandableListDetail.put(categoriModel.categoriName, categoriModel.subCategori);

        }
        return expandableListDetail;
    }

    List<String> getNavigationCategori() {
        List<String> categoriModelList = new ArrayList<>();
        for (CategoriModel categoriModel : categoriModelDao.getAllData()) {

            categoriModelList.add(categoriModel.categoriName);
            // }
        }
        return categoriModelList;
    }

    void setHomepageProductItems() {
        homepageAdapter = new HomepageAdapter2(context, getHomepageCategoriList(), getProductList(), getCategoriModelList(), homepageSlideingList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        homepageItemRV.setLayoutManager(layoutManager);
        homepageItemRV.setAdapter(homepageAdapter);
    }

    void setNavigationList() {
        expandableListView = (ExpandableListView) findViewById(R.id.expandableListView);
        navigationSubCategoriList = getNavigationSubCategori();
        navigationCategoriList = new ArrayList<String>(getNavigationCategori());
        expandableListAdapter = new NavigationListAdapter(this, navigationCategoriList, navigationSubCategoriList);
        expandableListView.setAdapter(expandableListAdapter);


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {

                if (parent.getChildCount() == 0) {

                    Intent intent = new Intent(context, ProductListActivity.class);
                    intent.putExtra("categoriId", String.valueOf(groupPosition));
                    intent.putExtra("categoriName", String.valueOf(navigationCategoriList.get(groupPosition)));

                    startActivity(intent);
                }
                return false;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("categoriId", String.valueOf(navigationSubCategoriList.get(navigationCategoriList.get(groupPosition)).get(childPosition).categoriId));
                intent.putExtra("subcategoriId", String.valueOf(navigationSubCategoriList.get(navigationCategoriList.get(groupPosition)).get(childPosition).id));
                intent.putExtra("categoriName", String.valueOf(navigationSubCategoriList.get(navigationCategoriList.get(groupPosition)).get(childPosition).subCategoriName));

                startActivity(intent);
                return false;
            }
        });
    }

    void cartListSize() {
        int size = cartModelDao.count();

        TextView cartSizeTV = findViewById(R.id.cartSizeTV);
        cartSizeTV.setText("" + size);
    }

    //....................
    List<ProductModel> getProductByCategori(int categori) {
        List<ProductModel> productModelList = new ArrayList<>();
        for (ProductModel productModel : productModelDao.getAllData()) {

            if (productModel.categoriId == categori) {
                productModelList.add(productModel);
            }
        }
        return productModelList;
    }

    List<ProductModel> getProductBySubCategori(int categori, int subcategori) {
        List<ProductModel> productModelList = new ArrayList<>();
        for (ProductModel productModel : productModelDao.getAllData()) {

            if (productModel.categoriId == categori && productModel.subCategoriId == subcategori) {
                productModelList.add(productModel);
            }
        }
        return productModelList;
    }

    public void cartButton(View view) {
        startActivity(new Intent(context, CartActivity.class));
    }

}