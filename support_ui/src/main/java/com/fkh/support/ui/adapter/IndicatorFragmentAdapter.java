package com.fkh.support.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by dinghu on 2019/4/1.
 */
public class IndicatorFragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {
    private List<T> fragmentList;
    private boolean isNeedRenewPage = false;
    private List<String> titleList;

    public IndicatorFragmentAdapter(FragmentManager fm, List<T> fragmentList, List<String> titleList) {
        this(fm, fragmentList, titleList, false);
    }

    public IndicatorFragmentAdapter(FragmentManager fm, List<T> fragmentList, List<String> titleList, boolean isNeedRenewPage) {
        super(fm);
        this.fragmentList = fragmentList;
        this.isNeedRenewPage = isNeedRenewPage;
        this.titleList = titleList;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Fragment getItem(int arg0) {
        return (fragmentList == null || fragmentList.size() == 0) ? null
                : fragmentList.get(arg0);
    }


    @Override
    public int getItemPosition(Object object) {
        if (isNeedRenewPage) {
            return POSITION_NONE;
        } else {
            return super.getItemPosition(object);
        }

    }

    @Override
    public int getCount() {
        return fragmentList == null ? 0 : fragmentList.size();
    }
}
