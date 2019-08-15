package com.fkh.support.engine;

import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.fkh.support.engine.retrofit.RetrofitHelper;

public class SupportEngine {
    private static SupportEngine instance;

    public static synchronized SupportEngine getInstance() {
        if (instance == null) {
            instance = new SupportEngine();
        }
        return instance;
    }

    public static void init(Builder builder) {
        RetrofitHelper.getInstance().init(builder.context, builder.baseUrl);
        Utils.init(builder.context);
    }

    public static Builder newSupportEngine() {
        return new Builder();
    }

    public static final class Builder {
        private Context context;
        private String baseUrl;

        public Builder() {
        }

        public Builder context(Context context) {
            this.context = context;
            return this;
        }

        public Builder baseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }
    }
}