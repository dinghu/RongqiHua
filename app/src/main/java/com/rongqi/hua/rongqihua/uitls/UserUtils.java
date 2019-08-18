package com.rongqi.hua.rongqihua.uitls;

import android.content.Context;
import android.text.TextUtils;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.SPUtils;
import com.rongqi.hua.rongqihua.activity.LoginActivity;
import com.rongqi.hua.rongqihua.entity.resp.TUserInfo;

/**
 * Created by dinghu on 2019/7/22.
 */
public class UserUtils {
    public static boolean isLogin() {
        String uuid = SPUtils.getInstance().getString("uuid");
        return !TextUtils.isEmpty(uuid);
    }

    public static String getDisplayName() {
        TUserInfo info = getUserInfo();
        if (info != null && !TextUtils.isEmpty(info.nick)) {
            return info.nick;
        }

        if (!TextUtils.isEmpty(getAccount())) {
            return getAccount();
        }
        return null;
    }

    public static void saveToken(String uuid) {
        SPUtils.getInstance().put("uuid", uuid);
    }

    public static String getToken() {
        return SPUtils.getInstance().getString("uuid");
    }

    public static void saveAccount(String name) {
        SPUtils.getInstance().put("account", name);
    }

    public static String getAccount() {
        return SPUtils.getInstance().getString("account");
    }

    public static void saveUserInfo(TUserInfo userInfo) {
        SPUtils.getInstance().put("userInfo", GsonUtils.toJson(userInfo));
    }

    public static TUserInfo getUserInfo() {
        String userInfo = SPUtils.getInstance().getString("userInfo");
        if (!TextUtils.isEmpty(userInfo)) {
            return GsonUtils.fromJson(userInfo, TUserInfo.class);
        }
        return null;
    }

    public static void reqLogin(Context context) {
        ActivityUtils.startActivity(context, LoginActivity.class);
    }
}
