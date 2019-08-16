package com.rongqi.hua.rongqihua.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.codbking.widget.DatePickDialog;
import com.codbking.widget.OnChangeLisener;
import com.codbking.widget.OnSureLisener;
import com.codbking.widget.bean.DateType;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.req.RegistInfoReq;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;

import java.util.Date;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by dinghu on 2019/8/15.
 */
public class RegisInfotActivity extends RqBaseActivity {
    @BindView(R.id.tvName)
    EditText tvName;
    @BindView(R.id.boy)
    RadioButton boy;
    @BindView(R.id.girl)
    RadioButton girl;
    @BindView(R.id.sexGroup)
    RadioGroup sexGroup;
    @BindView(R.id.tvBirthDay)
    TextView tvBirthDay;
    @BindView(R.id.workStart)
    TextView workStart;
    @BindView(R.id.shenfenCode)
    EditText shenfenCode;
    @BindView(R.id.telphone)
    EditText telphone;
    @BindView(R.id.mail)
    EditText mail;
    @BindView(R.id.address)
    EditText address;
    @BindView(R.id.joinCode)
    EditText joinCode;


    @Override
    protected int getLayout() {
        return R.layout.activity_regist;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.tvBirthDay, R.id.workStart, R.id.reserveBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvBirthDay:
                DatePickDialog dialog = new DatePickDialog(this);
                //设置上下年分限制
                dialog.setYearLimt(5);
                //设置标题
                dialog.setTitle("选择时间");
                //设置类型
                dialog.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialog.setMessageFormat("yyyy-MM-dd HH:mm");
                //设置选择回调
                dialog.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        tvBirthDay.setText(TimeUtils.date2String(date,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    }
                });
                //设置点击确定按钮回调
                dialog.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        tvBirthDay.setText(TimeUtils.date2String(date,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    }
                });
                dialog.show();
                break;
            case R.id.workStart:
                DatePickDialog dialogStart = new DatePickDialog(this);
                //设置上下年分限制
                dialogStart.setYearLimt(5);
                //设置标题
                dialogStart.setTitle("选择时间");
                //设置类型
                dialogStart.setType(DateType.TYPE_YMD);
                //设置消息体的显示格式，日期格式
                dialogStart.setMessageFormat("yyyy-MM-dd HH:mm");
                //设置选择回调
                dialogStart.setOnChangeLisener(new OnChangeLisener() {
                    @Override
                    public void onChanged(Date date) {
                        workStart.setText(TimeUtils.date2String(date,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    }
                });
                //设置点击确定按钮回调
                dialogStart.setOnSureLisener(new OnSureLisener() {
                    @Override
                    public void onSure(Date date) {
                        workStart.setText(TimeUtils.date2String(date,"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
                    }
                });
                dialogStart.show();
                break;
            case R.id.reserveBtn:
                String invCode = joinCode.getText().toString();
                RegistInfoReq registInfoReq = new RegistInfoReq();
                registInfoReq.birth = tvBirthDay.getText().toString();
                registInfoReq.hiredate = workStart.getText().toString();
                registInfoReq.sex = boy.isChecked();
                registInfoReq.email = mail.getText().toString();
                registInfoReq.phone = telphone.getText().toString();
                registInfoReq.invCode = invCode;
                registInfoReq.lastTime = TimeUtils.millis2String(System.currentTimeMillis(),"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
                registInfoReq.nick = tvName.getText().toString();
                registInfoReq.place = address.getText().toString();
                registInfoReq.tUuid = "";
                showLoading();
                RetrofitHelper.sendRequest(apiService.teacherInsert(invCode, registInfoReq), new ResponseListener<BaseResp>() {
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
                break;
        }
    }
}
