package com.rongqi.hua.rongqihua.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fkh.support.ui.fragment.RefreshLoadListViewFragment;
import com.rongqi.hua.rongqihua.R;

/**
 * Created by dinghu on 2019/8/16.
 */
public class YejiFragment extends RefreshLoadListViewFragment<Integer, YejiFragment.ViewHolder> {

    class ViewHolder {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yeji, null);
        return view;
    }

    @Override
    public ViewHolder getViewHolder(View convertView) {
        return null;
    }

    @Override
    public void initializeViews(int position, Integer integer, ViewHolder viewHolder) {

    }

    @Override
    public void getData(int page, boolean isRefreh) {

    }
}
