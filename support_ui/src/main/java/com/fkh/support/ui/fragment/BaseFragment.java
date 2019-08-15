package com.fkh.support.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fkh.support.ui.dialog.LoadDialog;
import com.fkh.support.ui.widget.TitleView;

import java.security.Permission;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import kr.co.namee.permissiongen.PermissionGen;

public abstract class BaseFragment extends Fragment {
    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    private Unbinder unBinder;
    private LoadDialog mLoadDialog;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(this.getLayoutId(), container, false);
        unBinder = ButterKnife.bind(this, view);
        initView(view);
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionGen.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unBinder != null) {
            unBinder.unbind();
        }
    }

    public void showLoading() {
        if (mLoadDialog == null) {
            mLoadDialog = new LoadDialog(getContext());
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
