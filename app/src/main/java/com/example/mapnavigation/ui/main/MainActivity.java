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

    private MapView mMapView = null;
    // @BindView(R.id.drawer_layout)
    public MaterialDrawerLayout mDrawerLayout;

    // @BindView(R.id.toolbar)
    public Toolbar mToolbar;
    public static final String EXTRA_TASK_ID = "TASK_ID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 获取控件引用
        mDrawerLayout = (MaterialDrawerLayout)findViewById(R.id.drawer_layout);
        mToolbar = (Toolbar)findViewById(R.id.toolbar);
        //mMapView = (MapView)findViewById(R.id.id_map_view);

        // 将toolbar设为标题栏
        setSupportActionBar(mToolbar);

        //　设置返回键可用
        getSupportActionBar().setHomeButtonEnabled(true);

        // 在Toolbar做最左边加上导航按钮
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // DrawerLayout绑定Toolbar
        mDrawerLayout.bindToolbar(mToolbar);

        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        //mMapView.onCreate(savedInstanceState);
        // 将View添加到Activity中
        String taskId = getIntent().getStringExtra(EXTRA_TASK_ID);

        MainFragment mainFragment = (MainFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragment_content);

        if (mainFragment == null) {
            mainFragment = mainFragment.newInstance(taskId);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mainFragment, R.id.fragment_content);
        }

        // Create the presenter
        new MainPresenter(
                taskId,
                mainFragment);
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
//        mMapView.onDestroy();
//    }
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
//        mMapView.onResume();
//    }
//    @Override
//    protected void onPause() {
//        super.onPause();
//        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
//        mMapView.onPause();
//    }
//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
//        mMapView.onSaveInstanceState(outState);
//    }
}
