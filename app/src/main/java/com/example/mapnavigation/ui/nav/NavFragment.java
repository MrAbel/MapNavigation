package com.example.mapnavigation.ui.nav;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import com.amap.api.maps.AMap;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.RegeocodeAddress;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;
import com.example.mapnavigation.MapApplication;
import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.BusRouteListAdapter;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.controller.AMapManager;
import com.example.mapnavigation.controller.LocationManager;
import com.example.mapnavigation.controller.QueryerManager;
import com.example.mapnavigation.data.db.LocationPoint;
import com.example.mapnavigation.di.module.RouteHistory;
import com.example.mapnavigation.overlay.DrivingRouteOverlay;
import com.example.mapnavigation.overlay.WalkRouteOverlay;
import com.example.mapnavigation.ui.ContentPage;
import com.example.mapnavigation.ui.nav.panel.NavPanel;
import com.example.mapnavigation.ui.nav.panel.bus.BusPagerFragment;
import com.example.mapnavigation.ui.nav.panel.car.CarPagerFragment;
import com.example.mapnavigation.ui.nav.panel.foot.WalkRouteFragment;
import com.example.mapnavigation.utils.AMapUtils;
import com.example.mapnavigation.utils.AppUtils;
import com.example.mapnavigation.utils.Constants;
import com.example.mapnavigation.utils.FragmentUtils;
import com.example.mapnavigation.utils.ToastUtils;

import java.util.ArrayList;
import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-11.
 */

