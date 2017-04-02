package com.example.mapnavigation.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.amap.api.maps.MapView;
import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseActivity;
import com.example.mapnavigation.ui.customview.MaterialDrawerLayout;

public class MainActivity extends BaseActivity {

    private MapView mMapView = null;
    // @BindView(R.id.drawer_layout)
    public MaterialDrawerLayout mDrawerLayout;

    // @BindView(R.id.toolbar)
    public Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取控件引用
        mDrawerLayout = (MaterialDrawerLayout)findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);

        // 将toolbar设为标题栏
        setSupportActionBar(mToolbar);

        //　设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);

        // 在Toolbar做最左边加上导航按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DrawerLayout绑定Toolbar
        mDrawerLayout.bindToolbar(mToolbar);


    }


}
