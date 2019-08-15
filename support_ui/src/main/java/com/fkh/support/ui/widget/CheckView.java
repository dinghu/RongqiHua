package com.fkh.support.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;

import com.fkh.support.ui.R;

/**
 * Created by dinghu on 2019/7/22.
 */
public class CheckView extends AppCompatImageView {
    private boolean isCheck;
    private OnCheckChangeListener onCheckChangeListener;

    private int img, imgCheck;

    public CheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CheckView);
        img = array.getResourceId(R.styleable.CheckView_imgNormal, R.drawable.switch_off);
        imgCheck = array.getResourceId(R.styleable.CheckView_imgCheck, R.drawable.switch_on);
        isCheck = array.getBoolean(R.styleable.CheckView_checked, false);
        array.recycle();//回收

        setImageResource(img);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                setCheck(!isCheck);
                if (onCheckChangeListener != null) {
                    onCheckChangeListener.onChange(isCheck);
                }
            }
        });
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
        int imgId = check ? imgCheck : img;
        setImageResource(imgId);
    }


    public void setOnCheckChangeListener(OnCheckChangeListener onCheckChangeListener) {
        this.onCheckChangeListener = onCheckChangeListener;
    }

    public interface OnCheckChangeListener {
        void onChange(boolean bool);
    }
}
