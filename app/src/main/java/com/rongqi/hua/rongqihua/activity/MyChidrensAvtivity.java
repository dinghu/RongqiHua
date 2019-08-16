package com.rongqi.hua.rongqihua.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.activity.RefreshLoadListViewActivity;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.Child;
import com.rongqi.hua.rongqihua.entity.resp.Developer;
import com.rongqi.hua.rongqihua.service.ApiService;
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
    private ArrayList<Child> childrens = new ArrayList<>();
    ApiService apiService = RetrofitHelper.createService(ApiService.class);

    @Override
    public int getItemLayout() {
        return R.layout.item_news;
    }

    @Override
    public ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void initializeViews(int position, Child child, ViewHolder viewHolder) {
        viewHolder.content.setText(child.getName());
    }

    @Override
    public void getData(int page, boolean isRefreh) {
        RetrofitHelper.sendRequest(apiService.myYejipersons(""), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    List<Child> childList = GsonUtils.fromJson(body.string(), new TypeToken<List<Developer>>() {
                    }.getType());
                    dealDataRecive(childList, false);
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
        bindView(smartRefreshLayout, list, childrens);
    }

    static class ViewHolder {
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
