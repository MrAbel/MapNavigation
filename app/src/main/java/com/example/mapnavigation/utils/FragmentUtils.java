package com.example.mapnavigation.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.mapnavigation.R;
import com.example.mapnavigation.ui.TestFragment;
import com.example.mapnavigation.ui.map.MapFragment;
import com.example.mapnavigation.ui.nav.NavFragment;
import com.example.mapnavigation.ui.nav.NavPanel;
import com.example.mapnavigation.ui.nav.bus.BusPagerFragment;
import com.example.mapnavigation.ui.nav.car.CarPagerFragment;
import com.example.mapnavigation.ui.nav.foot.FootPagerFragment;
import com.example.mapnavigation.ui.nearby.NearByFragment;


/**
 * Created by zzg on 17-4-12.
 */

public class FragmentUtils {
    /**
     * main
     *
     * @param position
     * @return
     */
    public static Fragment createFragment(int position) {

        Fragment fragment = null;
        switch (position) {
            case Constants.Pager_NearBy://　附近
                fragment = NearByFragment.newInstance();
                break;
            case Constants.Pager_Map:// 地图
                fragment = MapFragment.newInstance();
                break;
            case Constants.Pager_Nav:// 导航
                fragment = NavFragment.newInstance();
                break;
        }
        return fragment;
    }

    /**
     * 创建Tab绑定的Fragment
     * @param position
     * @return
     */
    public static Fragment createTabFragment(int position){

        Fragment fragment = null;
        switch (position) {
            case 0://　公交
                fragment = BusPagerFragment.newInstance();
                break;
            case 1:// 步行
                fragment = FootPagerFragment.newInstance();
                break;
            case 2:// 驾车
                fragment = CarPagerFragment.newInstance();
                break;
        }
        return fragment;
    }

    /**
     * 动态加载Fragment
     * @param fm
     * @param parentId
     * @param fragment
     */
    public static void addFragment(FragmentManager fm, int parentId, Fragment fragment){
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(parentId, fragment);
        transaction.commit();
    }
}


