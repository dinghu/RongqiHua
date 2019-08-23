package com.rongqi.hua.rongqihua.activity;

import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.req.AccountReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;
import com.rongqi.hua.rongqihua.entity.resp.BooleanResp;
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
    @BindView(R.id.password_value_two)
    EditText password_value_two;
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
        String passwordValueTwo = password_value_two.getText().toString();
        if (TextUtils.isEmpty(account)) {
            accountValue.setError(accountValue.getHint());
            accountValue.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            passwordValue.setError(passwordValue.getHint());
            passwordValue.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(passwordValueTwo)) {
            password_value_two.setError(password_value_two.getHint());
            password_value_two.requestFocus();
            return;
        }
        if (!passwordValueTwo.equals(password)) {
            ToastUtils.showLong("两次输入的密码不相同");
            return;
        }
        RetrofitHelper.sendRequest(apiService.adminInsert(new AccountReq(account, password)), new ResponseListener<BooleanResp>() {
            @Override
            public void onSuccess(BooleanResp baseResp) {
                hideLoading();
                ToastUtils.showLong(baseResp.message);
                if (baseResp.isSuccess()){
                    ActivityUtils.returnToActivity(RegistActivity.this, LoginActivity.class);
                }

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
