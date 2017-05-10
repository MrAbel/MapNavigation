package com.example.mapnavigation;

import android.app.Application;
import android.content.Context;


import com.example.mapnavigation.controller.AMapManager;
import com.example.mapnavigation.controller.LocationManager;
import com.example.mapnavigation.controller.QueryerManager;
import com.example.mapnavigation.data.db.LocationPoint;
import com.example.mapnavigation.utils.ToastUtils;

import org.litepal.LitePal;
import org.litepal.tablemanager.Connector;


/**
 * Created by zzg on 17-3-30.
 */

public class MapApplication extends Application {

    // 当前应用程序的上下文环境
    private static Context mContext;
    // 查询服务控制器，全局只有一个
    private QueryerManager mQueryManager;
    // 位置管理器
    private LocationManager mLocationMananger;
    // 地图管理器
    private AMapManager mAMapManager;

    // 当前应用程序实例
    private static MapApplication mInstance;

    private ToastUtils mToastUtils;


    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化全局上下文环境
        mContext = getApplicationContext();
        // 初始化LitePal
        LitePal.initialize(this);
        // 获取数据库实例
        Connector.getDatabase();
        // 获取查询服务管理器
        mQueryManager = new QueryerManager(mContext);


        mInstance = this;


        mToastUtils = new ToastUtils(mContext);

    }

    /**
     * 获取ApplicationContext
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

    public QueryerManager getmQueryManager(){
        return mQueryManager;
    }

    /**
     * 获取应用程序实例
     * @return
     */
    public static MapApplication getInstance() {
        return mInstance;
    }

    public LocationManager getmLocationMananger() {
        return mLocationMananger;
    }

    public void setmLocationMananger(LocationManager mLocationMananger) {
        this.mLocationMananger = mLocationMananger;
    }

    public void setmQueryManager(QueryerManager mQueryManager) {
        this.mQueryManager = mQueryManager;
    }

    public AMapManager getmAMapManager() {
        return mAMapManager;
    }

    public void setmAMapManager(AMapManager mAMapManager) {
        this.mAMapManager = mAMapManager;
    }


// ---------------------getter and setter--------------------------
}
