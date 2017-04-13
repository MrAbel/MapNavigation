package com.example.mapnavigation.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.ui.nav.NavContract;

/**
 * Created by zzg on 17-4-12.
 */

public class TestFragment extends BaseFragment {

    public static TestFragment newInstance() {
        TestFragment fragment = new TestFragment();
        return fragment;
    }

    private View mLoadingView;
    private FrameLayout frameLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        frameLayout = new FrameLayout(getActivity());
        mLoadingView = inflater.inflate(R.layout.fragment_content_nav, container, false);
        frameLayout.addView(mLoadingView);
        return frameLayout;
    }

    @Override
    public View getLoadedView() {
        return mLoadingView;
    }

    @Override
    public Object getData() {
        return 1;
    }
}
