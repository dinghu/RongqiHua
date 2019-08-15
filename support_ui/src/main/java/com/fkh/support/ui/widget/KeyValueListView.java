package com.fkh.support.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;


import com.fkh.support.ui.R;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class KeyValueListView extends LinearLayout {
    private Map<String, KeyValueView> keyValueviews = new HashMap<>();
    int keycolor;
    int valuecolor;
    int imageRight;
    int valueGravity;
    int itemPadding;
    int valueTextSize;
    int keyTextSize;
    int divider;
    int dividerHeight;

    public interface KeyValueListViewListener {
        void onKeyValueView(String key, String value, KeyValueView keyValueView);
    }


    private int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public KeyValueListView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.KeyValueListView);
        keycolor = typedArray.getColor(R.styleable.KeyValueListView_kvlkeyColor, Color.parseColor("#000000"));
        valuecolor = typedArray.getColor(R.styleable.KeyValueListView_kvlvalueColor, Color.parseColor("#000000"));
        imageRight = typedArray.getResourceId(R.styleable.KeyValueListView_kvlrightImage, -1);
        valueGravity = typedArray.getInt(R.styleable.KeyValueListView_kvlvalueGravity, 0);
        itemPadding = typedArray.getDimensionPixelSize(R.styleable.KeyValueListView_kvlitemPadding, -1);
        valueTextSize = typedArray.getDimensionPixelSize(R.styleable.KeyValueListView_kvlvalueTextSize, -1);
        keyTextSize = typedArray.getDimensionPixelSize(R.styleable.KeyValueListView_kvlkeyTextSize, -1);
        divider = typedArray.getColor(R.styleable.KeyValueListView_kvldivider, Color.parseColor("#c8cdcf"));
        dividerHeight = typedArray.getDimensionPixelSize(R.styleable.KeyValueListView_kvldividerHeight, dp2px(0.5f));
        typedArray.recycle();
    }

    public KeyValueView getKeyValueViewByKey(String key) {
        return keyValueviews.get(key);
    }

    public void setKeyValues(LinkedHashMap<String, String> keyValues, KeyValueListViewListener keyValueListViewListener) {
        for (Map.Entry<String, String> entry : keyValues.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            KeyValueView keyValueView = new KeyValueView(getContext());
            keyValueView.setValue(entry.getValue());
            keyValueView.setKey(entry.getKey());

            keyValueView.setKeyTextColor(keycolor);
            keyValueView.setValueTextColor(valuecolor);


            if (-1 != imageRight) {
                keyValueView.setRightImageResource(imageRight);
            }
            if (-1 != itemPadding) {
                keyValueView.setItemPadding(itemPadding);
            }
            if (-1 != valueTextSize) {
                keyValueView.setValueTextSize(valueTextSize);
            }
            if (-1 != keyTextSize) {
                keyValueView.setKeyTextSize(keyTextSize);
            }
            if (1 == valueGravity) {
                keyValueView.setValueGravity(KeyValueView.ValueGravity.RIGHT);
            }

            addView(keyValueView);
            keyValueviews.put(entry.getKey(), keyValueView);
            if (keyValueListViewListener != null) {
                keyValueListViewListener.onKeyValueView(entry.getKey(), entry.getValue(), keyValueView);
            }

            //分割线
            if (0 != dividerHeight) {
                View view = new View(getContext());
                view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, dividerHeight));
                view.setBackgroundColor(divider);
                addView(view);
            }

        }
    }
}
