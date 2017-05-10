package com.example.mapnavigation.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.mapnavigation.utils.Constants;
import com.example.mapnavigation.utils.FragmentUtils;

/**
 * MainPagerAdapter
 * 为ViewPager提供数据。
 * Created by zzg on 17-4-12.
 */

public class MainPaperAdapter extends FragmentStatePagerAdapter {

    // --------------------构造函数------------------
    public MainPaperAdapter(FragmentManager fm){
        super(fm);
    }


    // ------------------FragmentStatePagerAdapter--------------------
    /**
     * 根据position来创建相应的Fragment
     * @param position
     * @return
     */
    @Override
    public Fragment getItem(int position) {
        return FragmentUtils.createFragmentForMainVP(position);
    }

    /**
     * 获得Pager的数量
     * @return
     */
    @Override
    public int getCount() {

        return Constants.Pager_Main_ALL;
    }
}
