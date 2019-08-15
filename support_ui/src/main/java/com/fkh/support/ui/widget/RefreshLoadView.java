package com.fkh.support.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.fkh.support.ui.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * 继承自SwipeRefreshLayout,从而实现滑动到底部时上拉加载更多的功能.
 */
public class RefreshLoadView extends SwipeRefreshLayout implements
        AbsListView.OnScrollListener {

    /**
     * 滑动到最下面时的上拉操作
     */

    private int mTouchSlop;
    /**
     * listview实例
     */
    private View mChildView;

    /**
     * 上拉监听器, 到了最底部的上拉加载操作
     */
    private OnLoadListener mOnLoadListener;

    public void setmOnScrollListener(OnScrollListener mOnScrollListener) {
        this.mOnScrollListener = mOnScrollListener;
    }

    private OnScrollListener mOnScrollListener;
    /**
     * ListView的加载中footer
     */
    private View mListViewFooter;

    /**
     * 按下时的y坐标
     */
    private int mYDown;
    /**
     * 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
     */
    private int mLastY;
    /**
     * 是否在加载中 ( 上拉加载更多 )
     */
    private boolean isLoading = false;

    private TextView pull_to_refresh_loadmore_text;

    public void setEnableLoadMore(boolean enableLoadMore) {
        isEnableLoadMore = enableLoadMore;
    }

    private boolean isEnableLoadMore = true;
    private ProgressBar pull_to_refresh_load_progress;
    private Object adapter;


    /**
     * @param context
     */
    public RefreshLoadView(Context context) {
        this(context, null);
    }

    @SuppressLint("InflateParams")
    public RefreshLoadView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        mListViewFooter = mListViewFooter = LayoutInflater.from(context).inflate(
                R.layout.listview_footer, null, false);
        // mListViewFooter = mListViewFooter.findViewById(R.id.footer);
        pull_to_refresh_loadmore_text = (TextView) mListViewFooter.findViewById(R.id.pull_to_refresh_loadmore_text);
        pull_to_refresh_load_progress = (ProgressBar) mListViewFooter.findViewById(R.id.pull_to_refresh_load_progress);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始化ListView对象
        if (mChildView == null) {
            getListView();
        }
    }


    public void setAdapter(Object adapter) {
        this.adapter = adapter;
    }


    /**
     * 滑动监听
     */
    private class RecyclerViewOnScrollListener extends RecyclerView.OnScrollListener {

        public RecyclerViewOnScrollListener() {
            super();
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            if (newState == RecyclerView.SCROLL_STATE_IDLE && !isNomoreData && isEnableLoadMore && canLoad()) {
                loadData();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

        }

    }

    /**
     * 获取ListView对象
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof RecyclerView) {
                mChildView = childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                //((RecyclerView) mChildView).setOnScrollListener(this);
                if (!hasInitLoadMoreView) {
                    hasInitLoadMoreView = true;
                    ((RecyclerView) mChildView).setAdapter((RecyclerAdapterWithHF) adapter);
                    ((RecyclerView) mChildView).addOnScrollListener(new RecyclerViewOnScrollListener());
                    if (((RecyclerAdapterWithHF) adapter).getFootSize() <= 0 && null != mListViewFooter) {
                        ((RecyclerAdapterWithHF) adapter).addFooter(mListViewFooter);
                    }
                    mListViewFooter.setVisibility(View.INVISIBLE);
                }
                Log.d(VIEW_LOG_TAG, "### 找到listview");
            } else if (childView instanceof ListView) {
                mChildView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                ((ListView) mChildView).setOnScrollListener(this);
                if (!hasInitLoadMoreView) {
                    hasInitLoadMoreView = true;
                    ((ListView) mChildView).addFooterView(mListViewFooter);
                    if (((ListView) mChildView).getFooterViewsCount() > 0 && null != mListViewFooter) {
                        ((ListView) mChildView).addFooterView(mListViewFooter);
                    }
                    ((ListView) mChildView).setAdapter((ListAdapter) adapter);
                    // 添加只是为了在ListView的setAdapter方法时将Adapter包装成HeaderViewListAdapter。因此并不需要footer，因此添加后再移除,
                    ((ListView) mChildView).removeFooterView(mListViewFooter);
                }
                Log.d(VIEW_LOG_TAG, "### 找到listview");
            } else if (childView instanceof GridViewWithHeaderAndFooter) {
                mChildView = (GridViewWithHeaderAndFooter) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                ((GridViewWithHeaderAndFooter) mChildView).setOnScrollListener(this);
                if (!hasInitLoadMoreView) {
                    ((GridViewWithHeaderAndFooter) mChildView).addFooterView(mListViewFooter);
                    mListViewFooter.setVisibility(View.GONE);
                    ((GridViewWithHeaderAndFooter) mChildView).setAdapter((ListAdapter) adapter);
                    hasInitLoadMoreView = true;
                }

            }
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 按下
                mYDown = (int) event.getRawY();
                mLastY = mYDown;
                break;

            case MotionEvent.ACTION_MOVE:
                // 移动
                mLastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_UP:
                // 抬起
                if (!isNomoreData && isEnableLoadMore && canLoad()) {
                    loadData();
                }
                break;
            default:
                break;
        }

        return super.dispatchTouchEvent(event);
    }

    @Override
    public void setRefreshing(boolean refreshing) {
        if (refreshing == false) {
            hasInitLoadMoreView = false;
        }

        isNomoreData = false;

        super.setRefreshing(refreshing);
    }

    /**
     * 是否可以加载更多, 条件是到了最底部, listview不在加载中, 且为上拉操作.
     *
     * @return
     */
    private boolean canLoad() {
        return isBottom() && !isLoading && isPullUp() && !isRefreshing();
    }

    private boolean isCanScollVertically(RecyclerView recyclerView) {
        if (android.os.Build.VERSION.SDK_INT < 14) {
            return ViewCompat.canScrollVertically(recyclerView, 1) || recyclerView.getScrollY() < recyclerView.getHeight();
        } else {
            return ViewCompat.canScrollVertically(recyclerView, 1);
        }
    }

    /**
     * 判断是否到了最底部
     */
    private boolean isBottom() {
        if (mChildView instanceof RecyclerView) {
            RecyclerView mListView = (RecyclerView) mChildView;
            if (mListView != null && mListView.getAdapter() != null) {
                return !isCanScollVertically(mListView);
            }
        } else if (mChildView instanceof ListView) {
            ListView mListView = (ListView) mChildView;
            if (mListView != null && mListView.getAdapter() != null) {
                return mListView.getLastVisiblePosition() == (mListView
                        .getAdapter().getCount() - 1);
            }
        } else if (mChildView instanceof GridView) {
            GridView mGridView = (GridView) mChildView;
            if (mGridView != null && mGridView.getAdapter() != null) {
                return mGridView.getLastVisiblePosition() == (mGridView
                        .getAdapter().getCount() - 1);
            }
        }


        return false;
    }

    /**
     * 是否是上拉操作
     *
     * @return
     */
    private boolean isPullUp() {
        Log.i("KK", "mYDown:" + mYDown);
        Log.i("KK", "mLastY:" + mLastY);
        Log.i("KK", "mTouchSlop:" + mTouchSlop);
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 如果到了最底部,而且是上拉操作.那么执行onLoad方法
     */
    private void loadData() {
        if (mOnLoadListener != null) {
            // 设置状态
            setLoading(true);
            //
            mOnLoadListener.onLoad();
        }
    }

    boolean isNomoreData = false;

    public void setNoMoreData(boolean isNomore) {
        if (mChildView instanceof ListView) {
            if (((ListView) mChildView).getFooterViewsCount() <= 0 && null != mListViewFooter) {
                ((ListView) mChildView).addFooterView(mListViewFooter);
            }
        } else if (mChildView instanceof RecyclerView) {
            if (((RecyclerAdapterWithHF) adapter).getFootSize() <= 0 && null != mListViewFooter) {
                ((RecyclerAdapterWithHF) adapter).addFooter(mListViewFooter);
            }
        }
        mListViewFooter.setVisibility(VISIBLE);
        pull_to_refresh_loadmore_text.setText("没有更多结果了");
        mListViewFooter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        pull_to_refresh_load_progress.setVisibility(View.GONE);
        isNomoreData = true;
    }

    boolean hasInitLoadMoreView = false;

    /**
     * @param loading
     */
    public void setLoading(boolean loading) {

        if (this.isLoading == loading) {
            return;
        }
        pull_to_refresh_loadmore_text.setText("加载更多");
        pull_to_refresh_load_progress.setVisibility(View.VISIBLE);
        isLoading = loading;
        if (isLoading) {
            if (mChildView instanceof ListView) {
                if (((ListView) mChildView).getFooterViewsCount() > 0 && null != mListViewFooter) {
                    ((ListView) mChildView).removeFooterView(mListViewFooter);
                }

                if (((ListView) mChildView).getFooterViewsCount() <= 0 && null != mListViewFooter) {
                    ((ListView) mChildView).addFooterView(mListViewFooter);

                }
                mListViewFooter.setVisibility(VISIBLE);


            } else if (mChildView instanceof RecyclerView) {
//                if (((RecyclerAdapterWithHF) adapter).getFootSize() > 0 && null != mListViewFooter) {
//                    ((RecyclerAdapterWithHF) adapter).removeFooter(mListViewFooter);
//                }
//
//                if (((RecyclerAdapterWithHF) adapter).getFootSize() <= 0 && null != mListViewFooter) {
//                    ((RecyclerAdapterWithHF) adapter).addFooter(mListViewFooter);
//                }
                mListViewFooter.setVisibility(View.VISIBLE);
            } else if (mChildView instanceof GridView) {
                mListViewFooter.setVisibility(View.VISIBLE);
            }

        } else {
            if (mChildView instanceof ListView) {
                ((ListView) mChildView).removeFooterView(mListViewFooter);
            } else if (mChildView instanceof RecyclerView) {
                mListViewFooter.setVisibility(View.INVISIBLE);
//                ((RecyclerAdapterWithHF) adapter).removeFooter(mListViewFooter);
            } else if (mChildView instanceof GridView) {
                mListViewFooter.setVisibility(View.GONE);
            }

            mYDown = 0;
            mLastY = 0;
        }
    }

    /**
     * @param loadListener
     */
    public void setOnLoadListener(OnLoadListener loadListener) {
        mOnLoadListener = loadListener;
    }


    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // 滚动时到了最底部也可以加载更多
        if (!isNomoreData && isEnableLoadMore && canLoad()) {
            loadData();
        }

    }

    /**
     * 设置刷新
     */
    public static void setRefreshing(SwipeRefreshLayout refreshLayout,
                                     boolean refreshing, boolean notify) {
        Class<? extends SwipeRefreshLayout> refreshLayoutClass = refreshLayout
                .getClass();
        if (refreshLayoutClass != null) {

            try {
                Method setRefreshing = refreshLayoutClass.getDeclaredMethod(
                        "setRefreshing", boolean.class, boolean.class);
                setRefreshing.setAccessible(true);
                setRefreshing.invoke(refreshLayout, refreshing, notify);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 加载更多的监听器
     */
    public interface OnLoadListener {
        public void onLoad();
    }

    /**
     * 加载更多的监听器
     */
    public interface OnScrollListener {
        public void onSrollUp();

        public void onSrollDown();
    }
}
