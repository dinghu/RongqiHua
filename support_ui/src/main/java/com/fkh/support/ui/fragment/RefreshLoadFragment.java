package com.fkh.support.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fkh.support.ui.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public abstract class RefreshLoadFragment<T> extends BaseFragment {
    protected SmartRefreshLayout swipeRefreshLayout;
    protected List<T> mData;
    protected View noData;
    protected int page = 1;
    protected long mLastLoadingTime = 0;

    public void setNoDataView(View noData) {
        this.noData = noData;
    }

    public abstract void getData(int page, boolean isRefreh);

    protected void bindView(SmartRefreshLayout swipeRefreshLayout, List<T> mData) {
        this.swipeRefreshLayout = swipeRefreshLayout;
        this.mData = mData;
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                RefreshLoadFragment.this.swipeRefreshLayout.autoRefresh(200);
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                mLastLoadingTime = System.currentTimeMillis();
                page = 1;
                getData(page, true);
            }
        });
        swipeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                mLastLoadingTime = System.currentTimeMillis();
                getData(++page, false);
            }
        });
        swipeRefreshLayout.setEnableLoadMore(false);
    }


    public void notifyDataSetChanged(boolean noMoreData) {
        if (mData != null && mData.isEmpty()) {
            if (noData != null) {
                noData.setVisibility(View.VISIBLE);
            }
        } else {
            if (noData != null) {
                noData.setVisibility(View.GONE);
            }
        }

        boolean isRefresh = swipeRefreshLayout.isRefreshing();

        if (isRefresh) {
            swipeRefreshLayout.finishRefresh();
            if (!noMoreData) {
                swipeRefreshLayout.setNoMoreData(false);
                swipeRefreshLayout.setEnableLoadMore(true);
            }
        } else {
            if (!noMoreData) {
                swipeRefreshLayout.finishLoadMore();
            } else {
                swipeRefreshLayout.finishLoadMoreWithNoMoreData();
            }
        }

    }

    public void refreshData() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                if (noData != null) {
                    noData.setVisibility(View.GONE);
                }
                page = 1;
                swipeRefreshLayout.autoRefresh();
            }
        });

    }

    public void dealDataRecive(final List<T> reciveData, final boolean noMoreData) {
        final boolean isRefresh = swipeRefreshLayout.isRefreshing();
        long passTime = System.currentTimeMillis() - mLastLoadingTime;

        //保持最少1秒得延时
        int needDelay = Math.max(0, 200 - (int) passTime);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isRefresh) {
                    mData.clear();
                }
                mData.addAll(reciveData);
                notifyDataSetChanged(noMoreData);
            }
        }, needDelay);

    }

    public void dealError(String message) {
        boolean isRefresh = swipeRefreshLayout.isRefreshing();
        if (isRefresh) {
            swipeRefreshLayout.finishRefresh();
        } else {
            swipeRefreshLayout.finishLoadMore();
        }
        Toast.makeText(getContext(),message,Toast.LENGTH_LONG).show();
    }
}
