package com.rongqi.hua.rongqihua.activity;

import android.widget.EditText;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;
import com.rongqi.hua.rongqihua.entity.resp.Developer;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

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

    private void doAdd() {//Developer developer
        String shenfenNumber = shenfenCode.getText().toString();
        RetrofitHelper.sendRequest(
                apiService.relaInsert(shenfenNumber, UserUtils.getToken()), new ResponseListener<BaseResp>() {
                    @Override
                    public void onSuccess(BaseResp baseResp) {
                        hideLoading();
                        setResult(RESULT_OK);
                        ToastUtils.showLong(baseResp.message);
                        finish();
                    }

                    @Override
                    public void onFail(String code, String message) {
                        hideLoading();
                        ToastUtils.showLong(message);
                    }
                });
    }

    @OnClick(R.id.reserveBtn)
    public void onViewClicked() {

        String name = tvName.getText().toString();
        showLoading();
        RetrofitHelper.sendRequest(apiService.searchDeveloperByName(name), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody responseBody) {
                try {
                    String developers = responseBody.string();
                    List<Developer> developerList = GsonUtils.fromJson(developers, new TypeToken<List<Developer>>() {
                    }.getType());
                    if (developerList != null && !developerList.isEmpty()) {
                        doAdd();
                    } else {
                        hideLoading();
                        ToastUtils.showLong("不存在该合伙人");
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
}
