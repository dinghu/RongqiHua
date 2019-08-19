package com.rongqi.hua.rongqihua.activity;

import android.view.View;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.rongqi.hua.rongqihua.entity.req.RegistInfoReq;
import com.rongqi.hua.rongqihua.entity.resp.DataResp;
import com.rongqi.hua.rongqihua.entity.resp.TUserInfo;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import okhttp3.ResponseBody;

/**
 * Created by dinghu on 2019/8/19.
 */
public class ModifyRegistInfoActivity extends RegisInfotActivity {
    TUserInfo userInfo = UserUtils.getUserInfo();

    @Override
    protected void initView() {
        super.initView();
        userInfo = UserUtils.getUserInfo();
        tvName.setText(userInfo.name);
        boy.setChecked(userInfo.sex);
        girl.setChecked(!userInfo.sex);
        tvBirthDay.setText(TimeUtils.millis2String(userInfo.birth, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        workStart.setText(TimeUtils.millis2String(userInfo.hiredate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        shenfenCode.setText(userInfo.nid);
        telphone.setText(userInfo.phone);
        mail.setText(userInfo.email);
        address.setText(userInfo.place);
        joinCode.setText(userInfo.invCode);
        workStartLay.setVisibility(View.GONE);
        shenfenCodeLay.setVisibility(View.GONE);
        joinCodeLay.setVisibility(View.GONE);
        reserveBtn.setText("确认修改");
        titleView.setTitleText("修改注册信息");
    }

    @Override
    protected void onSubmit() {
        String invCode = userInfo.invCode;
        final RegistInfoReq registInfoReq = new RegistInfoReq();
        registInfoReq.name = tvName.getText().toString();
        registInfoReq.birth = tvBirthDay.getText().toString();
        registInfoReq.hiredate = TimeUtils.millis2String(userInfo.hiredate, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        registInfoReq.sex = boy.isChecked();
        registInfoReq.email = mail.getText().toString();
        registInfoReq.phone = telphone.getText().toString();
        registInfoReq.invCode = invCode;
        registInfoReq.lastTime = TimeUtils.millis2String(System.currentTimeMillis(), "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        registInfoReq.nick = tvName.getText().toString();
        registInfoReq.place = address.getText().toString();
        registInfoReq.tUuid = UserUtils.getToken();
        registInfoReq.nid = userInfo.nid;
        showLoading();
        RetrofitHelper.sendRequest(apiService.teacherUpdate(registInfoReq), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                try {
                    if ("true".equals(baseResp.string())) {
                        ToastUtils.showLong("修改成功");
                        userInfo.name = registInfoReq.name;
                        userInfo.birth = TimeUtils.string2Millis(registInfoReq.birth, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        userInfo.sex = registInfoReq.sex;
                        userInfo.email = registInfoReq.email;
                        userInfo.phone = registInfoReq.phone;
                        userInfo.lastTime = TimeUtils.string2Millis(registInfoReq.lastTime, "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                        userInfo.place = registInfoReq.place;
                        UserUtils.saveUserInfo(userInfo);
                        finish();
                    } else {
                        ToastUtils.showLong("修改失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showLong(e.getMessage());
                }
                hideLoading();
            }

            @Override
            public void onFail(String code, String message) {
                hideLoading();
                ToastUtils.showLong(message);
            }
        });
    }
}
