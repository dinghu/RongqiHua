package com.fkh.support.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseListAdapter<T, ViewHolder> extends BaseAdapter {

    protected List<T> mList;
    protected Context mContext;

    public BaseListAdapter(List<T> list, Context context) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public T getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public abstract int getItemLayout();

    public abstract ViewHolder getViewHolder(View convertView);

    public abstract void initializeViews(int position, T t, ViewHolder holder);

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(getItemLayout(), null);
            convertView.setTag(getViewHolder(convertView));
        }
        initializeViews(position, getItem(position), (ViewHolder) convertView.getTag());
        return convertView;
    }


}
