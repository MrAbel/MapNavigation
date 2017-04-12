package com.example.mapnavigation.ui.nav;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

import com.amap.api.maps.MapView;
import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.ui.customview.SearchBar;
import com.example.mapnavigation.ui.map.MapContract;
import com.example.mapnavigation.ui.map.MapFragment;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-11.
 */

public class NavFragment extends BaseFragment implements NavContract.View, View.OnClickListener{

    // --------------------fields-----------------------
    private NavContract.Presenter mPresenter;

    // 引入的布局引用
    public View mView;
    // 交换按钮应用
    public Button mSwitchBtn;
    // 驾车按钮引用
    public Button mCarRouteBtn;
    // 公交按钮引用
    public Button mBusRouteBtn;
    //　步行按钮引用
    public Button mFootRouteBtn;
    // 起始地点输入框引用
    public AutoCompleteTextView mBeginSiteEdit;
    // 结束地点输入框引用
    public AutoCompleteTextView mEndSiteEdit;


    // --------------------------碎片生命周期---------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 动态加载布局文件
        mView = inflater.inflate(R.layout.fragment_content_nav, container, false);
        return mView;
    }


    // ------------------普通函数--------------------------

    /**
     * 用于获取View(MapFragment)的实例
     * @return
     */
    public static NavFragment newInstance() {
        NavFragment fragment = new NavFragment();
        return fragment;
    }

    /**
     * 初始化视图控件
     */
    private void initView(){
        mBeginSiteEdit = (AutoCompleteTextView) mView.findViewById(R.id.begin_site);
        mEndSiteEdit = (AutoCompleteTextView) mView.findViewById(R.id.end_site);
        mSwitchBtn = (Button) mView.findViewById(R.id.swith_site);
        mBusRouteBtn = (Button) mView.findViewById(R.id.route_bus);
        mFootRouteBtn = (Button) mView.findViewById(R.id.route_foot);
        mCarRouteBtn = (Button) mView.findViewById(R.id.route_car);
        regiesterListener();
    }

    /**
     * 注册监听器
     */
    public void regiesterListener(){
        mSwitchBtn.setOnClickListener(this);
        mCarRouteBtn.setOnClickListener(this);
        mBusRouteBtn.setOnClickListener(this);
        mFootRouteBtn.setOnClickListener(this);
    }
    // --------------------NavContract.View-----------------------
    @Override
    public void setPresenter(NavContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    // --------------------View.OnClickListener-----------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.route_bus:
                break;
            case R.id.route_car:
                break;
            case R.id.route_foot:
                break;
            case R.id.swith_site:
                break;
            case R.id.begin_site:
                break;
            case R.id.end_site:
                break;
            default:
                break;
        }
    }
}
