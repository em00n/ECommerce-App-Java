package com.emon.ecommerceappjava.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageCategoriModel;
import com.emon.ecommerceappjava.model.homepage.HomepageSliderModel;
import com.emon.ecommerceappjava.model.product.ProductModel;
import com.emon.ecommerceappjava.view.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

public class HomepageAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<HomepageCategoriModel> homepageCategoriModelList;
    private List<ProductModel> productModelList;
    private List<CategoriModel> categoriModelList;
    private List<HomepageSliderModel> homepageSliderModelList;
    private LayoutInflater mLayoutInflater;

    private static final int DEFAULT_VIEW_TYPE = 1;
    private static final int EXTRA_VIEW_TYPE = 2;

    public HomepageAdapter2(Context context, List<HomepageCategoriModel> homepageCategoriModelList, List<ProductModel> productModelList, List<CategoriModel> categoriModelList, List<HomepageSliderModel> homepageSliderModelList) {
        this.context = context;
        this.homepageCategoriModelList = homepageCategoriModelList;
        this.productModelList = productModelList;
        this.categoriModelList = categoriModelList;
        this.homepageSliderModelList = homepageSliderModelList;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < 1) {
            return EXTRA_VIEW_TYPE;
        }
        return DEFAULT_VIEW_TYPE;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        switch (viewType) {
            case EXTRA_VIEW_TYPE:
                v = mLayoutInflater.inflate(R.layout.homepage_top, parent, false);
                return new ExtraViewHolder(v);
            default:
                v = mLayoutInflater.inflate(R.layout.homepagelist_item, parent, false);
                return new HomepageRecyclerViewHolder2(v);

        }
    }


    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

//holder.imageView.setImageResource(imageModels.get(position).getUrl());
        if (!(holder instanceof HomepageRecyclerViewHolder2)) {
            return;
        }
        HomepageRecyclerViewHolder2 holder1 = (HomepageRecyclerViewHolder2) holder;

        List<ProductModel> productList = new ArrayList<>();


        HomepageCategoriModel homepageCategoriModel = homepageCategoriModelList.get(position);
        for (ProductModel productModel : productModelList) {
            if (homepageCategoriModel.categoriId == productModel.categoriId) {
                if (productList.size() < 10) {
                    productList.add(productModel);
                }
            }
        }
        HomepageItemAdapter homepageItemAdapter = new HomepageItemAdapter(context, productList);


        holder1.categorinameTV.setText(homepageCategoriModel.categoriName);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        holder1.homepageItemRV.setLayoutManager(layoutManager);
        // holder.homepageItemRV.setItemAnimator(RecyclerView.ItemAnimator.DefaultItemAnimator());
        holder1.homepageItemRV.setAdapter(homepageItemAdapter);
        holder1.seeMoreCategoriTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductListActivity.class);
                intent.putExtra("categoriId", String.valueOf(homepageCategoriModel.categoriId));
                intent.putExtra("subcategoriId", String.valueOf("null"));
                intent.putExtra("categoriName", String.valueOf(homepageCategoriModel.categoriName));

                context.startActivity(intent);
                // ((Activity) context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return homepageCategoriModelList.size();
    }

    public class HomepageRecyclerViewHolder2 extends RecyclerView.ViewHolder {

        TextView categorinameTV, seeMoreCategoriTV;
        RecyclerView homepageItemRV;


        public HomepageRecyclerViewHolder2(View itemView) {
            super(itemView);

            categorinameTV = (TextView) itemView.findViewById(R.id.categoriNameTV);
            homepageItemRV = (RecyclerView) itemView.findViewById(R.id.homepageItemRV);
            seeMoreCategoriTV = (TextView) itemView.findViewById(R.id.seeMorecategoriTV);

        }

    }

    public class ExtraViewHolder extends RecyclerView.ViewHolder implements ViewFlipperAdapter.FlipListener, ViewFlipperAdapter.FlipItemClickListener {


        RecyclerView homepageCategoriRV;

        AdapterViewFlipper aviewFlipper;
        LinearLayout sliderDotspanel;
        private int dotscount;
        private ImageView[] dots;

        public ExtraViewHolder(final View itemView) {
            super(itemView);

            homepageCategoriRV = itemView.findViewById(R.id.homepageCategoriRV);
            aviewFlipper = itemView.findViewById(R.id.viewFlipper);

            sliderDotspanel = (LinearLayout) itemView.findViewById(R.id.SliderDots);

            //  Glide.with(context.getApplicationContext()).asBitmap().load(homepageSliderModelList.get(0).slidePicture).into(homepageImageSlider);
            CategoriAdapter categoriAdapter = new CategoriAdapter(context, categoriModelList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
            homepageCategoriRV.setLayoutManager(layoutManager);
            homepageCategoriRV.setAdapter(categoriAdapter);

            setViewFlipper();
        }

        public void setViewFlipper() {


            dotscount = homepageSliderModelList.size();
            dots = new ImageView[dotscount];

            for (int i = 0; i < dotscount; i++) {

                dots[i] = new ImageView(context);
                // dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_white_dot_1_24));
                Glide.with(context).asBitmap().load(R.drawable.nonactive_dot).into(dots[i]);

                float d = context.getResources().getDisplayMetrics().density;
                int height = (int) (10 * d);
                int width = (int) (10 * d);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
                params.setMargins(8, 0, 8, 0);

                sliderDotspanel.addView(dots[i], params);

            }

            Glide.with(context).asBitmap().load(R.drawable.active_dot).into(dots[0]);

            //creating adapter object
            ViewFlipperAdapter adapter = new ViewFlipperAdapter(context, (ArrayList<HomepageSliderModel>) homepageSliderModelList, this, this);

            //adding it to adapterview flipper
            aviewFlipper.setAdapter(adapter);
            aviewFlipper.setFlipInterval(3000);
            aviewFlipper.setAutoStart(true);
            aviewFlipper.setInAnimation(context, R.animator.right_in);
            aviewFlipper.setOutAnimation(context, R.animator.slide_out_left);
            aviewFlipper.startFlipping();


        }

        @Override
        public void onflip(int position) {
            for (int i = 0; i < dotscount; i++) {

                Glide.with(context).asBitmap().load(R.drawable.nonactive_dot).into(dots[i]);
            }
            Glide.with(context).asBitmap().load(R.drawable.active_dot).into(dots[position]);
        }

        @Override
        public void onflipItemClick(HomepageSliderModel homepageSliderModel) {
            Intent intent = new Intent(context, ProductListActivity.class);
            intent.putExtra("categoriId", String.valueOf(homepageSliderModel.categoriId));
            intent.putExtra("subcategoriId", String.valueOf("null"));
            intent.putExtra("categoriName", String.valueOf("null"));

            context.startActivity(intent);
        }
    }
}