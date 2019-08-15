package com.fkh.support.ui.activity;

import android.content.Context;
import android.view.View;
import android.widget.AbsListView;

import com.fkh.support.ui.adapter.BaseListAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.List;

public abstract class RefreshLoadListViewActivity<T,ViewHolder> extends RefreshLoadActivity<T> {

    private AbsListView listView;
    private BaseListAdapter adapter;

    public void bindView(SmartRefreshLayout smartRefreshLayout, AbsListView listView, List<T> mData) {
        super.bindView(smartRefreshLayout, mData);
        this.listView = listView;
        adapter = new ListAdapter(this, mData);
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
    public void refreshData() {
        //选中第一行
        if (adapter.getCount() != 0 || (mData != null && !mData.isEmpty())) {
            listView.setSelection(0);
        }
        super.refreshData();
    }

    @Override
    public void dealError(String message) {
        super.dealError(message);
    }

    public abstract ViewHolder getViewHolder(View convertView);
    public abstract int getItemLayout();

    public abstract void initializeViews(int position, T t, ViewHolder holder);


    private class ListAdapter extends BaseListAdapter<T,ViewHolder> {

        public ListAdapter(Context context, List<T> list) {
            super(list, context);
        }

        @Override
        public int getItemLayout() {
            return RefreshLoadListViewActivity.this.getItemLayout();
        }

        @Override
        public ViewHolder getViewHolder(View convertView) {
            return RefreshLoadListViewActivity.this.getViewHolder(convertView);
        }

        @Override
        public void initializeViews(int position, T object, ViewHolder holder) {
            RefreshLoadListViewActivity.this.initializeViews(position, object, holder);
        }
    }
}
