package com.example.mapnavigation.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.TabPagerAdapter;
import com.example.mapnavigation.ui.customview.TabPageIndicator;

/**
 * Created by zzg on 17-4-12.
 */

public abstract class BasePagerFragment extends Fragment {

    // --------------------fields-----------------

    // 标签控件
    public TabPageIndicator mTabPagerIndicator;
    // 标签页控制的ViewPager
    public ViewPager mViewPager;
    // 标签页控制的ViewPager的适配器
    private TabPagerAdapter mTabPagerAdapter;
    // 标签页的标签
    private View mView;

    // --------------------碎片的生命周期-----------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // 每一个标签页（子）的布局架构都一样
        mView = View.inflate(getActivity(), R.layout.fragment_viewpager_indicator, null);
        init();
        return mView;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    // --------------------普通函数-----------------
    /**
     * 为View做初始化工作
     */
    private void init(){
        initeView();
        // 初始化ViewPager的适配器
        mTabPagerAdapter = new TabPagerAdapter(getChildFragmentManager(), setTitles());
        // 为ViewPager设置适配器
        mViewPager.setAdapter(mTabPagerAdapter);
        mViewPager.setOffscreenPageLimit(10);
        // 将标签页和ViewPager关联
        mTabPagerIndicator.setViewPager(mViewPager);
        // 设置标签页的样式
        setIndicator();
    }

    /**
     * 初始化视图控件
     */
    private void initeView(){
        mViewPager = (ViewPager) mView.findViewById(R.id.viewpager_tab);
        mTabPagerIndicator = (TabPageIndicator) mView.findViewById(R.id.indicator);
    }

    /**
     * 设置指示器的样式，可以通过重写单独设置覆盖
     */
    private void setIndicator() {
        mTabPagerIndicator.setIndicatorMode(TabPageIndicator.IndicatorMode.MODE_WEIGHT_NOEXPAND_SAME);
        mTabPagerIndicator.setDividerColor(Color.parseColor("#dcdcdc"));// 设置分割线的颜色
        //mTabPagerIndicator.setDividerPadding(CommonUtils.dip2px(SYApplication.getContext(), 0));
        mTabPagerIndicator.setIndicatorColor(Color.parseColor("#44A43B"));// 设置底部横线的颜色
        mTabPagerIndicator.setTextColorSelected(Color.parseColor("#44A43B"));// 设置tab标题选中的颜色
        mTabPagerIndicator.setTextColor(Color.parseColor("#797979"));// 设置tab标题未被选中的颜色
        //mTabPagerIndicator.setTextSize(CommonUtils.sp2px(SYApplication.getContext(), 14));// 设置字体大小
    }

    // -------------------抽象函数----------------------

    /**
     * 为标签页设置标题
     * @return
     */
    protected abstract String[] setTitles();// 设置标题

}
