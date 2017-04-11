package com.example.mapnavigation;

import android.app.Application;
import android.content.Context;


import com.example.mapnavigation.data.db.LocationPoint;
import com.example.mapnavigation.utils.ToastUtils;

/**
 * Created by zzg on 17-3-30.
 */

public class MapApplication extends Application {

    // 当前位置点信息
    private LocationPoint mLocationPoint;

    // 当前应用程序实例
    private static MapApplication mInstance;

    private ToastUtils mToastUtils;

    // 当前应用程序的上下文环境
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;
        mContext = getApplicationContext();

        mLocationPoint = new LocationPoint();
        mToastUtils = new ToastUtils(mContext);

    }

    /**
     * 获取ApplicationContext
     * @return
     */
    public static Context getContext(){
        return mContext;
    }

    /**
     * 获取应用程序实例
     * @return
     */
    public static MapApplication getInstance() {
        return mInstance;
    }

    // ---------------------getter and setter--------------------------
    public LocationPoint getmLocationPoint() {
        return mLocationPoint;
    }

    public void setmLocationPoint(LocationPoint mLocationPoint) {
        this.mLocationPoint = mLocationPoint;
    }
    // ---------------------getter and setter--------------------------
}
