package com.example.mapnavigation.controller;

import android.content.Context;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviGuide;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.example.mapnavigation.utils.Constants.NAVI_MODE_GPS;

/**
 * Created by zzg on 17-5-8.
 */

public class AMapNaviManager implements AMapNaviListener {

    public Context mContext;
    public AMapNavi mAMapNavi;
    public AMapNaviView mAMapNaviView;
    public int mNaviMode;
    public boolean isNaviReady;
    /* 标记正在导航 . */
    private AtomicBoolean isNaving = new AtomicBoolean(false);
    /* 标记初次导航 . */
    private boolean fristNav = true;
    private List<AMapNaviGuide> mNaviGuides;

    /* 导航路径信息 . */
    private AMapNaviPath mNaviPath;

    private OnNavingListener mOnNavingListener;


    // 起点
    private NaviLatLng mSrcLocation;
    // 终点
    private NaviLatLng mDestLocation;

    public AMapNaviManager(Context context, AMapNaviView aMapNaviView) {
        mContext = context;
        mAMapNaviView = aMapNaviView;

    }

    public void preNavi(int naviMode){
        mNaviMode = naviMode;
        // 获取AMapNavi实例
        mAMapNavi = AMapNavi.getInstance(mContext);

        // 一般人步行速度 5km/h
        mAMapNavi.setEmulatorNaviSpeed(5);
        if (NAVI_MODE_GPS == naviMode){
            mAMapNavi.startGPS();
        }
        // 添加监听回调，用于处理算路成功
        mAMapNavi.addAMapNaviListener(this);
    }

    /**
     * 更新导航信息回调
     *
     * @param mOnNavingListener
     */
    public void setOnNavingListener(OnNavingListener mOnNavingListener) {
        this.mOnNavingListener = mOnNavingListener;
    }


    // -------------------导航事件回调-----------------------
    @Override
    public void onInitNaviFailure() {

    }

    @Override
    public void onInitNaviSuccess() {
        // 路径规划
        mAMapNavi.calculateWalkRoute(mSrcLocation, mDestLocation);
    }

    @Override
    public void onStartNavi(int i) {

    }

    @Override
    public void onTrafficStatusUpdate() {

    }

    @Override
    public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

    }

    @Override
    public void onGetNavigationText(int i, String s) {

    }

    @Override
    public void onEndEmulatorNavi() {

    }

    @Override
    public void onArriveDestination() {

    }

    /**
     * 步行或者驾车路径规划成功后的回调
     */
    @Override
    public void onCalculateRouteSuccess() {
        String calculateResult = "路径计算就绪";
        //mVoiceController.startSpeaking(calculateResult);
        // 初始化状态
        isNaviReady = true;
        isNaving.set(false);
        fristNav = true;

        // 获得规划好的路径 概览
        mNaviGuides = mAMapNavi.getNaviGuideList();
        // 详情
        mNaviPath = mAMapNavi.getNaviPath();

        if (null != mOnNavingListener && null != mNaviPath) {
            mOnNavingListener.onCalculateRouteSuccess(mNaviGuides, mNaviPath);
        }
    }

    @Override
    public void onCalculateRouteFailure(int i) {

    }

    @Override
    public void onReCalculateRouteForYaw() {

    }

    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    @Override
    public void onArrivedWayPoint(int i) {

    }

    @Override
    public void onGpsOpenStatus(boolean b) {

    }

    @Override
    public void onNaviInfoUpdate(NaviInfo naviInfo) {

    }

    @Override
    public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {

    }

    @Override
    public void hideCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

    }

    @Override
    public void hideLaneInfo() {

    }

    @Override
    public void onCalculateMultipleRoutesSuccess(int[] ints) {

    }

    @Override
    public void notifyParallelRoad(int i) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }

    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    public interface OnNavingListener {
        // 导航信息更新
        void onNaviInfoUpdated(NaviInfo info);

        // 到达目的地
        void onArriveDestination();

        // 路径规划成功
        void onCalculateRouteSuccess(List<AMapNaviGuide> mNaviGuides,
                                     AMapNaviPath mNaviPath);

        // 路径规划失败
        void onCalculateRouteFailure();

        // 重新规划路径
        void onReCalculateRoute();

        // 导航指引信息
        void onGetNavigationText(String text);

    }
}
