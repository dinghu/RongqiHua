package com.fkh.support.ui.dialog;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.fkh.support.ui.R;


public class LoadDialog {

    private Context mContext;
    private AlertDialog mAlertDialog;

    public LoadDialog(Context context) {
        this.mContext = context;
    }

    public void show() {
        if (mAlertDialog == null) {
            mAlertDialog = new AlertDialog.Builder(mContext, R.style.transparent_dialog).create();
            View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_load, null);
            mAlertDialog.setView(v);
            setDailogPosition(mContext, mAlertDialog, v, Gravity.CENTER, 0, 0);
            mAlertDialog.setCancelable(true);
        }

        mAlertDialog.show();
    }

    // 根据手机的分辨率将dp的单位转成px(像素)
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置对话框
     *
     * @param c
     * @param ad
     * @param v
     * @param position
     * @param y
     * @param animations
     */
    public void setDailogPosition(Context c, AlertDialog ad, View v, int position, int y, int animations) {

        Window window = ad.getWindow();
        WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = size.x; //设置满屏宽度
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT; // 默认高度
        window.setGravity(position);
        layoutParams.y = dip2px(c, y);
        if (animations != 0) {
            window.setWindowAnimations(animations);
        }// 设置动画
        window.setAttributes(layoutParams);
        window.setContentView(v);

    }

    public void cancel() {
        if (mAlertDialog != null)
            mAlertDialog.cancel();
    }

    public boolean isShow() {
        if (mAlertDialog != null) {
            return mAlertDialog.isShowing();
        }
        return false;
    }


}
