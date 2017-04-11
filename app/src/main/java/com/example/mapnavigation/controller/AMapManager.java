package com.example.mapnavigation.controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Poi;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.example.mapnavigation.MapApplication;
import com.example.mapnavigation.R;
import com.example.mapnavigation.utils.ToastUtils;

import static com.example.mapnavigation.utils.Constants.AMAP_INIT_ZOOM_VALUE;
import static com.example.mapnavigation.utils.Constants.AMAP_REGEOCODE_RADIUS;

/**
 * Created by zzg on 17-4-5.
 */

public class AMapManager implements AMap.OnPOIClickListener, AMap.OnMapClickListener,
        AMap.OnMarkerClickListener, QueryerManager.OnAMapQueryListener {

    // ----------------fields----------------
    // 当前上下文环境
    private Context mContext;
    // 地图操作组件
    private AMap mAMap;
    // 地图的UiSetting
    private UiSettings mUiSettings;
    // 地图搜索组件
    private QueryerManager mQueryerManager;
    // 标识搜索到的Marker
    private Marker mQueryMarker;
    // 标识当前点击的pPoi
    private Poi mPoi;

    // ---------------构造函数-----------------------
    public AMapManager(Context context, AMap aMap){
        init(context, aMap);
        regiesterListener();

    }

    // --------------普通函数------------------------
    /**
     * 初始化域字段
     * @param context
     * @param aMap
     */
    private void init(Context context, AMap aMap){
        mAMap = aMap;
        mContext = context;
        mUiSettings = aMap.getUiSettings();
        mQueryerManager = new QueryerManager(mContext);
        initAMapUI();
    }

    /**
     * 初始化地图控件的显示
     */
    private void initAMapUI(){

        // 设置初始缩放比例
        mAMap.animateCamera(CameraUpdateFactory.zoomTo(AMAP_INIT_ZOOM_VALUE));
        // 定位按钮可用
        mAMap.setMyLocationEnabled(true);
        // 设置默认定位按钮是否显示
        mUiSettings.setMyLocationButtonEnabled(true);
        // 设置指南针可见
        mUiSettings.setCompassEnabled(true);
        // 设置比例尺控件可见
        mUiSettings.setScaleControlsEnabled(true);
        // 设置缩放控件的位置
        mUiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER );
    }

    /**
     * 注册监听函数
     */
    private void regiesterListener(){
        // 为POI设置点击事件监听
        mAMap.setOnPOIClickListener(this);
        // 为Map设置点击事件监听
        mAMap.setOnMapClickListener(this);
        // 为Marker设置点击事件监听
        mAMap.setOnMarkerClickListener(this);
        // 为地图的查询组件设置回调
        mQueryerManager.setmOnAMapQueryListener(this);
    }

    /**
     * 将地图移动到当前所在的位置
     */
    public void moveToLocation(double latitude, double longitude) {

    }

    /**
     * 将地图移动到当前所在的位置
     */
    public void moveToLocation(LatLng mLatLng) {
        if (null == mLatLng)
            return;

        //mAMap.animateCamera(CameraUpdateFactory.newLatLngZoom(mLatLng, zoom));
    }

    // ----------------AMap.OnPOIClickListener---------------------
    /**
     * 处理POI的点击事件
     * @param poi
     */
    @Override
    public void onPOIClick(Poi poi) {
        //ToastUtils.showLong(poi.getName());
        mPoi = poi;
        mQueryerManager.searchAddress(poi.getCoordinate(), AMAP_REGEOCODE_RADIUS);
//        mAMap.clear();
//        Log.i("MY", poi.getPoiId()+poi.getName());
//        MarkerOptions markOptiopns = new MarkerOptions();
//        markOptiopns.position(poi.getCoordinate());
//        TextView textView = new TextView(MapApplication.getContext());
//        textView.setText("到"+poi.getName()+"去");
//        textView.setGravity(Gravity.CENTER);
//        textView.setTextColor(Color.BLACK);
//        //textView.setBackgroundResource(R.drawable.custom_info_bubble);
//        markOptiopns.icon(BitmapDescriptorFactory.fromView(textView));
//        mAMap.addMarker(markOptiopns);
    }

    // ----------------AMap.OnMapClickListener---------------------
    /**
     * 处理地图的点击事件
     * @param latLng
     */
    @Override
    public void onMapClick(LatLng latLng) {
        ToastUtils.showShort("Map click");
    }

    // ----------------AMap.OnMarkerClickListener---------------------
    /**
     * 处理marker的点击事件
     * @param marker
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
        LatLng latLng = marker.getPosition();
        ToastUtils.showShort("aaa");
        return false;
    }

    // ----------------QueryerManager.OnAMapQueryListener---------------------
    @Override
    public void onRegeocodeSearched(RegeocodeAddress address) {

        if (address == null){
            return ;
        }

        if (mQueryMarker == null) {
            // 创建Marker选项类
            MarkerOptions options = new MarkerOptions();
            // 在地图上添加Marker
            mQueryMarker = mAMap.addMarker(options);
        }

        // 为Marker设置参数
        mQueryMarker.setPosition(mPoi.getCoordinate());
        mQueryMarker.setTitle(mPoi.getName());
        mQueryMarker.setSnippet(address.getFormatAddress());
        mQueryMarker.setVisible(true);
        mQueryMarker.showInfoWindow();

//        // 标记当前标记
//        mCurrentMarker = mQueryMarker;
//        // 传出数据
//        if (null != mMapStatusLinstener) {
//            mMapStatusLinstener.onMapStatusChanged(mCurrentMarker,
//                    mCurrentMarker.getPosition(),
//                    AMapStatus.onRegeocodeSearched);
//        }
    }
}
