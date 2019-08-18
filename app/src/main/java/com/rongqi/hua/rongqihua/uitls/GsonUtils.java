package com.rongqi.hua.rongqihua.uitls;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class GsonUtils {
    public static <T> T fromJson(final String json, final Type type) {
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }
}
