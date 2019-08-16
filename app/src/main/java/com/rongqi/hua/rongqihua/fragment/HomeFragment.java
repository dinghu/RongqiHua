package com.rongqi.hua.rongqihua.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.adapter.BaseListAdapter;
import com.fkh.support.ui.widget.ScrollListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.activity.MyDevelopersActivity;
import com.rongqi.hua.rongqihua.activity.RegisInfotActivity;
import com.rongqi.hua.rongqihua.base.RqBaseFragment;
import com.rongqi.hua.rongqihua.entity.resp.NewsItem;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.ResponseBody;

/**
 * Created by dinghu on 2019/8/15.
 */
public class HomeFragment extends RqBaseFragment {
    @BindView(R.id.convenientBanner)
    ConvenientBanner convenientBanner;
    @BindView(R.id.head)
    RoundedImageView head;
    @BindView(R.id.company_setting)
    LinearLayout companySetting;
    @BindView(R.id.company_info)
    LinearLayout companyInfo;
    @BindView(R.id.company_members)
    LinearLayout companyMembers;
    @BindView(R.id.company_bindweichat)
    LinearLayout companyBindweichat;
    @BindView(R.id.newsList)
    ScrollListView newsList;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    BaseListAdapter newsAdapter;
    private ArrayList<NewsItem> news = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }


    class ViewHolder {
        TextView content;

        public ViewHolder(View contentView) {
            this.content = contentView.findViewById(R.id.content);
        }
    }

    @Override
    protected void initView(View view) {
        RetrofitHelper.sendRequest(apiService.relaOrderInfo(10), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                try {
                    String body = baseResp.string();
                    if (!TextUtils.isEmpty(body)) {
                        Gson gson = new Gson();
                        List<NewsItem> newsList = gson.fromJson(body, new TypeToken<List<NewsItem>>() {
                        }.getType());
                        news.addAll(newsList);
                        newsAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFail(String code, String message) {
                ToastUtils.showLong(message);
            }
        });

        newsList.setAdapter(newsAdapter = new BaseListAdapter<NewsItem, ViewHolder>(news, getContext()) {
            @Override
            public int getItemLayout() {
                return R.layout.item_news;
            }

            @Override
            public ViewHolder getViewHolder(View convertView) {
                return new ViewHolder(convertView);
            }

            @Override
            public void initializeViews(int position, NewsItem s, ViewHolder viewHolder) {
                String displayString = s.getTeacherName() + " 在 " +
                        TimeUtils.date2String(new Date(s.getInsertDate())) +
                        (s.getType() == 2 ? " 成功" : " 失败") + "发展 " + s.getStudentName()
                        + " 合伙人";
                viewHolder.content.setText(displayString);
            }
        });

    }

    @OnClick({R.id.company_setting, R.id.company_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_setting:
                ActivityUtils.startActivity(getContext(), RegisInfotActivity.class);
                break;
            case R.id.company_bindweichat:
                ActivityUtils.startActivity(getContext(), MyDevelopersActivity.class);
                break;
            case R.id.company_info:
                ActivityUtils.startActivity(getContext(), RegisInfotActivity.class);
                break;
        }
    }
}
