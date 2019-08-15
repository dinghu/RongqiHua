package com.fkh.support.ui.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class PullableScrollView extends NestedScrollView implements Pullable {
//    ISmartScrollChangedListener mSmartScrollChangedListener;

    public PullableScrollView(Context context) {
        super(context);
    }

    public PullableScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableScrollView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }



    //    /**
//     * 定义监听接口
//     */
//    public interface ISmartScrollChangedListener {
//        void canPullDown();
//
//        void canPullUp();
//    }
//
//    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
//        mSmartScrollChangedListener = smartScrollChangedListener;
//    }
//
//
//    @Override
//    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
//        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
//        if (canPullUp()) {
//            mSmartScrollChangedListener.canPullUp();
//        }
//    }


    @Override
    public boolean canPullDown() {
        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (getScrollY() >= (getChildAt(0).getHeight() - getMeasuredHeight()))
            return true;
        else
            return false;
    }

}
