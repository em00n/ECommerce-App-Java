package com.emon.ecommerceappjava.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.homepage.HomepageCategoriModel;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.view.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.HomepageRecyclerViewHolder> {
    private Context context;
    private List<HomepageCategoriModel> categoriList;
    private List<ProductModel> productModelList;


    public HomepageAdapter(Context context, List<HomepageCategoriModel> categoriList, List<ProductModel> productModelList) {
        this.context = context;
        this.categoriList = categoriList;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public HomepageRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homepagelist_item, viewGroup, false);
        return new HomepageRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageRecyclerViewHolder holder, int position) {

        List<ProductModel> productList = new ArrayList<>();


        HomepageCategoriModel homepageCategoriModel = categoriList.get(position);
        for (ProductModel productModel : productModelList) {
            if (homepageCategoriModel.categoriId == productModel.categoriId) {
                if (productList.size() < 10) {
                    productList.add(productModel);
                }
            }
        }
        HomepageItemAdapter homepageItemAdapter = new HomepageItemAdapter(context, productList);


        holder.categorinameTV.setText(homepageCategoriModel.categoriName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder.homepageItemRV.setLayoutManager(layoutManager);
        // holder.homepageItemRV.setItemAnimator(RecyclerView.ItemAnimator.DefaultItemAnimator());
        holder.homepageItemRV.setAdapter(homepageItemAdapter);
        holder.seeMoreCategoriTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("categoriId", String.valueOf(homepageCategoriModel.categoriId));
                intent.putExtra("categoriName", String.valueOf(homepageCategoriModel.categoriName));


                context.startActivity(intent);
                // ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriList.size();
    }


    public static class HomepageRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView categorinameTV, seeMoreCategoriTV;
        RecyclerView homepageItemRV;

        public HomepageRecyclerViewHolder(View itemView) {

            super(itemView);

            categorinameTV = (TextView) itemView.findViewById(R.id.categoriNameTV);
            homepageItemRV = (RecyclerView) itemView.findViewById(R.id.homepageItemRV);
            seeMoreCategoriTV = (TextView) itemView.findViewById(R.id.seeMorecategoriTV);


        }
    }
}
