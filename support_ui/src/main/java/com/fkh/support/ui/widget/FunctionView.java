package com.fkh.support.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fkh.support.ui.R;


public class FunctionView extends RelativeLayout {

    TextView tvKey;
    TextView tvValue;
    ImageView ivRightImage;
    ImageView ivPreImage;
    TextView tvBadge;

    public FunctionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_function, this);
        tvKey = (TextView) view.findViewById(R.id.tvKey);
        tvValue = (TextView) view.findViewById(R.id.tvValue);
        ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivPreImage = (ImageView) view.findViewById(R.id.ivPreImage);
        tvBadge = (TextView) view.findViewById(R.id.tvBadge);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FunctionView);
        int valuecolor = typedArray.getColor(R.styleable.FunctionView_ftkeyColor, Color.parseColor("#000000"));
        int keycolor = typedArray.getColor(R.styleable.FunctionView_ftvalueColor, Color.parseColor("#000000"));
        int imageRight = typedArray.getResourceId(R.styleable.FunctionView_ftrightImage, -1);
        int imagePre = typedArray.getResourceId(R.styleable.FunctionView_ftPreImage, -1);
        int valueTextSize = typedArray.getDimensionPixelSize(R.styleable.FunctionView_ftvalueTextSize, -1);
        int keyTextSize = typedArray.getDimensionPixelSize(R.styleable.FunctionView_ftkeyTextSize, -1);
        String key = typedArray.getString(R.styleable.FunctionView_ftkey);
        String value = typedArray.getString(R.styleable.FunctionView_ftvalue);
        int badge = typedArray.getInt(R.styleable.FunctionView_ftbadge, -1);

        tvKey.setText(key);
        tvValue.setText(value);
        tvKey.setTextColor(keycolor);
        tvValue.setTextColor(valuecolor);

        if (-1 != imageRight) {
            ivRightImage.setVisibility(VISIBLE);
            ivRightImage.setImageResource(imageRight);
        }

        if (-1 != valueTextSize) {
            tvValue.getPaint().setTextSize(valueTextSize);
        }
        if (-1 != badge) {
            tvBadge.setVisibility(VISIBLE);
            tvBadge.setText(String.valueOf(badge));
        }
        if (-1 != keyTextSize) {
            tvKey.getPaint().setTextSize(keyTextSize);
        }
        if (imagePre != -1) {
            ivPreImage.setVisibility(VISIBLE);
            ivPreImage.setImageResource(imagePre);
        }
    }
}
