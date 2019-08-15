package com.fkh.support.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fkh.support.ui.R;

/**
 * Created by dinghu on 2016/8/30.
 * <p>
 * 自己定义的actionbar  。。。
 */

public class TitleView extends RelativeLayout {

    private final TextView tvTitle;
    private final ImageView ivBack;
    private final ImageView ivClose;
    private final TextView tvRight;
    private final ImageView ivRight;
    private final ImageView ivArrow;
    private final ProgressBar progressBar;


    private OnClickRightListener mListener;


    public TitleView(final Context context, AttributeSet attrs) {
        super(context, attrs);
        View.inflate(context, R.layout.view_title, this);


        tvTitle = findViewById(R.id.title);
        ivBack = findViewById(R.id.ivBack);
        ivClose = findViewById(R.id.ivClose);
        tvRight = findViewById(R.id.tvRightText);
        ivRight = findViewById(R.id.ivRight);
        ivArrow = findViewById(R.id.ivArrow);
        progressBar = findViewById(R.id.progressBar);

        final TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.TitleView);
        boolean isLeftDisplay = a.getBoolean(R.styleable.TitleView_isLeftDisplay, true);
        boolean isCloseDisplay = a.getBoolean(R.styleable.TitleView_isCloseDisplay, false);
        String titleText = a.getString(R.styleable.TitleView_titleText);
        String rightText = a.getString(R.styleable.TitleView_rightText);
        Drawable image = a.getDrawable(R.styleable.TitleView_rightImg);
        int imageLeft = a.getResourceId(R.styleable.TitleView_leftImg, R.drawable.nav_btn_back);
        int color = a.getColor(R.styleable.TitleView_titleColor, Color.parseColor("#000000"));
        a.recycle();
        setTitleText(titleText);

        ivBack.setVisibility(isLeftDisplay ? VISIBLE : View.GONE);
        ivClose.setVisibility(isCloseDisplay ? VISIBLE : View.GONE);
        ivBack.setImageResource(imageLeft);
        tvTitle.setTextColor(color);
        if (image != null) {
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageDrawable(image);
            ivRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onClick(view);
                    }
                }
            });

        }
        setRightText(rightText);

        ivClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
            }
        });

        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).onBackPressed();
            }
        });
    }

    public void setProgressBar(int visible) {
        progressBar.setVisibility(visible);
    }

    public void setTitleText(String titleText) {
        if (!TextUtils.isEmpty(titleText)) {
            tvTitle.setText(titleText);
        }
    }

    public String getTitleText() {
        if (tvTitle != null) {
            return tvTitle.getText().toString().trim();
        }
        return "";
    }

    public void setTitleWidth(int titleText) {
        if (tvTitle != null) {
            ViewGroup.LayoutParams lp = tvTitle.getLayoutParams();
            lp.width = titleText;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            tvTitle.setLayoutParams(lp);
        }
    }

    public void setRightText(String titleText) {
        if (!TextUtils.isEmpty(titleText)) {
            tvRight.setVisibility(View.VISIBLE);
            ivRight.setVisibility(GONE);
            tvRight.setText(titleText);
            tvRight.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        mListener.onClick(v);
                    }
                }
            });
        }
    }

    public void setRightImg(int res) {
        if (res == 0) {
            ivRight.setVisibility(GONE);
        } else {
            ivRight.setVisibility(VISIBLE);
        }
        if (ivRight.getVisibility() == View.GONE) {
            ivRight.setVisibility(View.VISIBLE);
        }
        tvRight.setVisibility(GONE);
        ivRight.setImageResource(res);
    }

    public void showRightImg() {
        if (ivRight.getVisibility() == View.GONE) {
            ivRight.setVisibility(View.VISIBLE);
        }
    }

    public void hideRight() {
        if (tvRight.getVisibility() == View.VISIBLE) {
            tvRight.setVisibility(View.GONE);
        }
        if (ivRight.getVisibility() == View.VISIBLE) {
            ivRight.setVisibility(View.GONE);
        }
    }

    public void hideBack() {
        ivBack.setVisibility(View.GONE);
    }


    public void setOnClickRightListener(OnClickRightListener listener) {
        this.mListener = listener;
        ivRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.onClick(view);
                }
            }
        });
    }

    public void setTitleClickListener(OnClickListener listener) {
        if (ivArrow != null) {
            if (listener != null) {
                ivArrow.setVisibility(VISIBLE);
                ivArrow.setOnClickListener(listener);
                tvTitle.setOnClickListener(listener);
            } else {
                ivArrow.setVisibility(GONE);
            }
        }
    }

    public interface OnClickRightListener {
        void onClick(View v);
    }
}
