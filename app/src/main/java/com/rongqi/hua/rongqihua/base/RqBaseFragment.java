package com.rongqi.hua.rongqihua.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.fragment.BaseFragment;
import com.rongqi.hua.rongqihua.service.ApiService;

/**
 * Created by dinghu on 2019/8/16.
 */
public abstract class RqBaseFragment extends BaseFragment {
    protected ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        apiService = RetrofitHelper.createService(ApiService.class);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
