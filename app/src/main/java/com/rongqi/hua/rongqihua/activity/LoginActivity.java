package com.rongqi.hua.rongqihua.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.req.AccountReq;
import com.rongqi.hua.rongqihua.entity.req.UserLoginReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;
import com.rongqi.hua.rongqihua.entity.resp.DataResp;
import com.rongqi.hua.rongqihua.entity.resp.LoginResp;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

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

    public void onLoginSuc(){

    }

    public void userAccept(final String uuid) {
        final String account = accountValue.getText().toString();
        UserLoginReq userLoginReq = new UserLoginReq();
        userLoginReq.uId = 0;
        userLoginReq.nick = account;
        userLoginReq.logTime = TimeUtils.millis2String(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        userLoginReq.uUuid = uuid;
        RetrofitHelper.sendRequest(apiService.userlogInsert(userLoginReq), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                hideLoading();
                try {
                    if ("true".equals(baseResp.string())) {
                        ToastUtils.showLong("登录成功");
                        UserUtils.saveAccount(account);
                        UserUtils.saveToken(uuid);
                        finish();
                    } else {
                        ToastUtils.showLong("登录失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showLong(e.getMessage());
                }
            }

            @Override
            public void onFail(String code, String message) {
                ToastUtils.showLong(message);
            }
        });
    }

    private void doLogin() {
        showLoading();
        final String account = accountValue.getText().toString();
        String password = passwordValue.getText().toString();
        RetrofitHelper.sendRequest(apiService.login(new AccountReq(account, password)), new ResponseListener<LoginResp>() {
            @Override
            public void onSuccess(LoginResp baseResp) {
                LoginResp.UuidBean uuidBean = baseResp.data;
                if (!uuidBean.isUserLog()) {
                    //继续授权
                    userAccept(baseResp.data.getUuid());
                }
                if (uuidBean.isUserLog() && uuidBean.gettUserInfo() != null) {
                    hideLoading();
                    //已经注册了信息
                    UserUtils.saveUserInfo(uuidBean.gettUserInfo());
                    UserUtils.saveAccount(account);
                    UserUtils.saveToken(uuidBean.getUuid());
                    finish();
                }
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
