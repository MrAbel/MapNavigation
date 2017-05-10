package com.example.mapnavigation.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.amap.api.location.AMapLocation;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.example.mapnavigation.utils.Constants;
import com.example.mapnavigation.utils.ToastUtils;
import java.util.ArrayList;
import static com.example.mapnavigation.utils.Constants.AMAP_SEARCH_SUCCESS_CODE;

/**
 * 高德地图的搜索组件,封装高德地图的搜索服务包括地理编码查询、逆地理编码查询、行政区查询、路线查询等
 * Created by zzg on 17-4-10.
 */

public class QueryerManager implements GeocodeSearch.OnGeocodeSearchListener,
        DistrictSearch.OnDistrictSearchListener, PoiSearch.OnPoiSearchListener,
        RouteSearch.OnRouteSearchListener{

    // ----------------fields----------------
    private Context mContext;

    // 地图搜索回调
    private OnAMapQueryListener mOnAMapQueryListener;
    private OnAPoiSearchListener mOnAPoiSearchListener;
    private boolean isNearbySearch = false;

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
        // 异步查询
        search.getFromLocationAsyn(query);
    }

    /**
     * 根据地址信息获取其地址信息,地理编码查询经纬度
     * @param siteName
     * @param city
     */
    public void searchLatlon(String siteName, String city){

        // 创建地理编码地址查询条件
        GeocodeQuery query =  new GeocodeQuery(siteName, city);
        // 创建地理编码（逆地理编码）查询类
        GeocodeSearch search = new GeocodeSearch(mContext);
        // 为查询类设置监听器，查询后回调
        search.setOnGeocodeSearchListener(this);
        // 异步查询
        search.getFromLocationNameAsyn(query);
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


    public void searchNearbyKeyword(AMapLocation location, String keyword, String type, int radius){

        isNearbySearch = true;
        PoiSearch.Query query = new PoiSearch.Query(keyword, type, location.getCityCode());

        query.setPageSize(10);
        query.setPageNum(10);
        PoiSearch search = new PoiSearch(mContext, query);
        // 设置周边搜索的中心点以及区域
        search.setBound(new PoiSearch.SearchBound(new LatLonPoint(
                location.getLatitude(), location.getLongitude()), radius));
        // 设置数据返回的监听器
        search.setOnPoiSearchListener(this);
        // 开始搜索
        search.searchPOIAsyn();

    }

    /**
     * 根据关键字查询Poi
     * @param keyword
     */
    public void searchPoi(String keyword){
        isNearbySearch = false;
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

    /**
     * 根据提供的开始，结束节点查询路线，根据模式来确定是什么类型的路线（步行、驾车、公交等）
     * @param begin
     * @param end
     * @param type
     */
    public void searchRoute(LatLonPoint begin, LatLonPoint end, int type){

        // 创建起始对象
        RouteSearch.FromAndTo fromAndTo =
                new RouteSearch.FromAndTo(begin, end);
        // 创建路线查询类
        RouteSearch routeSearch = new RouteSearch(mContext);
        // 为路线查询设置监听器
        routeSearch.setRouteSearchListener(this);
        // 根据选择设置搜索参数，并且发起查询
        switch (type){
            case Constants.WALK_ROUTE:{
                RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo);
                routeSearch.calculateWalkRouteAsyn(query);
                break;
            }
            case Constants.BUS_ROUTE:{
                // fromAndTo包含路径规划的起点和终点，RouteSearch.BusLeaseWalk表示公交查询模式
                // 第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算,1表示计算
                RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(
                        fromAndTo, RouteSearch.BusLeaseWalk, "长沙",0);
                //query.setCityd("长沙");
                //query.setCityd("027");//终点城市区号
                routeSearch.calculateBusRouteAsyn(query);//开始规划路径
                break;
            }
            case Constants.CAR_ROUTE: {
                // fromAndTo包含路径规划的起点和终点，drivingMode表示驾车模式
                // 第三个参数表示途经点（最多支持16个），第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路
                RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(
                        fromAndTo, RouteSearch.DRIVING_SINGLE_DEFAULT, null, null, "");
                routeSearch.calculateDriveRouteAsyn(query);
                break;
            }
            default:
                break;
        }
    }

    public void searchWalkRoute(LatLonPoint begin, LatLonPoint end){

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
        if (i == AMAP_SEARCH_SUCCESS_CODE) {
            // 如果搜索返回成功
            if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                    && geocodeResult.getGeocodeAddressList().size() > 0){
                // 设置回调，在外部进行处理
                if (mOnAMapQueryListener != null) {
                    mOnAMapQueryListener.onGeocodeSearched(geocodeResult);
                }
            }
        } else {
            // 搜索失败
            if (mOnAMapQueryListener != null) {
                mOnAMapQueryListener.onGeocodeSearched(null);
            }
            ToastUtils.showShort("未找到匹配结果");
        }
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
        if (Constants.AMAP_SEARCH_SUCCESS_CODE == i){
            if (poiResult !=  null && poiResult.getQuery() != null) {
                // 对外回调
                if (mOnAPoiSearchListener != null) {
//                    if (isNearbySearch){
//                        mOnAPoiSearchListener.onNearbyPoiSearch(poiResult);
//                    }else{
                        mOnAPoiSearchListener.onPoiSearched(poiResult);
//                    }

                }
            }
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {
        //ToastUtils.showShort(poiItem.getCityCode());
    }
    // --------------RouteSearch.OnRouteSearchListener-------------
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

        if (i == Constants.AMAP_SEARCH_SUCCESS_CODE){
            if (busRouteResult != null && busRouteResult.getPaths() != null) {
                // 通过回调处理结果
                if (mOnAMapQueryListener != null) {
                    mOnAMapQueryListener.onBusRouteSearched(busRouteResult);
                }else if (busRouteResult != null && busRouteResult.getPaths() == null){
                    ToastUtils.showShort("bus line search failed!");
                }
            } else {
                ToastUtils.showShort("bus line search failed!");
            }
        }else{
            ToastUtils.showShort("bus line search failed!");
        }
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
        //aMap.clear();// 清理地图上的所有覆盖物
        if (i == Constants.AMAP_SEARCH_SUCCESS_CODE) {
            if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                if (driveRouteResult.getPaths().size() > 0) {

                    // 通过回调处理结果
                    if (mOnAMapQueryListener != null) {
                        mOnAMapQueryListener.onDriveRouteSearched(driveRouteResult);
                    }
                } else if (driveRouteResult != null
                        && driveRouteResult.getPaths() == null) {
                    ToastUtils.showShort("没有找到路线");
                }
            } else {
                ToastUtils.showShort("没有找到路线");
            }
        }

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {
        if (i == Constants.AMAP_SEARCH_SUCCESS_CODE){
            if (walkRouteResult != null && walkRouteResult.getPaths() != null) {
                if (walkRouteResult.getPaths().size() > 0) {
                    // 通过回调处理结果
                    if (mOnAMapQueryListener != null) {
                        mOnAMapQueryListener.onWalkRouteSearched(walkRouteResult);
                    }
                } else if (walkRouteResult != null
                        && walkRouteResult.getPaths() == null) {
                    ToastUtils.showShort("没有找到路线");
                }
            } else {
                ToastUtils.showShort("没有找到路线");
            }
        }

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    // -------------------------外部访问接口----------------------

    /**
     * 为了使在外部对结果进行处理
     * */
    public interface OnAMapQueryListener {
        // 逆地理搜索结果处理回调
        void onRegeocodeSearched(RegeocodeAddress address);
        // 地理搜索结果处理回调
        void onGeocodeSearched(GeocodeResult geocodeResult);
        //　行政区划搜索结果处理回调
        void onDistrictSearched(ArrayList<DistrictItem> districtItems);
        // 步行规划搜索结果回调
        void onWalkRouteSearched(WalkRouteResult walkRouteResult);
        // 汽车规划搜索结果回调
        void onDriveRouteSearched(DriveRouteResult driveRouteResult);
        // 公交车路线规划结果回调
        void onBusRouteSearched(BusRouteResult busRouteResult);
    }

    public interface OnAPoiSearchListener {
        // 周边Poi搜索
//        void onNearbyPoiSearch(PoiResult result);
        // Poi搜索
        void onPoiSearched(PoiResult result);
    }

    // ----------------------setter and getter-----------------------
    /**
     * 设置高德地图搜索监听
     * @param mOnAMapQueryListener
     */
    public void setmOnAMapQueryListener(OnAMapQueryListener mOnAMapQueryListener) {
        this.mOnAMapQueryListener = mOnAMapQueryListener;
    }

    public void setmAPoiSearchListener(OnAPoiSearchListener mOnAPoiSearchListener){
        this.mOnAPoiSearchListener = mOnAPoiSearchListener;
    }

}
