package com.fkh.support.ui.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.fkh.support.ui.R;


public class BaseDialog extends Dialog {

    protected Point mScreenPoint = new Point();
    protected Context context;

    public BaseDialog(@NonNull Activity context) {
        super(context, R.style.ClassicDialog);
        this.context = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
    }

    protected void initDialogWindow(float scale) {
        initDialogWindow(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, scale);
    }


    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    protected void initDialogWindow(int gravity, float scale) {
        Window dialogWindow = getWindow();

        /*
         * lp.x与lp.y表示相对于原始位置的偏移.
         * 当参数值包含Gravity.LEFT时,对话框出现在左边,所以lp.x就表示相对左边的偏移,负值忽略.
         * 当参数值包含Gravity.RIGHT时,对话框出现在右边,所以lp.x就表示相对右边的偏移,负值忽略.
         * 当参数值包含Gravity.TOP时,对话框出现在上边,所以lp.y就表示相对上边的偏移,负值忽略.
         * 当参数值包含Gravity.BOTTOM时,对话框出现在下边,所以lp.y就表示相对下边的偏移,负值忽略.
         * 当参数值包含Gravity.CENTER_HORIZONTAL时
         * ,对话框水平居中,所以lp.x就表示在水平居中的位置移动lp.x像素,正值向右移动,负值向左移动.
         * 当参数值包含Gravity.CENTER_VERTICAL时
         * ,对话框垂直居中,所以lp.y就表示在垂直居中的位置移动lp.y像素,正值向右移动,负值向左移动.
         * gravity的默认值为Gravity.CENTER,即Gravity.CENTER_HORIZONTAL |
         * Gravity.CENTER_VERTICAL.
         *
         * 本来setGravity的参数值为Gravity.LEFT | Gravity.TOP时对话框应出现在程序的左上角,但在
         * 我手机上测试时发现距左边与上边都有一小段距离,而且垂直坐标把程序标题栏也计算在内了,
         * Gravity.LEFT, Gravity.TOP, Gravity.BOTTOM与Gravity.RIGHT都是如此,据边界有一小段距离
         */
        //WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(gravity);
        //lp.y = - Utils.dip2px(50, mRootActivity.getResources());

        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        mScreenPoint.x = dm.widthPixels;
        mScreenPoint.y = dm.heightPixels;

        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.width = (int) (mScreenPoint.x * scale);    // 宽度设置为屏幕的0.9
        //p.height = (int) (mScreenPoint.y * 0.35);  // 高度设置为屏幕的0.35
        dialogWindow.setAttributes(p);
    }

    public interface OnCommitListener {
        void onCommitListener();
    }
}
