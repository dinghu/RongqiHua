package com.fkh.support.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;


/**
 * Created by dinghu on 2019/4/1.
 */
public class IndicatorFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {
    private List<T> fragmentList;
    private List<String> titleList;
    private boolean isNeedRenewPage = false;

    public IndicatorFragmentStatePagerAdapter(FragmentManager fm, List<T> fragmentList, List<String> titleList) {
        this(fm, fragmentList, titleList, false);
    }

    public IndicatorFragmentStatePagerAdapter(FragmentManager fm, List<T> fragmentList, List<String> titleList, boolean isNeedRenewPage) {
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
