package com.example.mapnavigation.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mapnavigation.ui.ContentPage;

/**
 * Created by zzg on 17-4-12.
 */

public abstract class BaseFragment extends Fragment {

    // -------------fields---------------------
    // 上下问环境
    protected Context mContext;
    // 该页面的实例状态
    protected Bundle mSavedInstanceState;
    protected FragmentManager mFragmentManager;
    protected ContentPage mContentPage;
    protected ProgressDialog mProgressDialog;


    // ------------------------碎片声明周期-------------------------
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // 获得上下文环境
        mContext = getActivity();
        mSavedInstanceState = savedInstanceState;

        // 初始化ProgressDialog Loading
        mProgressDialog = new ProgressDialog(mContext);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.setMessage("请稍后");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(true);

        // 获得FragmentManager
        mFragmentManager = (FragmentManager)getActivity().getSupportFragmentManager();

        if (mContentPage == null) {
            mContentPage = new ContentPage(mContext){

                @Override
                public View loadView() {
                    return getLoadedView();
                }

                @Override
                public Object loadData() {
                    return getData();
                }
            };
        } else {
            //CommonUtils.removeSelfFromParent(contentPage);
        }
        return mContentPage;
    }

    public void refreshPage(Object o) {
        mContentPage.refreshPage(o);
    }
    // --------------抽象函数----------------

    /**
     * 获取View
     * @return
     */
    public abstract View getLoadedView();

    /**
     * 获取数据
     * @return
     */
    public abstract Object getData();
}
