package com.rongqi.hua.rongqihua.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.activity.RefreshLoadListViewActivity;
import com.fkh.support.ui.widget.KeyValueView;
import com.fkh.support.ui.widget.TitleView;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.Developer;
import com.rongqi.hua.rongqihua.service.ApiService;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by dinghu on 2019/8/16.
 */
public class MyDevelopersActivity extends RefreshLoadListViewActivity<Developer, MyDevelopersActivity.ViewHolder> {
    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.noDataView)
    TextView noDataView;
    ApiService apiService = RetrofitHelper.createService(ApiService.class);
    @BindView(R.id.titleView)
    TitleView titleView;
    private ArrayList<Developer> developers = new ArrayList<>();

    @Override
    public ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_developer;
    }

    @Override
    public void initializeViews(int position, Developer s, ViewHolder viewHolder) {
        viewHolder.kvName.setValue(s.getName());
        viewHolder.sex.setValue(s.isSex() ? "男" : "女");
        viewHolder.workCode.setValue(s.getWorkId());
        viewHolder.shenfenCode.setValue(s.getNid());
        viewHolder.phone.setValue(s.getPhone());
        viewHolder.email.setValue(s.getEmail());
        viewHolder.birthDay.setValue(TimeUtils.millis2String(s.getBirth(), "yyyy/MM/dd"));
        viewHolder.hireday.setValue(TimeUtils.millis2String(s.getHiredate(), "yyyy/MM/dd"));
        viewHolder.address.setValue(s.getPlace());
    }

    @Override
    public void getData(int page, boolean isRefreh) {
        RetrofitHelper.sendRequest(apiService.mydevelopTeachers(UserUtils.getToken()), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    String bodyString = body.string();
                    List<Developer> developers = GsonUtils.fromJson(bodyString, new TypeToken<List<Developer>>() {
                    }.getType());
                    List<Developer> developersTar = new ArrayList<>();
                    for (Developer developer : developers) {
                        if (developer != null) {
                            developersTar.add(developer);
                        }
                    }
                    number.setText("共" + (developersTar != null ? developersTar.size() : 0) + "人");
                    dealDataRecive(developersTar, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String code, String message) {
                ToastUtils.showLong(message);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_developers;
    }

    @Override
    protected void initView() {
        titleView.setOnClickRightListener(new TitleView.OnClickRightListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivityForResult(MyDevelopersActivity.this, 1000,
                        AddDeveloperActivity.class);
            }
        });
        bindView(smartRefreshLayout, list, developers);
        setNoDataView(noDataView);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            refreshData();
        }
    }


    @OnClick(R.id.titleView)
    public void onViewClicked() {
    }

    class ViewHolder {
        @BindView(R.id.kvName)
        KeyValueView kvName;
        @BindView(R.id.sex)
        KeyValueView sex;
        @BindView(R.id.workCode)
        KeyValueView workCode;
        @BindView(R.id.shenfenCode)
        KeyValueView shenfenCode;
        @BindView(R.id.phone)
        KeyValueView phone;
        @BindView(R.id.email)
        KeyValueView email;
        @BindView(R.id.birthDay)
        KeyValueView birthDay;
        @BindView(R.id.hireday)
        KeyValueView hireday;
        @BindView(R.id.address)
        KeyValueView address;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
