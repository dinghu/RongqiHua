package com.rongqi.hua.rongqihua.fragment;

import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.fragment.RefreshLoadListViewFragment;
import com.fkh.support.ui.widget.KeyValueView;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.YejiTotal;
import com.rongqi.hua.rongqihua.service.ApiService;
import com.rongqi.hua.rongqihua.uitls.GsonUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

/**
 * Created by dinghu on 2019/8/16.
 */
public class YejiFragment extends RefreshLoadListViewFragment<YejiTotal, YejiFragment.ViewHolder> {

    ApiService apiService = RetrofitHelper.createService(ApiService.class);
    @BindView(R.id.list)
    ListView list;
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.noDataView)
    TextView noDataView;
    private ArrayList<YejiTotal> datas = new ArrayList<>();
    private Set<Integer> expandSet = new HashSet<>();


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
        return R.layout.item_yeji_total;
    }

    @Override
    public ViewHolder getViewHolder(View convertView) {
        return new ViewHolder(convertView);
    }

    @Override
    public void initializeViews(final int position, YejiTotal yejiTotal, final ViewHolder viewHolder) {
        viewHolder.kvName.setValue(yejiTotal.getName());
        viewHolder.sex.setValue(yejiTotal.isSex() ? "男" : "女");
        viewHolder.shenfenCode.setValue(yejiTotal.getNid());
        viewHolder.phone.setValue(yejiTotal.getPhone());
        viewHolder.shenfenType.setValue(yejiTotal.getType());
        viewHolder.zuanye.setValue(yejiTotal.getMajor());
        viewHolder.yuanxiao.setValue(yejiTotal.getGraduate());
        viewHolder.workCode.setValue(yejiTotal.getWorkId());
        viewHolder.address.setValue(yejiTotal.getPlace());
        viewHolder.TitleName.setText(yejiTotal.getName());
        if ("合伙人".equals(yejiTotal.getType())) {
            viewHolder.zuanye.setVisibility(View.GONE);
            viewHolder.yuanxiao.setVisibility(View.GONE);
            viewHolder.workCode.setVisibility(View.VISIBLE);
            viewHolder.address.setVisibility(View.VISIBLE);
        } else {
            viewHolder.zuanye.setVisibility(View.VISIBLE);
            viewHolder.yuanxiao.setVisibility(View.VISIBLE);
            viewHolder.workCode.setVisibility(View.GONE);
            viewHolder.address.setVisibility(View.GONE);
        }
        viewHolder.detail.setVisibility(expandSet.contains(position) ? View.VISIBLE : View.GONE);
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animation = AnimationUtils.loadAnimation(getContext(),
                        expandSet.contains(position) ? R.anim.rotate_up : R.anim.rotate_down);
                animation.setFillAfter(true);
                viewHolder.indicator_arrow.startAnimation(animation);
                if (expandSet.contains(position)) {
                    expandSet.remove(position);
                } else {
                    expandSet.add(position);
                }
                getAdapter().notifyDataSetChanged();
            }
        });
    }

    @Override
    public void getData(int page, boolean isRefreh) {
        RetrofitHelper.sendRequest(apiService.selectAllChildByTid(UserUtils.getToken()), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                try {
                    String body = baseResp.string();
                    if (!TextUtils.isEmpty(body)) {
                        List<YejiTotal> childList = GsonUtils.fromJson(body, new TypeToken<List<YejiTotal>>() {
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

    class ViewHolder {
        @BindView(R.id.TitleName)
        TextView TitleName;
        @BindView(R.id.kvName)
        KeyValueView kvName;
        @BindView(R.id.sex)
        KeyValueView sex;
        @BindView(R.id.shenfenCode)
        KeyValueView shenfenCode;
        @BindView(R.id.workCode)
        KeyValueView workCode;
        @BindView(R.id.phone)
        KeyValueView phone;
        @BindView(R.id.address)
        KeyValueView address;
        @BindView(R.id.shenfenType)
        KeyValueView shenfenType;
        @BindView(R.id.zuanye)
        KeyValueView zuanye;
        @BindView(R.id.yuanxiao)
        KeyValueView yuanxiao;
        @BindView(R.id.detail)
        LinearLayout detail;
        @BindView(R.id.indicator_arrow)
        ImageView indicator_arrow;

        View rootView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            rootView = view;
        }
    }
}
