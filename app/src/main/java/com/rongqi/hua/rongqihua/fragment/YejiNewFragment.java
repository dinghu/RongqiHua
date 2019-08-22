package com.rongqi.hua.rongqihua.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.adapter.BaseListAdapter;
import com.fkh.support.ui.fragment.BaseFragment;
import com.fkh.support.ui.widget.KeyValueView;
import com.fkh.support.ui.widget.ScrollListView;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.YejiTotal;
import com.rongqi.hua.rongqihua.service.ApiService;
import com.rongqi.hua.rongqihua.uitls.GsonUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class YejiNewFragment extends BaseFragment {
    @BindView(R.id.list)
    ScrollListView list;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<YejiTotal> datas = new ArrayList<>();

    BaseAdapter parentAdapter;

    private Set<YejiTotal> expandSetParent = new HashSet<>();
    private Set<YejiTotal> expandSetChild = new HashSet<>();
    private HashMap<String, BaseListAdapter> listAdapterHashMap = new HashMap<>();


    ApiService apiService = RetrofitHelper.createService(ApiService.class);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yeji_new;
    }

    @Override
    protected void initView(View view) {
        list.setAdapter(parentAdapter = new BaseListAdapter<YejiTotal, ViewHolder>(datas, getActivity()) {
            @Override
            public int getItemLayout() {
                return R.layout.item_yeji_total;
            }

            @Override
            public ViewHolder getViewHolder(View convertView) {
                return new ViewHolder(convertView);
            }

            @Override
            public void initializeViews(int position, YejiTotal yejiTotal, ViewHolder viewHolder) {
                setYejiTotalData(true, yejiTotal, viewHolder);

                BaseListAdapter adapter = listAdapterHashMap.get(position + "");
                if (adapter == null) {
                    adapter = new BaseListAdapter<YejiTotal, ViewHolder>(yejiTotal.getSub(), getContext()) {
                        @Override
                        public int getItemLayout() {
                            return R.layout.item_yeji_total;
                        }

                        @Override
                        public ViewHolder getViewHolder(View convertView) {
                            return new ViewHolder(convertView);
                        }

                        @Override
                        public void initializeViews(int position, YejiTotal yejiTotal, ViewHolder viewHolder) {
                            setYejiTotalData(false, yejiTotal, viewHolder);
                        }
                    };
                    //子业绩
                    viewHolder.subYeji.setAdapter(adapter);
                } else {
                    adapter.notifyDataSetChanged();
                }
            }
        });
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getData();
            }
        });
    }

    private void setYejiTotalData(final boolean isParent, final YejiTotal yejiTotal, final ViewHolder viewHolder) {
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
        viewHolder.detail.setVisibility(expandSetParent.contains(yejiTotal) ? View.VISIBLE : View.GONE);
        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Set<YejiTotal> expandSet = isParent ? expandSetParent : expandSetChild;
                Animation animation = AnimationUtils.loadAnimation(getContext(),
                        expandSet.contains(yejiTotal) ? R.anim.rotate_up : R.anim.rotate_down);
                animation.setFillAfter(true);
                viewHolder.indicator_arrow.startAnimation(animation);
                if (expandSet.contains(yejiTotal)) {
                    expandSet.remove(yejiTotal);
                } else {
                    expandSet.add(yejiTotal);
                }
                parentAdapter.notifyDataSetChanged();
            }
        });
    }

    public void getData() {
        RetrofitHelper.sendRequest(apiService.selectAllChildByTid(UserUtils.getToken()), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                try {
                    swipeRefreshLayout.setRefreshing(false);
                    String body = baseResp.string();
                    if (!TextUtils.isEmpty(body)) {
                        List<YejiTotal> childList = GsonUtils.fromJson(body, new TypeToken<List<YejiTotal>>() {
                        }.getType());
                        datas.clear();
                        datas.addAll(childList);
                        parentAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String code, String message) {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.showLong(message);
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
        @BindView(R.id.subYeji)
        ScrollListView subYeji;

        View rootView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
            rootView = view;
        }
    }
}
