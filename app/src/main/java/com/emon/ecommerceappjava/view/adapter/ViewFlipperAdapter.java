package com.emon.ecommerceappjava.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.cart.CartModel;
import com.emon.ecommerceappjava.model.homepage.HomepageSliderModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ViewFlipperAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<HomepageSliderModel> list;
    FlipListener flipListener;
    FlipItemClickListener flipItemClickListener;

    public ViewFlipperAdapter(Context context, ArrayList<HomepageSliderModel> list, FlipListener flipListener, FlipItemClickListener flipItemClickListener) {
        this.context = context;
        this.list = list;
        this.flipListener = flipListener;
        this.flipItemClickListener = flipItemClickListener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        HomepageSliderModel model = list.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.viewpager_item, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.viewpagerimageView);

        flipListener.onflip(position);

        Glide.with(context.getApplicationContext()).asBitmap().load(model.slidePicture).into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipItemClickListener.onflipItemClick(model);
            }
        });

        return view;
    }

    public interface FlipListener {
        void onflip(int position);
    }

    public interface FlipItemClickListener {
        void onflipItemClick(HomepageSliderModel homepageSliderModel);
    }
}