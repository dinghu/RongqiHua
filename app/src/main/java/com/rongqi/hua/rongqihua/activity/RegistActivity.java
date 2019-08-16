package com.rongqi.hua.rongqihua.activity;

import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.req.AccountReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinghu on 2019/8/16.
 */
public class RegistActivity extends RqBaseActivity {
    @BindView(R.id.account_value)
    EditText accountValue;
    @BindView(R.id.password_value)
    EditText passwordValue;
    @BindView(R.id.next)
    TextView next;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_regist;
    }

    @Override
    protected void initView() {

    }

    private void doRegist() {
        showLoading();
        String account = accountValue.getText().toString();
        String password = passwordValue.getText().toString();
        RetrofitHelper.sendRequest(apiService.adminInsert(new AccountReq(account, password)), new ResponseListener<BaseResp>() {
            @Override
            public void onSuccess(BaseResp baseResp) {
                hideLoading();
                ToastUtils.showLong(baseResp.message);
                ActivityUtils.returnToActivity(RegistActivity.this, LoginActivity.class);
            }

            @Override
            public void onFail(String code, String message) {
                hideLoading();
                ToastUtils.showLong(message);
            }
        });
    }

    @OnClick(R.id.next)
    public void onViewClicked() {
        doRegist();
    }
}
