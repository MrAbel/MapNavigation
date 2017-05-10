package com.example.mapnavigation.ui.activtiy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.example.mapnavigation.MapApplication;
import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.MainPaperAdapter;
import com.example.mapnavigation.base.BaseActivity;
import com.example.mapnavigation.controller.QueryerManager;
import com.example.mapnavigation.ui.customview.MaterialDrawerLayout;
import com.example.mapnavigation.utils.Constants;
import com.example.mapnavigation.utils.ToastUtils;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


import static android.support.v7.widget.AppCompatDrawableManager.get;
import static com.example.mapnavigation.utils.Constants.EVENT_SEARCH_POI;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    // 侧滑栏部件
    public MaterialDrawerLayout mDrawerLayout;
    // 标题栏部件
    public Toolbar mToolbar;
    // 加载Fragment的ViewPager部件
    public ViewPager mViewPager;
    // 底部工具栏部件
    public RadioGroup mRadioGroup;
    // 工具栏中附近按钮
    public RadioButton mNearbyBtn;
    // 工具栏中地图按钮
    public RadioButton mMapBtn;
    // 工具栏中导航按钮
    public RadioButton mNavBtn;
    // ViewPager的适配器
    private MainPaperAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 初始化工作
        init();

        // 将toolbar设为标题栏
        setSupportActionBar(mToolbar);
        //　设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        // 在Toolbar做最左边加上导航按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // DrawerLayout绑定Toolbar
        mDrawerLayout.bindToolbar(mToolbar);



    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        mDrawerLayout = (MaterialDrawerLayout)findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar)findViewById(R.id.title_bar);
        mViewPager = (ViewPager)findViewById(R.id.main_viewpager);
        mRadioGroup = (RadioGroup)findViewById(R.id.main_radio_group);
        mNearbyBtn = (RadioButton)findViewById(R.id.main_nearby);
        mMapBtn = (RadioButton)findViewById(R.id.main_map);
        mNavBtn = (RadioButton)findViewById(R.id.main_nav);
    }


    /**
     * 加载MainActivity时的一些初始化操作
     */
    private void init(){

        // 初始化部件引用
        initView();

        // 为ViewPager设置适配器
        mPagerAdapter = new MainPaperAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        // 设置ViewPager中可以容纳(不需要重新加载)的最多Fragment
        mViewPager.setOffscreenPageLimit(3);
        // 设置初始状态下显示的Fragment
        mViewPager.setCurrentItem(Constants.Pager_Map, false);
        mMapBtn.setChecked(true);



        // 注册监听器
        regiesterListener();
    }

    /**
     * 注册监听器
     */
    private void regiesterListener(){
        mRadioGroup.setOnCheckedChangeListener(this);
    }



    // --------------------RadioGroup.OnCheckedChangeListener--------------------
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.main_nearby:
                mViewPager.setCurrentItem(Constants.Pager_NearBy, false);
                mToolbar.setTitle("附近");
                break;
            case R.id.main_map:
                mViewPager.setCurrentItem(Constants.Pager_Map, false);
                mToolbar.setTitle("地图");
                break;
            case R.id.main_nav:
                mViewPager.setCurrentItem(Constants.Pager_Nav, false);
                mToolbar.setTitle("导航");
                break;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }

}
