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
import com.emon.ecommerceappjava.model.cart.CartModel;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.view.ProductDetailsActivity;

import java.util.ArrayList;
import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.CartListRecyclerViewHolder> {
    private Context context;
    private List<CartModel> cartModelList;
    public PlusCartSelectListener plusCartSelectListener;
    public MinusCartSelectListener minusCartSelectListener;
    public RemoveToCartListener removeToCartListener;

    public CartListAdapter(Context context, List<CartModel> cartModelList, PlusCartSelectListener plusCartSelectListener, MinusCartSelectListener minusCartSelectListener, RemoveToCartListener removeToCartListener) {
        this.context = context;
        this.cartModelList = cartModelList;
        this.plusCartSelectListener = plusCartSelectListener;
        this.minusCartSelectListener = minusCartSelectListener;
        this.removeToCartListener = removeToCartListener;
    }

    @NonNull
    @Override
    public CartListAdapter.CartListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item, viewGroup, false);
        return new CartListAdapter.CartListRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.CartListRecyclerViewHolder holder, int position) {

        CartModel cartModel = cartModelList.get(position);


        holder.productNameTV.setText(cartModel.productName);
        holder.cartItemSizeCountTV.setText(String.valueOf(cartModel.size));
        holder.cPriceTV.setText("৳ " + Utils.afterDiscountPrice(cartModel.productDiscount, cartModel.productPrice) + "*" + cartModel.size);
        holder.totalPriceTV.setText("৳ " + Utils.cartPrice(cartModel.size, cartModel.productDiscount, cartModel.productPrice));
        Glide.with(context.getApplicationContext()).asBitmap().load(cartModel.productPicture).into(holder.productIV);

        holder.plusCartIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusCartSelectListener.onPlusCartSelected(cartModel);
                // notifyDataSetChanged();
            }
        });
        holder.minusCartIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                minusCartSelectListener.onMinusCartSelected(cartModel);
                //  notifyDataSetChanged();

            }
        });
        holder.removeToCartLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeToCartListener.onRemoveToCart(cartModel);
                notifyDataSetChanged();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailsActivity.class);

                intent.putExtra("id", cartModel.productId);
                intent.putExtra("categoriId", cartModel.categoriId);
                intent.putExtra("subCategoriId", cartModel.subCategoriId);

                context.startActivity(intent);
                //  ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }


    public static class CartListRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTV, cPriceTV, totalPriceTV;
        ImageView productIV, plusCartIV, minusCartIV;
        TextView removeToCartLL, cartItemSizeCountTV;

        public CartListRecyclerViewHolder(View itemView) {

            super(itemView);
            //  group=(TextView) itemView.findViewById(R.id.group);
            productNameTV = (TextView) itemView.findViewById(R.id.productNameTV);
            cPriceTV = (TextView) itemView.findViewById(R.id.cPriceTV);
            totalPriceTV = (TextView) itemView.findViewById(R.id.ptotalPriceTV);
            productIV = (ImageView) itemView.findViewById(R.id.productIV);
            plusCartIV = (ImageView) itemView.findViewById(R.id.plusCartIV);
            minusCartIV = (ImageView) itemView.findViewById(R.id.minusCartIV);
            removeToCartLL = (TextView) itemView.findViewById(R.id.removeToCart);
            cartItemSizeCountTV = (TextView) itemView.findViewById(R.id.cartItemSizeCountTV);


        }
    }


    public interface PlusCartSelectListener {
        void onPlusCartSelected(CartModel cartModel);
    }

    public interface MinusCartSelectListener {
        void onMinusCartSelected(CartModel cartModel);
    }

    public interface RemoveToCartListener {
        void onRemoveToCart(CartModel cartModel);
    }
}
