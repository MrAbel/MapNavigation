package com.example.mapnavigation.ui.map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.amap.api.services.poisearch.PoiResult;
import com.example.mapnavigation.MapApplication;
import com.example.mapnavigation.R;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.controller.AMapManager;
import com.example.mapnavigation.controller.LocationManager;
import com.example.mapnavigation.controller.QueryerManager;
import com.example.mapnavigation.ui.ContentPage;
import com.example.mapnavigation.ui.customview.SearchBar;
import com.example.mapnavigation.utils.AppUtils;
import com.example.mapnavigation.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.example.mapnavigation.utils.Constants.EVENT_SEARCH_POI;
import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-3.
 */

public class MapFragment extends BaseFragment implements  MapContract.View, View.OnClickListener,
        View.OnKeyListener, TextWatcher, QueryerManager.OnAPoiSearchListener{

    private static final String ARGUMENT_TASK_ID = "TASK_ID";

    // ----------------fields----------------
    // view相对应的presenter
    private MapContract.Presenter mPresenter;
    // 定位服务类(此类提供单次定位、持续定位、最后位置相关功能)
    private AMapLocationClient mLocationClient;
    // 定位参数设置类
    private AMapLocationClientOption mLocationOption;
    //　对地图的操作控制类
    private AMap mAMap;
    // 定位管理类
    private LocationManager mLocationManager;
    // 地图管理类
    private AMapManager mAMapManager;
    // 搜索栏视图
    public SearchBar mSearchBar;
    // 地图视图
    public MapView mMapView;
    // 引入的布局引用
    public View mView;
    // 定位按钮
    public ImageView mLocateBtn;
    // 导航按钮
    public Button mNavBtn;

    private QueryerManager mQueryerManager;
    /* Poi结果 . */
    private PoiResult mCurrentPoiResult;

    private Handler mHandler;



    // -------------BaseFragment-----------------
    /**
     * 获取加载成功的Ｖiew
     * @return
     */
    @Override
    public View getLoadedView() {
        // 动态加载布局文件
        mView = View.inflate(mContext, R.layout.fragment_content_map, null);
        // 初始化该View
        init();
        return mView;
    }

    @Override
    public Object getData() {

        return ContentPage.PageState.STATE_LOADED;
    }


    // ------------------普通函数--------------------------

    /**
     * 用于获取View(MapFragment)的实例
     * @return
     */
    public static MapFragment newInstance() {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    /**
     * 为该View做些初始化工作
     */
    private void init(){
        // 获取控件的应用
        initView();
        // 创建地图
        mMapView.onCreate(mSavedInstanceState);
        // 获得地图控制
        mAMap = mMapView.getMap();
        // 获取定位管理类
        mLocationManager = new LocationManager(mAMap);
        MapApplication.getInstance().setmLocationMananger(mLocationManager);
        // 获取地图管理类
        mAMapManager = new AMapManager(mContext, mAMap);
        MapApplication.getInstance().setmAMapManager(mAMapManager);
        // 获取全局查询服务管理器
        mQueryerManager = MapApplication.getInstance().getmQueryManager();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what){
                    case EVENT_SEARCH_POI:
                        // 清空地图覆盖层
                        MapApplication.getInstance().getmAMapManager().removePoiOverlay();
                        //mActivity.get().mMapController.removeOverlay();
                        // 搜索Poi结果处理
                        MapApplication.getInstance().getmAMapManager().addPoiOverlay(mCurrentPoiResult);

                        break;

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
        mMapView = (MapView) mView.findViewById(R.id.mapView);
        mSearchBar = (SearchBar)mView.findViewById(R.id.searchbar);
        mLocateBtn = (ImageView)mView.findViewById(R.id.imageView_locate_btn);
        regiesterListener();
    }

    /**
     * 注册监听器
     */
    public void regiesterListener(){
        mLocateBtn.setOnClickListener(this);
        mSearchBar.setOnKeyListener(this);
        mSearchBar.addTextChangedListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState()，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    // ----------------View.OnClickListener----------------------
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageView_locate_btn:
                mAMapManager.moveToLocation(mLocationManager.getmLatLng());
                //mLocationManager.locate();
                //showCurLocation();
                break;
            default:
                break;
        }
    }

    // ----------------MapContract.View----------------------
    @Override
    public void setPresenter(MapContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showCurLocation() {

    }

    // ---------------------View.OnKeyListener---------------------
    /**
     * 监听搜索栏输入
     * */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_ENTER){
            // 输入的是回车键，用户输入完成
            String keyword = AppUtils.getString(mSearchBar);
            mSearchBar.setText("");
            if (AppUtils.checkString(keyword) == false){
                return false;
            }

            ToastUtils.showShort(keyword);
            //mAMapManager.searchDistrict(keyword);.
            mQueryerManager.setmAPoiSearchListener(this);
            //mQueryerManager.searchPoi(keyword);
            mQueryerManager.searchNearbyKeyword(mLocationManager.getmAMapLocation(), keyword, keyword, 1000);
            //mQueryerManager.searchNearbyKeyword(mLocationClient);

            return true;
        }

        return false;
    }

    // ---------------------TextWatcher---------------------
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 当文本框的内容改变时回调
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        String newText = s.toString().trim();
        InputtipsQuery inputquery = new InputtipsQuery(newText, "长沙");
        Inputtips inputTips = new Inputtips(getContext(), inputquery);
        inputTips.setInputtipsListener(new Inputtips.InputtipsListener() {
            @Override
            public void onGetInputtips(List<Tip> list, int i) {
                if (i == AMapException.CODE_AMAP_SUCCESS) {// 正确返回
                    List<String> listString = new ArrayList<String>();
                    for (int j = 0; j < list.size(); j++) {
                        listString.add(list.get(j).getName());
                    }
                    ArrayAdapter<String> aAdapter = new ArrayAdapter<String>(
                            MapApplication.getContext(),
                            R.layout.drawer_list_item, listString);
                    mSearchBar.setAdapter(aAdapter);
                    aAdapter.notifyDataSetChanged();
                } else {

                }
            }
        });
        inputTips.requestInputtipsAsyn();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    // --------------------------碎片生命周期---------------------------
    @Override
    public void onStart() {
        super.onStart();

        //mLocateBtn.setVisibility(View.GONE);
        mLocationManager.startLocation();
    }

    @Override
    public void onStop() {
        super.onStop();
        mLocationManager.stopLocation();
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        //unbinder.unbind();
        mMapView.onDestroy();

    }

    @Override
    public void onPoiSearched(final PoiResult result) {
        //final PoiResult fresult = result;
        // 延迟500ms再隐藏进度对话框
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //mProgressDialog.dismiss();

                if (result == null){
                    return;
                }

                mCurrentPoiResult = result;
                // 处理Poi搜索
                mHandler.sendEmptyMessage(EVENT_SEARCH_POI);
            }
        }, 500);
    }


}
