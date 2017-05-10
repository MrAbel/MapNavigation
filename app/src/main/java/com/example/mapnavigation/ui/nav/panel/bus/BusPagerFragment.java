package com.example.mapnavigation.ui.nav.panel.bus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.BusRouteListAdapter;
import com.example.mapnavigation.adapter.RouteAdapter;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.di.module.RouteHistory;
import com.example.mapnavigation.ui.ContentPage;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzg on 17-4-12.
 */

public class BusPagerFragment extends BaseFragment{

    private View mView;
    private MapView mMapView;
    private AMap mAMap;

    private static LinearLayout mBusResultLayout;
    private static RecyclerView mBusResultList;
    private static RecyclerView mHistoryRouteList;
    private BusRouteResult mBusRouteResult;
    private List<RouteHistory> mRouteHistoriesList;
    private List<BusPath> list = new ArrayList<BusPath>();
    private static BusRouteListAdapter mBusRouteListAdapter;


    private void initView(){
        mMapView = (MapView) mView.findViewById(R.id.route_map);
        mBusResultLayout = (LinearLayout) mView.findViewById(R.id.bus_result);
        mBusResultList = (RecyclerView) mView.findViewById(R.id.bus_result_list);
        mHistoryRouteList = (RecyclerView) mView.findViewById(R.id.pull_refresh_list);

    }

    private void init(){
        initView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        initdata();
        mHistoryRouteList.setLayoutManager(layoutManager);
        RouteAdapter adapter = new RouteAdapter(mRouteHistoriesList);
        mHistoryRouteList.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(mContext);
        mBusResultList.setLayoutManager(layoutManager);
        //mBusRouteListAdapter = new BusRouteListAdapter(list);
        //mBusResultList.setAdapter(mBusRouteListAdapter);

    }
    private void initdata() {
        mRouteHistoriesList = DataSupport.findAll(RouteHistory.class);
    }

    public static void updata(List<BusPath> list, BusRouteResult result){
        mBusResultLayout.setVisibility(View.VISIBLE);
        mHistoryRouteList.setVisibility(View.GONE);
        mBusRouteListAdapter = new BusRouteListAdapter(list, result);
        mBusResultList.setAdapter(mBusRouteListAdapter);
    }
    @Override
    public View getLoadedView() {
        mView = View.inflate(mContext, R.layout.fragment_pager_foot, null);
//
//        mMapView = (MapView) mView.findViewById(R.id.route_map);
//        mMapView.onCreate(mSavedInstanceState);
//        mAMap = mMapView.getMap();
        init();
        return mView;
    }

    @Override
    public Object getData() {

        return ContentPage.PageState.STATE_LOADED;
    }

    public static BusPagerFragment newInstance() {
        BusPagerFragment fragment = new BusPagerFragment();
        return fragment;
    }
}
