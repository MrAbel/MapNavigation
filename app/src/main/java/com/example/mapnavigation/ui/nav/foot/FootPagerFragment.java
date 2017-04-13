package com.example.mapnavigation.ui.nav.foot;

import android.view.View;

import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.ui.ContentPage;

/**
 * Created by zzg on 17-4-13.
 */

public class FootPagerFragment extends BaseFragment {

    private View mView;
    public static FootPagerFragment newInstance() {
        FootPagerFragment fragment = new FootPagerFragment();
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
