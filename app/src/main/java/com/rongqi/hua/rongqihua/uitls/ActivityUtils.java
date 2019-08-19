package com.rongqi.hua.rongqihua.uitls;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtils {

    public static void startActivity(Context context,  Intent intent) {
        context.startActivity(intent);
    }

    public static void startActivity(Context context, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivity(intent);
    }

    public static void startActivityForResult(Activity context,int requestCode, Class<?> activityClass) {
        Intent intent = new Intent(context, activityClass);
        context.startActivityForResult(intent,requestCode);
    }

    public static void returnToActivity(Context context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
        if (context instanceof Activity) {
            ((Activity) context).finish();
        }
    }
}
