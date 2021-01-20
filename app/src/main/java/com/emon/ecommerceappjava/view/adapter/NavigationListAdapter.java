package com.emon.ecommerceappjava.view.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.emon.ecommerceappjava.R;
import com.emon.ecommerceappjava.model.categori.CategoriModel;
import com.emon.ecommerceappjava.model.subcate.SubCategoriModel;

import java.util.HashMap;
import java.util.List;

public class NavigationListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> categoriList;
    private HashMap<String, List<SubCategoriModel>> subcategoriList;

    public NavigationListAdapter(Context context, List<String> categoriList,
                                 HashMap<String, List<SubCategoriModel>> subcategoriList) {
        this.context = context;
        this.categoriList = categoriList;
        this.subcategoriList = subcategoriList;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return this.subcategoriList.get(this.categoriList.get(listPosition))
                .get(expandedListPosition).subCategoriName;
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(listPosition, expandedListPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group_child, null);
        }
        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.childTV);
        expandedListTextView.setText(expandedListText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return this.subcategoriList.get(this.categoriList.get(listPosition))
                .size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return this.categoriList.get(listPosition);
    }

    @Override
    public int getGroupCount() {
        return this.categoriList.size();
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(listPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group_header, null);
        }
        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.headerTV);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return true;
    }
}