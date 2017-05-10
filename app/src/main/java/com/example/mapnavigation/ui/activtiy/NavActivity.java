package com.example.mapnavigation.ui.activtiy;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseNaviActivity;

public class NavActivity extends BaseNaviActivity {


    private LatLonPoint s;
    private LatLonPoint d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nav);
        super.onCreate(savedInstanceState);

        // 获取传过来的数据
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("data");
        s = bundle.getParcelable("s");
        d = bundle.getParcelable("d");

        //获取 AMapNaviView 实例
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        mAMapNavi.calculateWalkRoute(new NaviLatLng(s.getLatitude(), s.getLongitude()),
                new NaviLatLng(d.getLatitude(), d.getLongitude()));

    }

    @Override
    public void onCalculateRouteSuccess() {
        super.onCalculateRouteSuccess();
        mAMapNavi.startNavi(NaviType.GPS);
    }


}
