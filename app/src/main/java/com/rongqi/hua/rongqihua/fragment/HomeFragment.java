package com.rongqi.hua.rongqihua.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.fkh.support.ui.fragment.BaseFragment;
import com.fkh.support.ui.widget.ScrollListView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.activity.RegisInfotActivity;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by dinghu on 2019/8/15.
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.head)
    RoundedImageView head;
    @BindView(R.id.company_setting)
    LinearLayout companySetting;
    @BindView(R.id.company_info)
    LinearLayout companyInfo;
    @BindView(R.id.company_members)
    LinearLayout companyMembers;
    @BindView(R.id.company_bindweichat)
    LinearLayout companyBindweichat;
    @BindView(R.id.newsList)
    ScrollListView newsList;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {

    }

    @OnClick({R.id.company_setting, R.id.company_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_setting:
                ActivityUtils.startActivity(getContext(), RegisInfotActivity.class);
                break;
            case R.id.company_info:
                break;
        }
    }
}
