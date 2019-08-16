package com.fkh.support.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.fkh.support.ui.R;
import com.fkh.support.ui.adapter.BaseListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public abstract class RefreshLoadListViewFragment<T,ViewHolder> extends RefreshLoadFragment<T> {
    private AbsListView listView;
    private BaseListAdapter adapter;
    private int itemLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_refresh_load_common, null);
        return view;
    }

    public void bindView(SmartRefreshLayout smartRefreshLayout, AbsListView listView, List<T> mData, int itemLayout) {
        super.bindView(smartRefreshLayout, mData);
        this.listView = listView;
        this.itemLayout = itemLayout;
        adapter = new RefreshLoadListViewFragment.ListAdapter(getContext(), mData);
        this.listView.setAdapter(this.adapter);
    }

    public AbsListView getListView() {
        return listView;
    }

    public BaseListAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void notifyDataSetChanged(boolean noMoreData) {
        super.notifyDataSetChanged(noMoreData);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void dealError(String message) {
        super.dealError(message);
    }

    public abstract ViewHolder getViewHolder(View convertView);

    public abstract void initializeViews(int position, T t, ViewHolder holder);


    private class ListAdapter extends BaseListAdapter<T,ViewHolder> {

        public ListAdapter(Context context, List<T> list) {
            super(list, context);
        }

        @Override
        public int getItemLayout() {
            return itemLayout;
        }

        @Override
        public ViewHolder getViewHolder(View convertView) {
            return RefreshLoadListViewFragment.this.getViewHolder(convertView);
        }

        @Override
        public void initializeViews(int position, T object, ViewHolder holder) {
            RefreshLoadListViewFragment.this.initializeViews(position, object, holder);
        }
    }
}
