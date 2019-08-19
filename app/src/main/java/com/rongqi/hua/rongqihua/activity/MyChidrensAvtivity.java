package com.rongqi.hua.rongqihua.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.activity.RefreshLoadListViewActivity;
import com.fkh.support.ui.widget.KeyValueView;
import com.fkh.support.ui.widget.TitleView;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.Child;
import com.rongqi.hua.rongqihua.service.ApiService;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by dinghu on 2019/8/16.
 */
public class MyChidrensAvtivity extends RefreshLoadListViewActivity<Child, MyChidrensAvtivity.ViewHolder> {

    @BindView(R.id.number)
    TextView number;
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.noDataView)
    TextView noDataView;
    @BindView(R.id.titleView)
    TitleView titleView;
    private ArrayList<Child> childrens = new ArrayList<>();
    ApiService apiService = RetrofitHelper.createService(ApiService.class);

    @Override
    public int getItemLayout() {
        return R.layout.item_child;
    }

    @Override
    public ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void initializeViews(int position, Child child, ViewHolder viewHolder) {
        viewHolder.kvName.setValue(child.getName());
        viewHolder.sex.setValue(child.isSex() ? "男" : "女");
        viewHolder.shenfenCode.setValue(child.getNid());
        viewHolder.phone.setValue(child.getPhone());
        viewHolder.shenfenType.setValue(child.getType());
        viewHolder.zuanye.setValue(child.getMajor());
        viewHolder.yuanxiao.setValue(child.getGraduate());
    }

    @Override
    public void getData(int page, boolean isRefreh) {
        RetrofitHelper.sendRequest(apiService.myYejipersons(UserUtils.getToken()), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    List<Child> childList = GsonUtils.fromJson(body.string(), new TypeToken<List<Child>>() {
                    }.getType());
                    number.setText("共" + (childList != null ? childList.size() : 0) + "人");
                    dealDataRecive(childList, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String code, String message) {
                dealError(message);
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_my_childrens;
    }

    @Override
    protected void initView() {
        titleView.setOnClickRightListener(new TitleView.OnClickRightListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivityForResult(MyChidrensAvtivity.this, 1000, AddChildActivity.class);
            }
        });
        bindView(smartRefreshLayout, list, childrens);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            refreshData();
        }
    }

    class ViewHolder {
        @BindView(R.id.kvName)
        KeyValueView kvName;
        @BindView(R.id.sex)
        KeyValueView sex;
        @BindView(R.id.shenfenCode)
        KeyValueView shenfenCode;
        @BindView(R.id.phone)
        KeyValueView phone;
        @BindView(R.id.shenfenType)
        KeyValueView shenfenType;
        @BindView(R.id.zuanye)
        KeyValueView zuanye;
        @BindView(R.id.yuanxiao)
        KeyValueView yuanxiao;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
