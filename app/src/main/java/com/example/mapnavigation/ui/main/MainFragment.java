package com.example.mapnavigation.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.example.mapnavigation.MapApplication;
import com.example.mapnavigation.R;
import com.example.mapnavigation.controller.AMapManager;
import com.example.mapnavigation.controller.LocationManager;
import com.example.mapnavigation.ui.customview.SearchBar;

import java.util.ArrayList;
import java.util.List;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by zzg on 17-4-3.
 */

public class MainFragment extends Fragment implements  MainContract.View, View.OnClickListener{

    private static final String ARGUMENT_TASK_ID = "TASK_ID";

    // ----------------fields----------------
    // 所处的Activity的上下文环境
    private Context mContext;
    // view相对应的presenter
    private MainContract.Presenter mPresenter;
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



    // --------------------------碎片生命周期---------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // 动态加载布局文件
        mView = inflater.inflate(R.layout.fragment_content_main, container, false);
        // 获取控件的应用
        initView();
        // 创建地图
        mMapView.onCreate(savedInstanceState);
        // 获得地图控制
        mAMap = mMapView.getMap();
        // 获得上下问环境
        mContext = getContext();
        // 获取定位管理类
        mLocationManager = new LocationManager(mAMap);
        // 获取地图管理类
        mAMapManager = new AMapManager(mContext, mAMap);
        //mLocateBtn.setVisibility(View.GONE);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();
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

    // ------------------普通函数--------------------------

    /**
     * 用于获取View(MainFragment)的实例
     * @return
     */
    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
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
        mSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

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
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
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

    // ----------------MainContract.View----------------------
    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showCurLocation() {

    }

}
