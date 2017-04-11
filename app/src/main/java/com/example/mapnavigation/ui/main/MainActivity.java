package com.example.mapnavigation.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.amap.api.maps.MapView;
import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseActivity;
import com.example.mapnavigation.ui.customview.MaterialDrawerLayout;
import com.example.mapnavigation.utils.ActivityUtils;

public class MainActivity extends BaseActivity {

    public MaterialDrawerLayout mDrawerLayout;

    public Toolbar mToolbar;
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
        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_content_main);

        // 如果获得Fragment不为空就将其添加到Activity中
        if (mainFragment == null) {
            mainFragment = mainFragment.newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mainFragment, R.id.fragment_content_main);
        }

        // Create the presenter
        new MainPresenter(mainFragment);
    }

    /**
     * 初始化视图控件
     */
    private void initView(){
        mDrawerLayout = (MaterialDrawerLayout)findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
    }

}
