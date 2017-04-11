package com.example.mapnavigation.data.db;

import com.amap.api.location.AMapLocation;

/**
 * LocationPoint
 * 保存当前位置信息
 * Created by zzg on 17-4-5.
 */

public class LocationPoint {

    // 纬度
    private double mLatitude;

    // 经度
    private double mLongitude;

    // 海拔
    private double mAltitude;

    // 速度
    private float mSpeed;

    // 方向角
    private float mBearing;

    // 地址描述
    private String mAddress;

    // 国家
    private String mCountry;

    // 省份
    private String mProvince;

    // 市
    private String mCity;

    // 城区
    private String mDistrict;

    // 街道
    private String mStreet;

    // 门牌号
    private String mStreetNum;

    // 城市编码
    private String mCityCode;

    // 区编码
    private String mAdCode;

    // POI名称
    private String mPOIName;


    //-----------------------getter and setter---------------------------------

    public String getmDistrict() {
        return mDistrict;
    }

    public double getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public double getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public void setmDistrict(String mDistrict) {

        this.mDistrict = mDistrict;
    }

    public double getmAltitude() {
        return mAltitude;
    }

    public void setmAltitude(double mAltitude) {
        this.mAltitude = mAltitude;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public float getmSpeed() {
        return mSpeed;
    }

    public void setmSpeed(float mSpeed) {
        this.mSpeed = mSpeed;
    }

    public float getmBearing() {
        return mBearing;
    }

    public void setmBearing(float mBearing) {
        this.mBearing = mBearing;
    }

    public String getmCountry() {
        return mCountry;
    }

    public void setmCountry(String mCountry) {
        this.mCountry = mCountry;
    }

    public String getmProvince() {
        return mProvince;
    }

    public void setmProvince(String mProvince) {
        this.mProvince = mProvince;
    }

    public String getmCity() {
        return mCity;
    }

    public void setmCity(String mCity) {
        this.mCity = mCity;
    }


    public String getmStreet() {
        return mStreet;
    }

    public void setmStreet(String mStreet) {
        this.mStreet = mStreet;
    }

    public String getmStreetNum() {
        return mStreetNum;
    }

    public void setmStreetNum(String mStreetNum) {
        this.mStreetNum = mStreetNum;
    }

    public String getmCityCode() {
        return mCityCode;
    }

    public void setmCityCode(String mCityCode) {
        this.mCityCode = mCityCode;
    }

    public String getmAdCode() {
        return mAdCode;
    }

    public void setmAdCode(String mAdCode) {
        this.mAdCode = mAdCode;
    }

    public String getmPOIName() {
        return mPOIName;
    }

    public void setmPOIName(String mPOIName) {
        this.mPOIName = mPOIName;
    }
    //-----------------------getter and setter---------------------------------

    /**
     * 从AMapLocation中提取信息
     * @param aMapLocation
     * @param locationPoint
     */
    public static void AMapLocationToLocationInfo(AMapLocation aMapLocation,
                                              LocationPoint locationPoint) {
        if (aMapLocation == null || locationPoint == null){
            throw new NullPointerException();
        }

        locationPoint.setmDistrict(aMapLocation.getDistrict());
        locationPoint.setmAdCode(aMapLocation.getAdCode());
        locationPoint.setmAddress(aMapLocation.getAddress());
        locationPoint.setmAltitude(aMapLocation.getAltitude());
        locationPoint.setmBearing(aMapLocation.getBearing());
        locationPoint.setmCity(aMapLocation.getCity());
        locationPoint.setmCityCode(aMapLocation.getCityCode());
        locationPoint.setmCountry(aMapLocation.getCountry());
        locationPoint.setmLatitude(aMapLocation.getLatitude());
        locationPoint.setmLongitude(aMapLocation.getLongitude());
        locationPoint.setmPOIName(aMapLocation.getPoiName());
        locationPoint.setmProvince(aMapLocation.getProvince());
        locationPoint.setmSpeed(aMapLocation.getSpeed());
        locationPoint.setmStreet(aMapLocation.getStreet());
        locationPoint.setmStreetNum(aMapLocation.getStreetNum());

    }
}
