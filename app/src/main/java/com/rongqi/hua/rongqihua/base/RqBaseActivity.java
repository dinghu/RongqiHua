package com.rongqi.hua.rongqihua.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.activity.BaseActivity;
import com.rongqi.hua.rongqihua.service.ApiService;

/**
 * Created by dinghu on 2019/8/16.
 */
public abstract class RqBaseActivity extends BaseActivity {
    protected ApiService apiService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiService = RetrofitHelper.createService(ApiService.class);
    }
}
