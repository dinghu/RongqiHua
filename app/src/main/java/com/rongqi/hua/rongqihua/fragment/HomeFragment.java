package com.rongqi.hua.rongqihua.fragment;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.blankj.utilcode.util.TimeUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.fkh.support.engine.retrofit.ResponseListener;
import com.fkh.support.engine.retrofit.RetrofitHelper;
import com.fkh.support.ui.adapter.BaseListAdapter;
import com.fkh.support.ui.widget.ScrollListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.activity.ModifyRegistInfoActivity;
import com.rongqi.hua.rongqihua.activity.MyChidrensAvtivity;
import com.rongqi.hua.rongqihua.activity.MyDevelopersActivity;
import com.rongqi.hua.rongqihua.activity.RegisInfotActivity;
import com.rongqi.hua.rongqihua.activity.ZhengceActivity;
import com.rongqi.hua.rongqihua.base.RqBaseFragment;
import com.rongqi.hua.rongqihua.entity.resp.NewsItem;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

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
    ListView newsList;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    BaseListAdapter newsAdapter;
    List<Integer> bannerList = new ArrayList<>();
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

    private void getData() {
        RetrofitHelper.sendRequest(apiService.relaOrderInfo(10), new ResponseListener<ResponseBody>() {
            @Override
            public void onSuccess(ResponseBody baseResp) {
                try {
                    String body = baseResp.string();
                    if (!TextUtils.isEmpty(body)) {
                        Gson gson = new Gson();
                        List<NewsItem> newsList = gson.fromJson(body, new TypeToken<List<NewsItem>>() {
                        }.getType());
                        swipeRefreshLayout.setRefreshing(false);
                        news.clear();
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
    }

    private String data2String(String dateString) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy", Locale.US);
        Date date = TimeUtils.string2Date(dateString, simpleDateFormat);
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return TimeUtils.date2String(date, simpleDateFormat1);
    }

    @Override
    protected void initView(View view) {
        bannerList.add(R.drawable.swiper1);
        bannerList.add(R.drawable.swiper2);
        bannerList.add(R.drawable.swiper3);
        bannerList.add(R.drawable.swiper4);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }
        });
        newsList.setFocusable(false);

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
                String displayString = "";

                if (s.getType() == 2) {
                    displayString = s.getTeacherName() + " 在 " +
                            data2String(s.getInsertDate()) + (TextUtils.isEmpty(s.getStudentName()) ?
                            " 失败发展 " : " 成功发展 ") + s.getStudentName()
                            + " 合伙人";
                } else {
                    displayString = s.getTeacherName() + " 在 " +
                            data2String(s.getInsertDate()) + (TextUtils.isEmpty(s.getStudentName()) ?
                            " 失败招收 " : " 成功招收 ") + s.getStudentName()
                            + " 学生";
                }
                viewHolder.content.setText(displayString);
            }
        });
        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
                getData();
            }
        }, 100);
        initBanner();
    }


    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView adsImageView;
        private TextView adsTextView;

        @Override
        public View createView(Context context) {
            View adsItemView = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
            adsImageView = adsItemView.findViewById(R.id.ads_image);
            adsTextView = adsItemView.findViewById(R.id.ads_description);
            return adsItemView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            adsImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //
                }
            });
            adsImageView.setImageResource(data);
        }
    }

    void initBanner() {
        convenientBanner.setFocusable(false);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, bannerList)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
                .setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
    }

    @Override
    public void onResume() {
        super.onResume();
        //开始自动翻页
        convenientBanner.startTurning(3000);
        convenientBanner.setCanLoop(true);
    }

    @OnClick({R.id.company_setting, R.id.company_info, R.id.company_bindweichat, R.id.company_members})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.company_members:
                ActivityUtils.startActivity(getContext(), ZhengceActivity.class);
                break;
            case R.id.company_setting:
                if (!UserUtils.isLogin()) {
                    UserUtils.reqLogin(getContext());
                    return;
                }
                if (UserUtils.getUserInfo() != null) {
                    ActivityUtils.startActivity(getContext(), ModifyRegistInfoActivity.class);
                } else {
                    ActivityUtils.startActivity(getContext(), RegisInfotActivity.class);
                }

                break;
            case R.id.company_bindweichat:
                ActivityUtils.startActivity(getContext(), MyDevelopersActivity.class);
                break;
            case R.id.company_info:
                ActivityUtils.startActivity(getContext(), MyChidrensAvtivity.class);
                break;
        }
    }
}
