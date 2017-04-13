package com.example.mapnavigation.ui.nav;

import android.content.Context;

import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BasePagerFragment;
import com.example.mapnavigation.utils.AppUtils;

/**
 * Created by zzg on 17-4-13.
 */

public class NavPanel extends BasePagerFragment {


    /**
     * 用于获取View(NavPanel)的实例
     * @return
     */
    public static NavPanel newInstance() {
        NavPanel fragment = new NavPanel();
        return fragment;
    }

    // --------------------BasePagerFragment-----------------------

    @Override
    protected String[] setTitles() {

        return AppUtils.getStringArray(R.array.nav_method);
    }
}
