package com.example.mapnavigation.ui.nav.panel.foot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.LatLonPoint;
import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.RouteAdapter;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.di.module.RouteHistory;
import com.example.mapnavigation.ui.ContentPage;
import com.example.mapnavigation.ui.activtiy.MainActivity;
import com.example.mapnavigation.ui.activtiy.NavActivity;
import com.example.mapnavigation.utils.ToastUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzg on 17-4-13.
 */

public class WalkRouteFragment extends BaseFragment {

    private View mView;
    private MapView mMapView;
    private static LinearLayout mBottomLayout;
    private static TextView mRotueTimeDes;
    private TextView mRouteDetailDes;

    private static AMap mAMap;
    private static LatLonPoint mS;
    private static LatLonPoint mD;
    private static RecyclerView recyclerView;
    private List<RouteHistory> list = new ArrayList<RouteHistory>();


    public static AMap getAMap(){
        return mAMap;
    }

    public static WalkRouteFragment newInstance() {
        WalkRouteFragment fragment = new WalkRouteFragment();
        return fragment;

    }

    /**
     * 初始化视图控件
     */
    private void initView(){
        mMapView = (MapView) mView.findViewById(R.id.route_map);
        mBottomLayout = (LinearLayout) mView.findViewById(R.id.bottom_layout);
        mRotueTimeDes = (TextView)mView.findViewById(R.id.firstline);
        mRouteDetailDes = (TextView)mView.findViewById(R.id.secondline);
        recyclerView = (RecyclerView)mView.findViewById(R.id.pull_refresh_list);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        initdata();
        recyclerView.setLayoutManager(layoutManager);
        RouteAdapter adapter = new RouteAdapter(list);
        recyclerView.setAdapter(adapter);


        mBottomLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("s", mS);
                bundle.putParcelable("d", mD);
                Intent intent = new Intent("com.example.mapnavigation.ACTION_START");
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });

    }


    private void initdata() {
        list = DataSupport.findAll(RouteHistory.class);
    }
    private void init(){
        initView();
    }

    public static void updata(String des){
        mBottomLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        mRotueTimeDes.setText(des);

    }

    public static void setLocation(LatLonPoint s, LatLonPoint d){
        mS = s;
        mD =d;
    }

    @Override
    public View getLoadedView() {
        // 初始化时加载历史步行路线布局
        mView = View.inflate(mContext, R.layout.fragment_pager_foot, null);
        mMapView = (MapView) mView.findViewById(R.id.route_map);
        mMapView.onCreate(mSavedInstanceState);
        mAMap = mMapView.getMap();
        init();
        return mView;
    }

    @Override
    public Object getData() {
        return ContentPage.PageState.STATE_LOADED;
    }
}

