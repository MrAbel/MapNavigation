package com.example.mapnavigation.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.MainPaperAdapter;
import com.example.mapnavigation.base.BaseActivity;
import com.example.mapnavigation.ui.customview.MaterialDrawerLayout;
import com.example.mapnavigation.utils.Constants;

public class MainActivity extends BaseActivity {

    public MaterialDrawerLayout mDrawerLayout;

    public Toolbar mToolbar;

    public ViewPager mViewPager;

    public MainPaperAdapter mPagerAdapter;

    public RadioGroup mRadioGroup;

    public RadioButton mNearbyBtn;

    public RadioButton mMapBtn;

    public RadioButton mNavBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        // 将toolbar设为标题栏
        setSupportActionBar(mToolbar);
        //　设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);
        // 在Toolbar做最左边加上导航按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // DrawerLayout绑定Toolbar
        mDrawerLayout.bindToolbar(mToolbar);

        // 获得Fragment
//        MapFragment mapFragment = (MapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.fragment_content);

        // 如果获得Fragment不为空就将其添加到Activity中
//        if (mapFragment == null) {
//            mapFragment = mapFragment.newInstance();

//            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
//                    mapFragment, R.id.fragment_content);
//        }

        // Create the presenter
  //      new MapPresenter(mapFragment);
    }

    /**
     * 初始化视图控件
     */
    private void initView() {
        mDrawerLayout = (MaterialDrawerLayout)findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        mViewPager = (ViewPager)findViewById(R.id.viewpager);
        mRadioGroup = (RadioGroup)findViewById(R.id.radio_group);
        mNearbyBtn = (RadioButton)findViewById(R.id.bottom_nearby);
        mMapBtn = (RadioButton)findViewById(R.id.bottom_map);
        mNavBtn = (RadioButton)findViewById(R.id.bottom_nav);

        mPagerAdapter = new MainPaperAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setCurrentItem(Constants.Pager_Map, false);
        mMapBtn.setChecked(true);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.bottom_nearby:
                        mViewPager.setCurrentItem(Constants.Pager_NearBy, false);
                        //homeTitle.setText("首页");
                        break;
                    case R.id.bottom_map:
                        mViewPager.setCurrentItem(Constants.Pager_Map, false);
                        //homeTitle.setText("停车场");
                        break;
                    case R.id.bottom_nav:
                        mViewPager.setCurrentItem(Constants.Pager_Nav, false);
                        //homeTitle.setText("我");
                        break;
                }
            }
        });
    }

}
