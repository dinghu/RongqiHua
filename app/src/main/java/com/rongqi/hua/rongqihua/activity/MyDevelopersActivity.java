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
import com.fkh.support.ui.adapter.BaseListAdapter;
import com.fkh.support.ui.widget.TitleView;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.Developer;
import com.rongqi.hua.rongqihua.service.ApiService;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;
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
    BaseListAdapter developersAdapter;
    @BindView(R.id.titleView)
    TitleView titleView;
    private ArrayList<Developer> developers = new ArrayList<>();

    @Override
    public ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_news;
    }

    @Override
    public void initializeViews(int position, Developer s, ViewHolder viewHolder) {
        viewHolder.content.setText(s.getName());
    }

    @Override
    public void getData(int page, boolean isRefreh) {
        RetrofitHelper.sendRequest(apiService.mydevelopTeachers(""), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody body) {
                try {
                    List<Developer> developers = GsonUtils.fromJson(body.string(), new TypeToken<List<Developer>>() {
                    }.getType());
                    dealDataRecive(developers, false);
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
                ActivityUtils.startActivity(MyDevelopersActivity.this, AddDeveloperActivity.class);
            }
        });
        bindView(smartRefreshLayout, list, developers);
        setNoDataView(noDataView);
    }


    @OnClick(R.id.titleView)
    public void onViewClicked() {
    }

    static class ViewHolder {
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
