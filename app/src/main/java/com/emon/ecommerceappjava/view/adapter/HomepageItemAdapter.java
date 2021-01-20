package com.emon.ecommerceappjava.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.view.ProductDetailsActivity;


import java.util.List;

public class HomepageItemAdapter extends RecyclerView.Adapter<HomepageItemAdapter.HomepageItemRecyclerViewHolder> {
    private Context context;
    private List<ProductModel> productModelList;


    public HomepageItemAdapter(Context context, List<ProductModel> productModelList) {
        this.context = context;
        this.productModelList = productModelList;
    }

    @NonNull
    @Override
    public HomepageItemAdapter.HomepageItemRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.homepage_item, viewGroup, false);
        return new HomepageItemAdapter.HomepageItemRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomepageItemAdapter.HomepageItemRecyclerViewHolder holder, int position) {
        int colorblack = Color.BLACK;
        ProductModel productModel = productModelList.get(position);

        if (productModel.productDiscount.equalsIgnoreCase("0")) {
            holder.cPriceTV.setVisibility(View.GONE);
            holder.realPriceTV.setTextColor(colorblack);
        } else {
            holder.realPriceTV.setPaintFlags(holder.realPriceTV.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        holder.productNameTV.setText(productModel.productName);
        holder.cPriceTV.setText("৳ " + Utils.afterDiscountPrice(productModel.productDiscount, productModel.productPrice) + "(" + productModel.productDiscount + "% off)");
        holder.realPriceTV.setText("৳ " + productModel.productPrice);
        Glide.with(context.getApplicationContext()).asBitmap().load(productModel.productPicture).into(holder.productIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);

                intent.putExtra("id", productModel.id);
                intent.putExtra("categoriId", productModel.categoriId);
                intent.putExtra("subCategoriId", productModel.subCategoriId);

                context.startActivity(intent);
                //  ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModelList.size();
    }


    public static class HomepageItemRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTV, cPriceTV, realPriceTV;
        ImageView productIV;

        public HomepageItemRecyclerViewHolder(View itemView) {

            super(itemView);
            //  group=(TextView) itemView.findViewById(R.id.group);
            productNameTV = (TextView) itemView.findViewById(R.id.productNameTV);
            cPriceTV = (TextView) itemView.findViewById(R.id.cPriceTV);
            realPriceTV = (TextView) itemView.findViewById(R.id.realPriceTV);
            productIV = (ImageView) itemView.findViewById(R.id.productIV);


        }
    }
}
