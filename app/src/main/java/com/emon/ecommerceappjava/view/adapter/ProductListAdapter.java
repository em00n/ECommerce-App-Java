package com.emon.ecommerceappjava.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.view.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductListRecyclerViewHolder> {
    private Context context;
    private List<ProductModel> productModelList;
    public ProductAddToCartListener productAddToCartListener;

    public ProductListAdapter(Context context, List<ProductModel> productModelList, ProductAddToCartListener productAddToCartListener) {
        this.context = context;
        this.productModelList = productModelList;
        this.productAddToCartListener = productAddToCartListener;
    }

    @NonNull
    @Override
    public ProductListAdapter.ProductListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_list_item, viewGroup, false);
        return new ProductListAdapter.ProductListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListAdapter.ProductListRecyclerViewHolder holder, int position) {
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

        if (!productModel.isStock) {
            holder.addToCartTV.setText("Stock Out");
            holder.addToCartTV.setClickable(false);
        }
        holder.addToCartTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productAddToCartListener.onProductAddToCart(productModel);
            }
        });

        if (!productModel.isStock) {
            holder.addToCartTV.setText("Stock Out");
            holder.addToCartTV.setClickable(false);
        }

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


    public static class ProductListRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTV, cPriceTV, realPriceTV;
        ImageView productIV;
        TextView addToCartTV;

        public ProductListRecyclerViewHolder(View itemView) {

            super(itemView);
            //  group=(TextView) itemView.findViewById(R.id.group);
            productNameTV = (TextView) itemView.findViewById(R.id.productNameTV);
            cPriceTV = (TextView) itemView.findViewById(R.id.cPriceTV);
            realPriceTV = (TextView) itemView.findViewById(R.id.realPriceTV);
            productIV = (ImageView) itemView.findViewById(R.id.productIV);
            addToCartTV = (TextView) itemView.findViewById(R.id.addToCartTV);


        }
    }

    public void setFilter(List<ProductModel> searchproductModelList) {
        productModelList = new ArrayList<>();

        productModelList.clear();
        productModelList.addAll(searchproductModelList);

        notifyDataSetChanged();
    }

    public interface ProductAddToCartListener {
        void onProductAddToCart(ProductModel productModel);
    }
}
