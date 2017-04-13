package com.example.mapnavigation.ui.nav.bus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.ui.ContentPage;

/**
 * Created by zzg on 17-4-12.
 */

public class BusPagerFragment extends BaseFragment{

    private View mView;

    @Override
    public View getLoadedView() {
        mView = View.inflate(mContext, R.layout.test, null);
        return mView;
    }

    @Override
    public Object getData() {

        return ContentPage.PageState.STATE_LOADED;
    }

    public static BusPagerFragment newInstance() {
        BusPagerFragment fragment = new BusPagerFragment();
        return fragment;
    }
}
