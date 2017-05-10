package com.example.mapnavigation.utils;

import com.amap.api.navi.enums.NaviType;

/**
 * 定义整个程序所需要的常量
 * Created by zzg on 17-4-10.
 */

public class Constants {

    // 地图搜索查询成功返回码
    public static final int AMAP_SEARCH_SUCCESS_CODE = 1000;
    // 逆地理搜索半径
    public static final int AMAP_REGEOCODE_RADIUS = 200;
    // 地图初始缩放比例
    public static final float AMAP_INIT_ZOOM_VALUE = 18;
    // 地图显示行政区边界时的缩放比例
    public static final float AMAP_DISTRICT_ZOOM_VALUE = 8;

    // ----------ViewPager中的页名称-----------
    // ----------MainActivity中的ViewPager
    public static final int Pager_NearBy = 0;
    public static final int Pager_Map = 1;
    public static final int Pager_Nav = 2;
    public static final int Pager_Main_ALL = 3;
    // ----------NavFragment中的ViewPager
    public static final int Pager_Walk = 0;
    public static final int Pager_Bus = 1;
    public static final int Pager_Car = 2;
    public static final int Pager_Nav_ALL = 3;

    // ----------------------------------------
    public static final int WALK_ROUTE = 0;
    public static final int BUS_ROUTE = 1;
    public static final int CAR_ROUTE = 2;
    public static final int BIKE_ROUTE = 3;

    // -----------------------------------------
    public static final String Kilometer = "\u516c\u91cc";// "公里";
    public static final String Meter = "\u7c73";// "米";




    public static final int MAX_ITEM_LOAD_MORE = 5;// 当首次请求数据超过条后开启加载更多功能

    // ---------------------------------------------------
    // poi搜索处理事件
    public static final int EVENT_SEARCH_POI = 0x100;


    /* 导航模式 。 */
    public static final int NAVI_MODE_EMULATOR = NaviType.EMULATOR;
    public static final int NAVI_MODE_GPS = NaviType.GPS;

    // handle message
    public static final int SEARCH_BEGINPOS = 1;
    public static final int SEARCH_ENDPOS = 2;
    public static final int SEARCH_ROUTE = 3;
    public static final int SEARCH_HISTORY = 4;

}
