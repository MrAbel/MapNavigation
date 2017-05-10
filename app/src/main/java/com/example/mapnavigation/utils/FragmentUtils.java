package com.example.mapnavigation.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.example.mapnavigation.ui.map.MapFragment;
import com.example.mapnavigation.ui.nav.NavFragment;
import com.example.mapnavigation.ui.nav.panel.HistoryFragment;
import com.example.mapnavigation.ui.nav.panel.bus.BusPagerFragment;
import com.example.mapnavigation.ui.nav.panel.car.CarPagerFragment;
import com.example.mapnavigation.ui.nav.panel.foot.WalkRouteFragment;
import com.example.mapnavigation.ui.nearby.NearByFragment;



/**
 * Created by zzg on 17-4-12.
 */

public class FragmentUtils {
    /**
     * 为主ViewPager创建Fragment
     *
     * @param position
     * @return
     */
    public static Fragment createFragmentForMainVP(int position) {

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
    public static Fragment createFragmentForNavPanel(int position){

        Fragment fragment = null;
        switch (position) {
            case Constants.Pager_Walk:// 步行
                fragment = WalkRouteFragment.newInstance();
                break;
            case Constants.Pager_Bus://　公交
                fragment = BusPagerFragment.newInstance();
                break;
            case Constants.Pager_Car:// 驾车
                fragment = CarPagerFragment.newInstance();
                break;
        }
        return fragment;
    }

    public static Fragment create(int position){

        Fragment fragment = null;
        switch (position) {
            case Constants.Pager_Walk:// 步行
                fragment = HistoryFragment.newInstance();
                break;
            case Constants.Pager_Bus://　公交
                fragment = HistoryFragment.newInstance();
                break;
            case Constants.Pager_Car:// 驾车
                fragment = HistoryFragment.newInstance();
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


