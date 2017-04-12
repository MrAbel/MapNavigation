package com.example.mapnavigation.controller;

import android.content.Context;
import android.graphics.Color;
import android.provider.SyncStateContract;
import android.util.Log;

import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.District;
import com.example.mapnavigation.utils.ToastUtils;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static com.example.mapnavigation.utils.Constants.AMAP_SEARCH_SUCCESS_CODE;

/**
 * 高德地图的搜索组件,封装高德地图的搜索服务包括地理编码查询、逆地理编码查询、行政区查询等
 * Created by zzg on 17-4-10.
 */

public class QueryerManager implements GeocodeSearch.OnGeocodeSearchListener,
        DistrictSearch.OnDistrictSearchListener, PoiSearch.OnPoiSearchListener {

    // ----------------fields----------------
    private Context mContext;

    // 地图搜索回调
    private OnAMapQueryListener mOnAMapQueryListener;

    // -------------------构造函数-------------------
    public QueryerManager(Context context){
        mContext = context;
    }

    // ------------------------查询搜索-----------------------------
    /**
     * 根据经纬度获取其地址信息,逆地理编码查询地址信息
     * @param latlng        地理坐标
     * @param radius        查询范围
     */
    public void searchAddress(LatLng latlng, float radius) {

        // 创建逆地理编码条件
        RegeocodeQuery query = new RegeocodeQuery(
                new LatLonPoint(latlng.latitude, latlng.longitude), radius, GeocodeSearch.AMAP);
        // 创建地理编码（逆地理编码）查询类
        GeocodeSearch search = new GeocodeSearch(mContext);
        // 为查询类设置监听器，查询后回调
        search.setOnGeocodeSearchListener(this);
        // 查询
        search.getFromLocationAsyn(query);
    }

    /**
     * 根据行政区关键字搜索行政区边界
     * @param keyword
     */
    public void searchDistrictBoundary(String keyword){

        // 创建区域搜索类
        DistrictSearch districtSearch = new DistrictSearch(mContext);
        //　创建搜索条件类
        DistrictSearchQuery districtSearchQuery = new DistrictSearchQuery();
        // 传入关键字
        districtSearchQuery.setKeywords(keyword);
        // 返回边界值
        districtSearchQuery.setShowBoundary(true);
        // 为搜索类设置搜索条件
        districtSearch.setQuery(districtSearchQuery);
        // 为搜索类设置回调监听
        districtSearch.setOnDistrictSearchListener(this);
        // 异步查询
        districtSearch.searchDistrictAsyn();
    }

    /**
     * 根据关键字查询Poi
     * @param keyword
     */
    public void searchPoi(String keyword){
        // 构造Poi查询条件
        PoiSearch.Query query = new PoiSearch.Query(keyword, "");
        // 设置每页最多返回多少条poi　item
        query.setPageSize(10);
        //　设置查询页码
        //query.setPageNum(currentPage);
        // 创建Poi查询对象
        PoiSearch poiSearch = new PoiSearch(mContext, query);
        // 为Poi查询设置回调监听
        poiSearch.setOnPoiSearchListener(this);
        // 异步查询
        poiSearch.searchPOIAsyn();
    }

    // --------------GeocodeSearch.OnGeocodeSearchListener-------------
    /**
     * 逆地理查询时回调（根据经纬查地址）
     * @param regeocodeResult
     * @param i
     */
    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

        if (i == AMAP_SEARCH_SUCCESS_CODE) {
            // 如果搜索返回成功
            if (regeocodeResult != null && regeocodeResult.getRegeocodeAddress() != null
                    && regeocodeResult.getRegeocodeAddress().getFormatAddress() != null){
                // 获取逆地理编码返回的格式化地址。
                RegeocodeAddress address = regeocodeResult.getRegeocodeAddress();
                // 设置回调，在外部进行处理
                if (mOnAMapQueryListener != null) {
                    mOnAMapQueryListener.onRegeocodeSearched(address);
                }
            }
        } else {
            // 搜索失败
            if (mOnAMapQueryListener != null) {
                mOnAMapQueryListener.onRegeocodeSearched(null);
            }
            ToastUtils.showShort("未找到匹配结果");
        }
    }

    /**
     * 地理查询回调（根据地址查经纬）
     * @param geocodeResult
     * @param i
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    // --------------DistrictSearch.OnDistrictSearchListener-------------

    /**
     * 返回District（行政区划）异步处理的结果
     * @param districtResult
     */
    @Override
    public void onDistrictSearched(DistrictResult districtResult) {

        if (districtResult == null || districtResult.getDistrict()==null) {
            return;
        }
        // 通过ErrorCode判断是否成功
        if ((districtResult.getAMapException() != null) &&
                (districtResult.getAMapException().getErrorCode() ==
                        AMapException.CODE_AMAP_SUCCESS)) {
            ArrayList<DistrictItem> items = districtResult.getDistrict();
            // 设置回调，在外部进行处理
            if (mOnAMapQueryListener != null) {
                mOnAMapQueryListener.onDistrictSearched(items);
            }
        } else {
            // 搜索失败
            if (mOnAMapQueryListener != null) {
                mOnAMapQueryListener.onDistrictSearched(null);
            }

            if(districtResult.getAMapException() != null){
                ToastUtils.showError(districtResult.getAMapException().getErrorCode());
            }
        }
    }


    // --------------PoiSearch.OnPoiSearchListener-------------
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {

    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    // -------------------------外部访问接口----------------------
    /**
     * 为了使在外部对结果进行处理
     * */
    public interface OnAMapQueryListener {
        // 逆地理搜索结果处理回调
        void onRegeocodeSearched(RegeocodeAddress address);
        //　行政区划搜索结果处理回调
        void onDistrictSearched(ArrayList<DistrictItem> districtItems);
    }

    // ----------------------setter and getter-----------------------
    /**
     * 设置高德地图搜索监听
     * @param mOnAMapQueryListener
     */
    public void setmOnAMapQueryListener(OnAMapQueryListener mOnAMapQueryListener) {
        this.mOnAMapQueryListener = mOnAMapQueryListener;
    }
}
