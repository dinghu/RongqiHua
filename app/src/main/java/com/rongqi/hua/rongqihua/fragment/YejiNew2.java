package com.rongqi.hua.rongqihua.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.fragment.BaseFragment;
import com.google.gson.reflect.TypeToken;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.activity.LoginActivity;
import com.rongqi.hua.rongqihua.entity.resp.YejiTotal;
import com.rongqi.hua.rongqihua.fragment.yeji.YejiNodeBinder;
import com.rongqi.hua.rongqihua.service.ApiService;
import com.rongqi.hua.rongqihua.uitls.GsonUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewAdapter;

/**
 * Created by dinghu on 2019/8/23.
 */
public class YejiNew2 extends BaseFragment {
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    List<TreeNode> nodes = new ArrayList<>();


    ApiService apiService = RetrofitHelper.createService(ApiService.class);

    TreeViewAdapter adapter;

    private ArrayList<YejiTotal> datas = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_yeji_3;
    }

    @Override
    protected void initView(View view) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new TreeViewAdapter(nodes, Arrays.asList(new YejiNodeBinder()));
        adapter.setPadding(SizeUtils.dp2px(16));

        adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
            @Override
            public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
                if (!node.isLeaf()) {
                    onToggle(!node.isExpand(), holder);
                }
                if (node.isLeaf()) {
                    node.toggle();
                }
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
                YejiNodeBinder.ViewHolder dirViewHolder = (YejiNodeBinder.ViewHolder) holder;
                final ImageView ivArrow = dirViewHolder.getIndicatorArrow();
                int rotateDegree = isExpand ? 90 : -90;
                ivArrow.animate().rotationBy(rotateDegree)
                        .start();
            }
        });
        rv.setAdapter(adapter);
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getData();
            }
        }, 100);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtils.isLogin() && LoginActivity.needRefresh) {
            LoginActivity.needRefresh = false;
            swipeRefreshLayout.setRefreshing(true);
            getData();
        }
    }


    public void getData() {
        RetrofitHelper.sendRequest(apiService.selectAllChildByTid(UserUtils.getToken()), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                swipeRefreshLayout.setRefreshing(false);
                try {
                    String body = baseResp.string();
                    if (!TextUtils.isEmpty(body)) {
                        List<YejiTotal> childList = GsonUtils.fromJson(body, new TypeToken<List<YejiTotal>>() {
                        }.getType());
                        datas.clear();
                        datas.addAll(childList);
                        setData(datas);
                        adapter.refresh(nodes);
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

    private void setLevelNodes(TreeNode<YejiTotal> parentLevel, List<YejiTotal> yejiTotals) {
        if (yejiTotals != null) {
            for (YejiTotal yejiTotal : yejiTotals) {
                TreeNode<YejiTotal> childLevel = new TreeNode(yejiTotal);
                parentLevel.addChild(childLevel);
                if (yejiTotal.getSub() != null && !yejiTotal.getSub().isEmpty()) {
                    setLevelNodes(childLevel, yejiTotal.getSub());
                }
            }
        }

    }

    private void setData(List<YejiTotal> childList) {
        nodes.clear();

        for (YejiTotal yejiTotal : childList) {
            TreeNode<YejiTotal> firstLevel = new TreeNode(yejiTotal);
            nodes.add(firstLevel);
            setLevelNodes(firstLevel, yejiTotal.getSub());
        }
    }
}
