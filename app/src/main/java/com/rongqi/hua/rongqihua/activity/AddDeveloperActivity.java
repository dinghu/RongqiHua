package com.rongqi.hua.rongqihua.activity;

import android.widget.EditText;

import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinghu on 2019/8/16.
 */
public class AddDeveloperActivity extends RqBaseActivity {
    @BindView(R.id.tvName)
    EditText tvName;
    @BindView(R.id.shenfenCode)
    EditText shenfenCode;

    @Override
    protected int getLayout() {
        return R.layout.activity_add_developer;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.reserveBtn)
    public void onViewClicked() {
    }
}
