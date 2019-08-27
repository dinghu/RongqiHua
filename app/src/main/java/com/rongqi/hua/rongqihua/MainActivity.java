package com.rongqi.hua.rongqihua;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fkh.support.ui.activity.HomeActivity;
import com.fkh.support.ui.widget.NoScrollViewPager;
import com.fkh.support.ui.widget.alphatab.AlphaTabsLayout;
import com.fkh.support.ui.widget.alphatab.OnTabChangedListner;
import com.rongqi.hua.rongqihua.fragment.HomeFragment;
import com.rongqi.hua.rongqihua.fragment.MineFragment;
import com.rongqi.hua.rongqihua.fragment.YejiFragment;
import com.rongqi.hua.rongqihua.fragment.YejiNew2;
import com.rongqi.hua.rongqihua.fragment.YejiNewFragment;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends HomeActivity {

    @BindView(R.id.alphaIndicator)
    AlphaTabsLayout alphaIndicator;
    @BindView(R.id.viewPager)
    NoScrollViewPager viewPager;
    ArrayList<Fragment> fragments = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }


    @Override
    protected void initView() {
        mAlphaIndicator = MainActivity.this.alphaIndicator;
        mViewPager = viewPager;
        fragments.add(new HomeFragment());
        fragments.add(new YejiNew2());
        fragments.add(new MineFragment());
        bindFragement(fragments);
        alphaIndicator.setOnTabChangedListner(new OnTabChangedListner() {
            @Override
            public boolean onTabSelected(int tabNum) {
                if (tabNum == 1) {
                    if (!UserUtils.isLogin()) {
                        UserUtils.reqLogin(MainActivity.this);
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
