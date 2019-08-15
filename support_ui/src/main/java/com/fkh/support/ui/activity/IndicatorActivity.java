package com.fkh.support.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


import com.fkh.support.ui.adapter.IndicatorFragmentAdapter;
import com.fkh.support.ui.adapter.IndicatorFragmentStatePagerAdapter;
import com.fkh.support.ui.widget.LinePagerIndicatorView;

import java.util.List;

/**
 * Created by dinghu on 2019/4/1.
 */
public abstract class IndicatorActivity<T extends Fragment> extends BaseActivity {

    /**
     * 滑动分页Pager
     **/
    private PagerAdapter myFragmentPagerAdapter;
    protected boolean isNeedRenewPage = false;

    //通知Viewpager更新
    public void notifyPageDataSetChanged() {
        myFragmentPagerAdapter.notifyDataSetChanged();
    }

    public void bindPage(ViewPager viewPager, LinePagerIndicatorView linePagerIndicator, List<T> fragmentList, List<String> titleList) {
        if (viewPager != null) {
            if (isNeedRenewPage) {
                myFragmentPagerAdapter = new IndicatorFragmentStatePagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
            } else {
                myFragmentPagerAdapter = new IndicatorFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);

            }
            viewPager.setAdapter(myFragmentPagerAdapter);
        }
        viewPager.setOffscreenPageLimit(fragmentList.size() > 1 ? fragmentList.size() - 1 : 1);
        //绑定viewpager
        linePagerIndicator.setViewPager(viewPager);
        linePagerIndicator.setIndicatorMode(fragmentList.size() >= 5 ? LinePagerIndicatorView.IndicatorMode.MODE_NOWEIGHT_EXPAND_SAME :
                LinePagerIndicatorView.IndicatorMode.MODE_WEIGHT_NOEXPAND_NOSAME);// 设置模式
    }
}