package com.fkh.support.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.fkh.support.ui.dialog.LoadDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by dinghu on 2019/4/2.
 */
public abstract class BaseIndicatorActivity extends IndicatorActivity<Fragment> {
    protected abstract int getLayout();

    protected abstract void initView();

    private Unbinder unBinder;
    private LoadDialog mLoadDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        unBinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unBinder.unbind();
    }


    public void showLoading() {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(this);
        }

        if (mLoadDialog.isShow()) {
            return;
        }
        mLoadDialog.show();
    }

    public void hideLoading() {
        if (mLoadDialog != null) {
            mLoadDialog.cancel();
        }
    }
}
