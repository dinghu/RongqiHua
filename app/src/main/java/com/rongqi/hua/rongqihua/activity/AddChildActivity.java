package com.rongqi.hua.rongqihua.activity;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;
import com.rongqi.hua.rongqihua.entity.resp.Child;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

public class AddChildActivity extends RqBaseActivity {
    @BindView(R.id.tvName)
    EditText tvName;
    @BindView(R.id.boy)
    RadioButton boy;
    @BindView(R.id.girl)
    RadioButton girl;
    @BindView(R.id.sexGroup)
    RadioGroup sexGroup;
    @BindView(R.id.shenfenCode)
    EditText shenfenCode;
    @BindView(R.id.tel)
    EditText tel;
    @BindView(R.id.shenfenType)
    EditText shenfenType;
    @BindView(R.id.zhuanye)
    EditText zhuanye;
    @BindView(R.id.school)
    EditText school;
    @BindView(R.id.reserveBtn)
    Button reserveBtn;

    @Override
    protected int getLayout() {
        return R.layout.activity_child_insert;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.reserveBtn)
    public void onViewClicked() {
        String name = tvName.getText().toString();
        String nid = shenfenCode.getText().toString();
        Child child = new Child();
        child.setName( name);
        child.setNid(nid);
        child.setSex(boy.isChecked());
        child.setPhone(tel.getText().toString());
        child.setMajor(zhuanye.getText().toString());
        child.setGraduate(school.getText().toString());
        child.setSId(0);

        RetrofitHelper.sendRequest(apiService.studentInsert(1, UserUtils.getToken(), child), new ResponseListener<BaseResp>() {
            @Override
            public void onSuccess(BaseResp responseBody) {
                ToastUtils.showLong(responseBody.message);
                finish();
            }

            @Override
            public void onFail(String code, String message) {
                ToastUtils.showLong(message);
            }
        });
    }
}
