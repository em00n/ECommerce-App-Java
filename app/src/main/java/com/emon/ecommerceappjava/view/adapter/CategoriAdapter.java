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
import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.utils.Utils;
import com.emon.ecommerceappjava.view.ProductDetailsActivity;
import com.emon.ecommerceappjava.view.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoriAdapter extends RecyclerView.Adapter<CategoriAdapter.CategoriRecyclerViewHolder> {
    private Context context;
    private List<CategoriModel> categoriModelList;

    public CategoriAdapter(Context context, List<CategoriModel> categoriModelList) {
        this.context = context;
        this.categoriModelList = categoriModelList;
    }

    @NonNull
    @Override
    public CategoriAdapter.CategoriRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.categori_item, viewGroup, false);
        return new CategoriAdapter.CategoriRecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriAdapter.CategoriRecyclerViewHolder holder, int position) {

        CategoriModel categoriModel = categoriModelList.get(position);


        holder.categoriNameTV.setText(categoriModel.categoriName);

        Glide.with(context.getApplicationContext()).asBitmap().load(categoriModel.categoriPicture).into(holder.categoriIV);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("categoriId", String.valueOf(categoriModel.id));
                intent.putExtra("subcategoriId", String.valueOf("null"));
                intent.putExtra("categoriName", String.valueOf(categoriModel.categoriName));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoriModelList.size();
    }


    public static class CategoriRecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView categoriNameTV;
        ImageView categoriIV;

        public CategoriRecyclerViewHolder(View itemView) {

            super(itemView);
            //  group=(TextView) itemView.findViewById(R.id.group);
            categoriNameTV = (TextView) itemView.findViewById(R.id.categoriNameTV);
            categoriIV = (ImageView) itemView.findViewById(R.id.categoriIV);


        }
    }


}
