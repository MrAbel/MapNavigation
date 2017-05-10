package com.example.mapnavigation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mapnavigation.utils.FragmentUtils;

/**
 * Created by zzg on 17-4-16.
 */

public class NavPagerAdapt extends FragmentStatePagerAdapter {
    private String[] mTitles;
    // --------------------构造函数------------------
    public NavPagerAdapt(FragmentManager fm, String[] titles) {
        super(fm);
        mTitles  = titles;
    }

    // ------------------FragmentStatePagerAdapter--------------------
    /**
     * 根据position来创建相应的Fragment
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return FragmentUtils.create(position);
    }

    /**
     * 获得Tab Pager的数量
     * @return
     */
    @Override
    public int getCount() {

        return mTitles.length;
    }

    /**
     * 获取Tab　Pager相应的标题
     * @param position
     * @return
     */
    @Override
    public CharSequence getPageTitle(int position) {

        return mTitles[position];
    }
}
