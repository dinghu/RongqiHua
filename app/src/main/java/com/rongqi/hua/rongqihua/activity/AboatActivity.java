package com.rongqi.hua.rongqihua.activity;

import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.fkh.support.ui.activity.BaseActivity;
import com.rongqi.hua.rongqihua.R;

import butterknife.BindView;

public class AboatActivity extends BaseActivity {

    @BindView(R.id.versionText)
    TextView versionText;

    @Override
    protected int getLayout() {
        return R.layout.activity_aboat;
    }

    @Override
    protected void initView() {
        versionText.setText(AppUtils.getAppVersionName());
    }


}
