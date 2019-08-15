package com.fkh.support.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fkh.support.ui.R;


//键值对view
public class KeyValueView extends RelativeLayout {

    TextView tvKey;
    TextView tvValue;
    ImageView ivRightImage;
    ImageView ivPreIcon;
    RelativeLayout layValue;
    RelativeLayout tvItem;

    public enum ValueGravity {
        LEFT,
        RIGHT,
    }

    private float getFontWidth(Paint paint, String text) {
        return paint.measureText(text);
    }

    private int dp2px(final float dpValue) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public KeyValueView(Context context) {
        this(context, null);
    }

    public KeyValueView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = View.inflate(context, R.layout.view_keyvalue, this);
        TextView tvKeyShort = (TextView) view.findViewById(R.id.tvKey);
        TextView tvKeyWrap = (TextView) view.findViewById(R.id.tvKeyWrap);
        TextView tvKeyLong = view.findViewById(R.id.tvKeyLong);
        tvValue = (TextView) view.findViewById(R.id.tvValue);
        ivRightImage = (ImageView) view.findViewById(R.id.ivRightImage);
        ivPreIcon = (ImageView) view.findViewById(R.id.ivPreIcon);
        layValue = view.findViewById(R.id.layValue);
        tvItem = view.findViewById(R.id.tvItem);
        tvKey = tvKeyShort;
        if (attrs != null) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.keyValueView);
            String key = typedArray.getString(R.styleable.keyValueView_kvkey);
            String value = typedArray.getString(R.styleable.keyValueView_kvvalue);
            String valuehint = typedArray.getString(R.styleable.keyValueView_kvvaluehint);
            int keycolor = typedArray.getColor(R.styleable.keyValueView_kvkeyColor, Color.parseColor("#000000"));
            int valuecolor = typedArray.getColor(R.styleable.keyValueView_kvvalueColor, Color.parseColor("#000000"));
            int valuehintColor = typedArray.getColor(R.styleable.keyValueView_kvvaluehintColor, Color.parseColor("#989898"));
            int imageRight = typedArray.getResourceId(R.styleable.keyValueView_kvrightImage, -1);
            int textPreImage = typedArray.getResourceId(R.styleable.keyValueView_kvtextPreImage, -1);
            int valueGravity = typedArray.getInt(R.styleable.keyValueView_kvvalueGravity, 0);
            int itemPadding = typedArray.getDimensionPixelSize(R.styleable.keyValueView_kvitemPadding, -1);
            int valueTextSize = typedArray.getDimensionPixelSize(R.styleable.keyValueView_kvvalueTextSize, -1);
            int keyTextSize = typedArray.getDimensionPixelSize(R.styleable.keyValueView_kvkeyTextSize, -1);
            int kvkeyvalueSpace = typedArray.getDimensionPixelSize(R.styleable.keyValueView_kvkeyvalueSpace, -1);
            typedArray.recycle();

            boolean useLongText = valueGravity == 1
                    || TextUtils.isEmpty(value)
                    || getFontWidth(tvKeyShort.getPaint(), key) > dp2px(90) ? true : false;

            if (useLongText) {
                tvKeyShort.setVisibility(View.GONE);
                tvKeyLong.setVisibility(View.VISIBLE);
                tvKey = tvKeyLong;
            }
            if (-1 != kvkeyvalueSpace) {
                tvKeyShort.setVisibility(View.GONE);
                tvKeyLong.setVisibility(View.GONE);
                tvKeyWrap.setVisibility(View.VISIBLE);
                tvKey = tvKeyWrap;
                LayoutParams layoutParams = (LayoutParams) layValue.getLayoutParams();
                layoutParams.leftMargin = kvkeyvalueSpace;
                layValue.setLayoutParams(layoutParams);
            }
            tvKey.setText(key);
            tvValue.setText(value);
            tvKey.setTextColor(keycolor);
            tvValue.setTextColor(valuecolor);
            tvValue.setHintTextColor(valuehintColor);
            if (!TextUtils.isEmpty(valuehint)) {
                tvValue.setHint(valuehint);
            }

            if (-1 != imageRight) {
                ivRightImage.setVisibility(VISIBLE);
                ivRightImage.setImageResource(imageRight);
            }
            if (-1 != itemPadding) {
                tvItem.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
            }
            if (-1 != valueTextSize) {
                tvValue.getPaint().setTextSize(valueTextSize);
            }
            if (-1 != keyTextSize) {
                tvKey.getPaint().setTextSize(keyTextSize);
            }
            if (textPreImage != -1) {
                ivPreIcon.setVisibility(VISIBLE);
                ivPreIcon.setImageResource(textPreImage);
            }
            if (1 == valueGravity) {
                layValue.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
            }
        }

    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public KeyValueView setValue(String value) {
        this.tvValue.setText(value);
        return this;
    }

    public KeyValueView setTextPreIcon(int textPreIcon) {
        ivPreIcon.setVisibility(VISIBLE);
        ivPreIcon.setImageResource(textPreIcon);
        return this;
    }


    public KeyValueView setKey(String key) {
        this.tvKey.setText(key);
        return this;
    }

    public KeyValueView setRightImageResource(int imageId) {
        ivRightImage.setVisibility(VISIBLE);
        ivRightImage.setImageResource(imageId);
        return this;
    }

    public KeyValueView setPreIcon(int imageId) {
        ivPreIcon.setVisibility(VISIBLE);
        ivPreIcon.setImageResource(imageId);
        return this;
    }

    public KeyValueView setValueTextSize(int valueTextSize) {
        return setValueTextSize(TypedValue.COMPLEX_UNIT_SP, valueTextSize);
    }

    public KeyValueView setValueTextSize(int unit, int valueTextSize) {
        tvValue.setTextSize(unit, valueTextSize);
        return this;
    }

    public KeyValueView setKeyTextSize(int keyTextSize) {
        return setKeyTextSize(TypedValue.COMPLEX_UNIT_SP, keyTextSize);
    }

    public KeyValueView setKeyTextSize(int unit, int keyTextSize) {
        tvKey.setTextSize(unit, keyTextSize);
        return this;
    }

    public KeyValueView setValueTextColor(int valuecolor) {
        tvValue.setTextColor(valuecolor);
        return this;
    }

    public KeyValueView setKeyTextColor(int keycolor) {
        tvKey.setTextColor(keycolor);
        return this;
    }

    public KeyValueView setItemPadding(int itemPadding) {
        tvItem.setPadding(itemPadding, itemPadding, itemPadding, itemPadding);
        return this;
    }

    public KeyValueView setValueGravity(ValueGravity valueGravity) {
        if (ValueGravity.RIGHT == valueGravity) {
            layValue.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        } else {
            layValue.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        }
        return this;
    }

}
