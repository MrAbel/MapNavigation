package com.example.mapnavigation.ui.activtiy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnInfoWindowClickListener;
import com.amap.api.maps.AMap.OnMapClickListener;
import com.amap.api.maps.AMap.OnMapLoadedListener;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.services.route.BusPath;

import com.amap.api.services.route.BusRouteResult;
import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.BusRouteDetailListAdapter;
import com.example.mapnavigation.overlay.BusRouteOverlay;
import com.example.mapnavigation.utils.AMapUtils;

public class BusRouteDetailActivity extends Activity implements OnMapLoadedListener,
		OnMapClickListener, InfoWindowAdapter, OnInfoWindowClickListener, OnMarkerClickListener {
	private AMap aMap;
	private MapView mapView;
	private BusPath mBuspath;
	private BusRouteResult mBusRouteResult;
	private TextView mTitle, mTitleBusRoute, mDesBusRoute;
	private RecyclerView mBusStepList;
	private BusRouteDetailListAdapter mBusRouteDetailListAdapter;
	private LinearLayout mBusMap, mBuspathview;
	private BusRouteOverlay mBusrouteOverlay;
	private Bundle mSavedInstanceState;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_detail);
		mSavedInstanceState = savedInstanceState;
		getIntentData();
		init();
	}

	/**
	 * 获取Intent中携带的数据
	 */
	private void getIntentData() {
		Intent intent = getIntent();
		if (intent != null) {
			mBuspath = intent.getParcelableExtra("bus_path");
			mBusRouteResult = intent.getParcelableExtra("bus_result");
		}
	}

	private void initView(){
		mapView = (MapView) findViewById(R.id.route_map);
		mapView.onCreate(mSavedInstanceState);// 此方法必须重写
		mTitle = (TextView) findViewById(R.id.title_center);
		mTitleBusRoute = (TextView) findViewById(R.id.firstline);
		mDesBusRoute = (TextView) findViewById(R.id.secondline);
		mBusMap = (LinearLayout)findViewById(R.id.title_map);
		mBuspathview = (LinearLayout)findViewById(R.id.bus_path);
		mBusStepList = (RecyclerView) findViewById(R.id.bus_step_list);
	}

	/**
	 * 初始化工作
	 */
	private void init() {
		initView();
		if (aMap == null) {
			aMap = mapView.getMap();	
		}
		registerListener();
		
		mTitle.setText("公交路线详情");
		String dur = AMapUtils.getFriendlyTime((int) mBuspath.getDuration());
		String dis = AMapUtils.getFriendlyLength((int) mBuspath.getDistance());
		mTitleBusRoute.setText(dur + "(" + dis + ")");
		int taxiCost = (int) mBusRouteResult.getTaxiCost();
		mDesBusRoute.setText("打车约"+taxiCost+"元");
		mDesBusRoute.setVisibility(View.VISIBLE);
		mBusMap.setVisibility(View.VISIBLE);
		configureListView();
	}

	/**
	 * 注册监听器
	 */
	private void registerListener() {
		aMap.setOnMapLoadedListener(this);
		aMap.setOnMapClickListener(this);
		aMap.setOnMarkerClickListener(this);
		aMap.setOnInfoWindowClickListener(this);
		aMap.setInfoWindowAdapter(this);
	}

	/**
	 * 配置ListView
	 */
	private void configureListView() {
		LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
		//initdata();
		mBusStepList.setLayoutManager(layoutManager);


		mBusRouteDetailListAdapter = new BusRouteDetailListAdapter(mBuspath.getSteps());
		mBusStepList.setAdapter(mBusRouteDetailListAdapter);

	}
	
	public void onBackClick(View view) {
		this.finish();
	}
	
	public void onMapClick(View view) {
		mBuspathview.setVisibility(View.GONE);
		mBusMap.setVisibility(View.GONE);
		mapView.setVisibility(View.VISIBLE);
		aMap.clear();// 清理地图上的所有覆盖物
		mBusrouteOverlay = new BusRouteOverlay(this, aMap,
				mBuspath, mBusRouteResult.getStartPos(),
				mBusRouteResult.getTargetPos());
		mBusrouteOverlay.removeFromMap();

	}

	@Override
	public void onMapLoaded() {
		if (mBusrouteOverlay != null) {
			mBusrouteOverlay.addToMap();
			mBusrouteOverlay.zoomToSpan();
		}
	}

	@Override
	public void onMapClick(LatLng arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public View getInfoContents(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onInfoWindowClick(Marker arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