public class NavFragment extends BaseFragment implements NavContract.View, View.OnClickListener,
        QueryerManager.OnAMapQueryListener{

    // --------------------fields-----------------------
    private NavContract.Presenter mPresenter;

    // 引入的布局引用
    public View mView;
    // 交换按钮应用
    public Button mSwitchBtn;
    // 起始地点输入框引用
    public AutoCompleteTextView mBeginSiteEdit;
    // 结束地点输入框引用
    public AutoCompleteTextView mEndSiteEdit;
    public FrameLayout mNavPanel;
    private QueryerManager mQueryerManager;
    private AMapManager mAMapManager;
    private boolean isBeginPoint = true;
    private static Handler mHandler;
    private NavPanel navPanel;

    private AMap mWalkRouteAMap;
    private AMap mCarRouteAMap;
    private AMap mBusRouteAMap;
    private boolean mIsHistoryDisp = true;

    // 位置信息
    private String mBeginSite;
    private String mEndSite;
    private LatLonPoint mBeginPoint;
    private LatLonPoint mEndPoint;
    private LatLonPoint mCurPoint;
    private LocationPoint mCurPosInfo;

    private LocationManager mLocationManager;
    private int mRouteType = Constants.CAR_ROUTE;




    // ------------------普通函数--------------------------

    /**
     * 用于获取View(NavFragment)的实例
     * @return
     */
    public static NavFragment newInstance() {
        NavFragment fragment = new NavFragment();
        return fragment;
    }

    /**
     * 加载布局时做些初始化工作
     */
    private void init(){
        // 初始化视图控件
        initView();
        // 获取查询管理器
        mQueryerManager = new QueryerManager(mContext);
        // 获取地图管理器
        mAMapManager = MapApplication.getInstance().getmAMapManager();
        // 获取位置管理器
        mLocationManager = MapApplication.getInstance().getmLocationMananger();
        // 设置监听器
        regiesterListener();
        // 初始化Handler
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what){
                    case Constants.SEARCH_BEGINPOS:
                        // 获取起点坐标
                        searchBeginPoint();
                        break;
                    case Constants.SEARCH_ENDPOS:
                        // 获取终点坐标
                        searchEndPoint();
                        break;
                    case Constants.SEARCH_ROUTE:
                        searchRoute(mRouteType);
                        // 将查询记录存入数据库中
                        RouteHistory route = new RouteHistory();
                        route.setBeginSite(mBeginSite);
                        route.setEndSite(mEndSite);
                        route.setType(1);
                        route.save();
                        break;
                    case Constants.SEARCH_HISTORY:
                        mRouteType = getType();
                        Bundle bundle1 = msg.getData();
                        mBeginSite = bundle1.getString("start");
                        mEndSite = bundle1.getString("end");
                        mEndSiteEdit.setText(mEndSite);
                        mHandler.sendEmptyMessage(Constants.SEARCH_BEGINPOS);
                    default:
                        break;
                }
            }

        };




    }

    /**
     * 初始化视图控件
     */
    private void initView(){
        mBeginSiteEdit = (AutoCompleteTextView) mView.findViewById(R.id.begin_site);
        mEndSiteEdit = (AutoCompleteTextView) mView.findViewById(R.id.end_site);
        mEndSiteEdit.requestFocus();
        mSwitchBtn = (Button) mView.findViewById(R.id.switch_site);
        mNavPanel = (FrameLayout) mView.findViewById(R.id.nav_panel);


    }

    /**
     * 注册监听器
     */
    private void regiesterListener(){
        mSwitchBtn.setOnClickListener(this);
        mQueryerManager.setmOnAMapQueryListener(this);
    }

    /**
     * 查询起点坐标
     */
    private void searchBeginPoint(){
        // 如果起点是“我的位置”则省略查询起点坐标，用当前坐标赋值
        if (mBeginSite.equals("我的位置")){
            // 获取当前位置
            getCurPos();
            mBeginPoint = mCurPoint;
            isBeginPoint = false;
            mHandler.sendEmptyMessage(Constants.SEARCH_ENDPOS);
        }else{
            mQueryerManager.searchLatlon(mBeginSite, "长沙");
        }
    }

    /**
     * 查询终点坐标
     */
    private void searchEndPoint(){
        mQueryerManager.searchLatlon(mEndSite, "长沙");
    }

    /**
     * 查询路线
     * @param type
     */
    private void searchRoute(int type){

        // 如果第一次查询，加载地图
        if (mIsHistoryDisp){
            // navPanel.setAdapter();

            mWalkRouteAMap = WalkRouteFragment.getAMap();
            mCarRouteAMap = CarPagerFragment.getAMap();

            mIsHistoryDisp = false;
        }

        // 调用查询模块
        mQueryerManager.searchRoute(mBeginPoint, mEndPoint, type);
    }

    /**
     * 用于获取当前位置坐标
     */
    private void getCurPos(){
        // 获取当前位置
        mCurPosInfo = mLocationManager.getmCurPosInfo();
        mCurPoint = new LatLonPoint(mCurPosInfo.getmLatitude(), mCurPosInfo.getmLongitude());
    }

    /**
     * 获取当前类型
     * @return
     */
    private int getType(){
        return navPanel.getTitlePos();
    }

    // ---------------------外部接口--------------------
    public static Handler getmHandler(){
        return mHandler;
    }

    // --------------------NavContract.View-----------------------
    @Override
    public void setPresenter(NavContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    // --------------------View.OnClickListener-----------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.switch_site:
                //获取路线类型
                mRouteType = getType();
                // 获取起点、终点
                mBeginSite = AppUtils.getString(mBeginSiteEdit);
                mEndSite = AppUtils.getString(mEndSiteEdit);
                // 查询起点坐标
                mHandler.sendEmptyMessage(Constants.SEARCH_BEGINPOS);
                break;
            case R.id.begin_site:
                break;
            case R.id.end_site:
                break;
            default:
                break;
        }
    }


    /**
     * 加载布局文件
     * @return
     */
    @Override
    public View getLoadedView() {
        // 动态加载布局
        mView = View.inflate(mContext, R.layout.fragment_content_nav, null);
        // 获取NavPanel实例
        navPanel = NavPanel.newInstance();
        // 加载NavPanel布局
        FragmentUtils.addFragment(getChildFragmentManager(), R.id.nav_panel, navPanel);
        // 做初始化工作
        init();
        return mView;
    }


    @Override
    public Object getData() {
        return ContentPage.PageState.STATE_LOADED;
    }

    // ----------------QueryerManager.OnAMapQueryListener----------------
    @Override
    public void onRegeocodeSearched(RegeocodeAddress address) {

    }

    /**
     * 根据地址查询坐标的处理回调
     * @param geocodeResult
     */
    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult) {
        if (geocodeResult == null){
            return;
        }
        // 获取地理编码后的地点
        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);

        // 如果是起点坐标，则查询终点坐标，如果是终点坐标查询路线
        if (isBeginPoint){
            isBeginPoint = false;
            mBeginPoint = address.getLatLonPoint();
            mHandler.sendEmptyMessage(Constants.SEARCH_ENDPOS);
        }else{
            isBeginPoint = true;
            mEndPoint = address.getLatLonPoint();
            mHandler.sendEmptyMessage(Constants.SEARCH_ROUTE);
        }
    }

    @Override
    public void onDistrictSearched(ArrayList<DistrictItem> districtItems) {

    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult) {

        // 判读返回结果是否为空
        if (walkRouteResult == null){
            return;
        }

        // 获取步行路径规划方案。
        final WalkPath walkPath = walkRouteResult.getPaths().get(0);

        // 创建步行规划图层
        WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
                mContext, mWalkRouteAMap, walkPath,
                walkRouteResult.getStartPos(),
                walkRouteResult.getTargetPos());

        mWalkRouteAMap.clear();
        // 清空之前的路线图层
        walkRouteOverlay.removeFromMap();
        // 添加步行路线到地图中
        walkRouteOverlay.addToMap();
        // 移动镜头到当前的视角
        walkRouteOverlay.zoomToSpan();

        // 获取此规划方案的距离，单位米。
        int dis = (int) walkPath.getDistance();
        // 获取方案的预计消耗时间，单位秒。
        int dur = (int) walkPath.getDuration();
        // 将时间和距离转换成友好的显示方式
        String des = AMapUtils.getFriendlyTime(dur)+"("+ AMapUtils.getFriendlyLength(dis)+")";

        // 传递信息给显示界面，用于导航
        WalkRouteFragment.setLocation(mBeginPoint, mEndPoint);
        WalkRouteFragment.updata(des);
    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult) {

        // 判读返回结果是否为空
        if (driveRouteResult == null) {
            return;
        }

        // 获取汽车路径规划方案。
        final DrivePath drivePath = driveRouteResult.getPaths().get(0);

        // 创建汽车规划图层
        DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                mContext, mCarRouteAMap, drivePath,
                driveRouteResult.getStartPos(),
                driveRouteResult.getTargetPos(), null);

        mCarRouteAMap.clear();
        //设置节点marker是否显示
        drivingRouteOverlay.setNodeIconVisibility(false);
        //是否用颜色展示交通拥堵情况，默认true
        drivingRouteOverlay.setIsColorfulline(true);
        // 清空之前的路线图层
        drivingRouteOverlay.removeFromMap();
        // 添加汽车路线到地图中
        drivingRouteOverlay.addToMap();
        // 移动镜头到当前的视角
        drivingRouteOverlay.zoomToSpan();


        // 获取此规划方案的距离，单位米。
        int dis = (int) drivePath.getDistance();
        // 获取方案的预计消耗时间，单位秒。
        int dur = (int) drivePath.getDuration();
        // 将时间和距离转换成友好的显示方式
        String des = AMapUtils.getFriendlyTime(dur) + "(" + AMapUtils.getFriendlyLength(dis) + ")";

        // 传递信息给显示界面，用于导航
        CarPagerFragment.setLocation(mBeginPoint, mEndPoint);
        CarPagerFragment.updata(des);
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult) {
        //mAMap.clear();// 清理地图上的所有覆盖物
        // 判读返回结果是否为空
        if (busRouteResult == null){
            return;
        }

        if (busRouteResult.getPaths().size() > 0) {
            BusPagerFragment.updata(busRouteResult.getPaths(), busRouteResult);

        } else if (busRouteResult != null && busRouteResult.getPaths() == null) {
            ToastUtils.showShort("bus route not found");
        }
    }
}
