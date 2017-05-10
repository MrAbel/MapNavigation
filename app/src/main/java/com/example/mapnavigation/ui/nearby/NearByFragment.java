package com.example.mapnavigation.ui.nearby;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseFragment;

/**
 * Created by zzg on 17-4-12.
 */

public class NearByFragment extends BaseFragment {


    // 引入的布局引用
    public View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 动态加载布局文件
        mView = inflater.inflate(R.layout.fragment_content_nearby, container, false);
        return mView;
    }

    @Override
    public View getLoadedView() {
        return mView;
    }

    @Override
    public Object getData() {
        return 1;
    }


    /**
     * 用于获取View(NearByFragment)的实例
     * @return
     */
    public static NearByFragment newInstance() {
        NearByFragment fragment = new NearByFragment();
        return fragment;
    }
}
