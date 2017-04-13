package com.example.mapnavigation.ui.nav.car;

import android.view.View;

import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.ui.ContentPage;

/**
 * Created by zzg on 17-4-13.
 */

public class CarPagerFragment extends BaseFragment {
    private View mView;

    /**
     * 用于获取View(CarPagerFragment)的实例
     * @return
     */
    public static CarPagerFragment newInstance() {
        CarPagerFragment fragment = new CarPagerFragment();
        return fragment;
    }
    @Override
    public View getLoadedView() {
        mView = View.inflate(mContext, R.layout.test, null);
        return mView;
    }

    @Override
    public Object getData() {
        return ContentPage.PageState.STATE_LOADED;
    }
}
