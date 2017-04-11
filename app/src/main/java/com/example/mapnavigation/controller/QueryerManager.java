package com.example.mapnavigation.controller;

import android.content.Context;
import android.provider.SyncStateContract;
import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.example.mapnavigation.utils.ToastUtils;

import static android.content.ContentValues.TAG;
import static com.example.mapnavigation.utils.Constants.AMAP_SEARCH_SUCCESS_CODE;

/**
 * 高德地图的搜索组件,封装高德地图的搜索服务
 * Created by zzg on 17-4-10.
 */

public class QueryerManager implements GeocodeSearch.OnGeocodeSearchListener{

    // ----------------fields----------------
    private Context mContext;

    // 地图搜索回调
    private OnAMapQueryListener mOnAMapQueryListener;

    // -------------------构造函数-------------------
    public QueryerManager(Context context){
        mContext = context;
    }

    // ------------------------ 搜索-----------------------------
    /**
     * 根据经纬度获取其地址信息,逆地理编码查询地址信息
     * @param latlng        地理坐标
     * @param radius        查询范围
     */
    public void searchAddress(LatLng latlng, float radius) {

        // 创建逆地理编码
        RegeocodeQuery mquery = new RegeocodeQuery(
                new LatLonPoint(latlng.latitude, latlng.longitude), radius, GeocodeSearch.AMAP);
        // 创建地理编码（逆地理编码）查询类
        GeocodeSearch msearch = new GeocodeSearch(mContext);
        // 为查询类设置监听器，查询后回调
        msearch.setOnGeocodeSearchListener(this);
        // 查询
        msearch.getFromLocationAsyn(mquery);
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

    // -------------------------外部访问接口----------------------
    /**
     * 为了使在外部对结果进行处理
     * */
    public interface OnAMapQueryListener {
        // 逆地理搜索结果回调
        void onRegeocodeSearched(RegeocodeAddress address);

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
