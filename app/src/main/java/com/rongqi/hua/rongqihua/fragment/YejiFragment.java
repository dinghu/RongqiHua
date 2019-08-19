package com.rongqi.hua.rongqihua.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.fragment.RefreshLoadListViewFragment;
import com.fkh.support.ui.widget.KeyValueView;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.Child;
import com.rongqi.hua.rongqihua.service.ApiService;
import com.rongqi.hua.rongqihua.uitls.GsonUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.ResponseBody;

/**
 * Created by dinghu on 2019/8/16.
 */
public class YejiFragment extends RefreshLoadListViewFragment<Child, YejiFragment.ViewHolder> {

    ApiService apiService = RetrofitHelper.createService(ApiService.class);
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.noDataView)
    TextView noDataView;
    private ArrayList<Child> datas = new ArrayList<>();

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

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yeji;
    }

    @Override
    protected void initView(View view) {
        bindView(smartRefreshLayout, list, datas);
        setNoDataView(noDataView);
    }

    @Override
    public int getItemLayout() {
        return R.layout.item_child;
    }

    @Override
    public ViewHolder getViewHolder(View convertView) {
        return null;
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
        RetrofitHelper.sendRequest(apiService.selectAllChildByTid(UserUtils.getToken()), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                try {
                    String body = baseResp.string();
                    if (!TextUtils.isEmpty(body)) {
                        List<Child> childList = GsonUtils.fromJson(body, new TypeToken<List<Child>>() {
                        }.getType());
                        dealDataRecive(childList, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String code, String message) {
                ToastUtils.showLong(message);
                dealError(message);
            }
        });
    }
}
