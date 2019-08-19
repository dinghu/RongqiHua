package com.rongqi.hua.rongqihua.activity;

import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.widget.ListSelectDialog;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.resp.BaseResp;
import com.rongqi.hua.rongqihua.entity.resp.Child;
import com.rongqi.hua.rongqihua.entity.resp.PerType;
import com.rongqi.hua.rongqihua.uitls.GsonUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import java.util.ArrayList;
import java.util.List;

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
    TextView shenfenType;
    @BindView(R.id.zhuanye)
    EditText zhuanye;
    @BindView(R.id.school)
    EditText school;
    @BindView(R.id.reserveBtn)
    Button reserveBtn;

    static List<PerType> perTypes = new ArrayList<>();

    Integer typeId = 1;

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
        child.setName(name);
        child.setNid(nid);
        child.setSex(boy.isChecked());
        child.setPhone(tel.getText().toString());
        child.setMajor(zhuanye.getText().toString());
        child.setGraduate(school.getText().toString());
        child.setSId(0);
        showLoading();
        RetrofitHelper.sendRequest(apiService.studentInsert(typeId, UserUtils.getToken(), child), new ResponseListener<BaseResp>() {
            @Override
            public void onSuccess(BaseResp responseBody) {
                hideLoading();
                setResult(RESULT_OK);
                ToastUtils.showLong(responseBody.message);
                finish();
            }

            @Override
            public void onFail(String code, String message) {
                hideLoading();
                ToastUtils.showLong(message);
            }
        });
    }


    @OnClick(R.id.shenfenType)
    public void onShenfenTypeClicked() {
        if (AddChildActivity.this.perTypes.isEmpty()) {
            RetrofitHelper.sendRequest(apiService.perType(), new ResponseListener<ResponseBody>() {
                @Override
                public void onSuccess(ResponseBody body) {
                    try {
                        String tps = body.string();
                        if (!TextUtils.isEmpty(tps)) {
                            final List<PerType> perTypes = GsonUtils.fromJson(tps, new TypeToken<List<PerType>>() {
                            }.getType());
                            AddChildActivity.this.perTypes.clear();
                            AddChildActivity.this.perTypes.addAll(perTypes);

                            ListSelectDialog<PerType> listSelectDialog = new ListSelectDialog<PerType>(AddChildActivity.this
                                    , AddChildActivity.this.perTypes, new ListSelectDialog.OnItemSelectListener<PerType>() {
                                @Override
                                public void onSelect(PerType item) {
                                    typeId = item.getId();
                                    shenfenType.setText(item.getName());
                                }
                            }) {
                                @Override
                                protected void initializeViews(PerType perType, int pos, ItemSelectAdapter.ViewHolder holder) {
                                    holder.tvContent.setText(perType.getName());
                                }
                            };
                            listSelectDialog.show();

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
        } else {
            ListSelectDialog<PerType> listSelectDialog = new ListSelectDialog<PerType>(AddChildActivity.this
                    , AddChildActivity.this.perTypes, new ListSelectDialog.OnItemSelectListener<PerType>() {
                @Override
                public void onSelect(PerType item) {
                    typeId = item.getId();
                    shenfenType.setText(item.getName());
                }
            }) {
                @Override
                protected void initializeViews(PerType perType, int pos, ItemSelectAdapter.ViewHolder holder) {
                    holder.tvContent.setText(perType.getName());
                }
            };
            listSelectDialog.show();
        }


    }
}
