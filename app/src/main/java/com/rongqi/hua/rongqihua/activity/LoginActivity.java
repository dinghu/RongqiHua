package com.rongqi.hua.rongqihua.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.req.AccountReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinghu on 2019/8/16.
 */
public class LoginActivity extends RqBaseActivity {
    @BindView(R.id.account_value)
    EditText accountValue;
    @BindView(R.id.password_value)
    EditText passwordValue;
    @BindView(R.id.login)
    TextView login;
    @BindView(R.id.regist)
    TextView regist;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_login;
    }

    @Override
    protected void initView() {

    }

    private void doLogin() {
        showLoading();
        String account = accountValue.getText().toString();
        String password = passwordValue.getText().toString();
        RetrofitHelper.sendRequest(apiService.login(new AccountReq(account, password)), new ResponseListener<BaseResp>() {
            @Override
            public void onSuccess(BaseResp baseResp) {
                hideLoading();
                ToastUtils.showLong(baseResp.message);
            }

            @Override
            public void onFail(String code, String message) {
                hideLoading();
                ToastUtils.showLong(message);
            }
        });
    }

    @OnClick({R.id.login, R.id.regist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                doLogin();
                break;
            case R.id.regist:
                startActivity(new Intent(this, RegistActivity.class));
                break;
        }
    }
}
