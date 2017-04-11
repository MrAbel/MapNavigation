package com.example.mapnavigation.controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.mapnavigation.MapApplication;
import com.example.mapnavigation.R;
import com.example.mapnavigation.data.db.LocationPoint;

import java.sql.Date;
import java.text.SimpleDateFormat;

import static android.R.id.progress;

/**
 * LocationManager
 * 地图定位功能管理
 * Created by zzg on 17-4-5.
 */

public class LocationManager implements  AMapLocationListener{

    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    // 定位服务类(此类提供单次定位、持续定位、最后位置相关功能)
    public AMapLocationClient mLocationClient;
    // 定位参数设置类
    public AMapLocationClientOption mLocationOption = null;

    private LocationSource.OnLocationChangedListener mListener;
    // 当前位置的维度
    private double mLatitude;
    // 当前位置的经度
    private double mLongitude;
    private AMap mAMap;
    // 当前位置信息
    private LocationPoint mLocationPoint;

    private LatLng mLatLng;

    public LocationManager(AMap aMap){
        mAMap = aMap;
    }

    /**
     * 为定位做初始化工作，包括设置定位参数，设置监听器
     */
    public void initLocate(){

        // 初始化定位服务类
        mLocationClient = new AMapLocationClient(MapApplication.getContext());
        // 设置定位回调监听
        mLocationClient.setLocationListener(this);
        // -------------------初始化定位参数----------------
        mLocationOption = new AMapLocationClientOption();
        //　设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //　获取一次定位结果，该方法默认为false。
        mLocationOption.setOnceLocation(true);
        //mLocationOption.setNeedAddress(true);
        //　获取最近3s内精度最高的一次定位结果：
        //　设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果
        //　如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        // -------------------初始化定位参数----------------
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);

    }

    /**
     * 定位
     */
    public void startLocation(){
        // 定位初始化
        initLocate();
        // 设置蓝点样式
        setupLocationStyle();
        // 启动定位
        mLocationClient.startLocation();
    }

    /**
     * 停止定位
     */
    public void stopLocation(){
        mLocationClient.stopLocation();
    }

    /**
     * 设置自定义定位蓝点
     */
    private void setupLocationStyle(){

        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();

        // 连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);

        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        // 自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        mAMap.setMyLocationStyle(myLocationStyle);
    }

    /**
     * 定位回调函数
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {

                mLatLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());

                // 将获取的信息存为当前位置
                LocationPoint.AMapLocationToLocationInfo(aMapLocation, mLocationPoint);

                //定位成功回调信息，设置相关消息

            }else {

                //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }

        //获取定位时间
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        Date date = new Date(aMapLocation.getTime());
//        df.format(date);
    }

    public LatLng getmLatLng(){
        return mLatLng;
    }
}
