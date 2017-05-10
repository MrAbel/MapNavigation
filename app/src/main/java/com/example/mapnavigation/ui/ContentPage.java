package com.example.mapnavigation.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.mapnavigation.R;

/**
 * ViewPager中的内容页，该内容页支持三种状态加载中（默认）、加载成功、加载失败
 * Created by zzg on 17-4-12.
 */

public abstract class ContentPage extends FrameLayout {

    // ---------------field---------------------

    // 定义Page页加载状态常量
    public enum PageState {
        STATE_LOADING(0),   // 加载中的状态
        STATE_LOADED(1),   // 加载成功的状态
        STATE_ERROR(2);     // 加载数据失败的状态

        private int value;

        PageState(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    // mState表示当前页的状态
    private PageState mState = PageState.STATE_LOADING; // 每个界面默认是加载中的状态
    // 加载中对应的View
    private View mLoadingView;
    // 加载数据失败的View
    private View mErrorView;
    // 加载数据成功的View
    private View mLoadedView;
    // 布局参数
    private LayoutParams mParams;

    // ---------------构造函数-------------------
    public ContentPage(Context context) {
        super(context);
        init();
    }

    public ContentPage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ContentPage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // -----------------普通函数-------------------
    /**
     * 初始化ContentPage
     */
    private void init() {
        // 向ContenPage中引入View(Fragment)
        loadPage();
        // 加载ContentPage中所需要的数据以及刷新页面
        loadDataAndRefreshPage();
    }

    /**
     * 根据ContenPage的PageState加载相应的View
     */
    private void loadPage(){

        // 加载View的参数
        mParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        // 区分不同的ContenPage状态
        switch (mState.getValue()) {
            case 0://加载中的状态:
                if (mLoadingView == null) {
                    mLoadingView = View.inflate(getContext(), R.layout.content_page_loading, null);
                }
                removeAllViews();
                addView(mLoadingView, mParams);
                break;
            case 1://加载成功的状态
                removeAllViews();
                mLoadedView = loadView();
                addView(mLoadedView, mParams);
                break;
            case 2://加载失败的状态
                if (mErrorView == null) {
                    mErrorView = View.inflate(getContext(), R.layout.content_page_error, null);
                    // 如果加载失败，点击重新加载
                    Button btn_reload = (Button) mErrorView.findViewById(R.id.btn_reload);
                    btn_reload.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //　先显示loadingView
                            mState = PageState.STATE_LOADING;
                            loadPage();
                            //　重新请求数据，然后刷新page
                            loadDataAndRefreshPage();
                        }
                    });
                }
                removeAllViews();
                addView(mErrorView, mParams);
                break;
        }
    }

    /**
     * 获取数据并且刷新页面
     */
    private void loadDataAndRefreshPage() {

        //　获取数据对象
        Object data = loadData();

        //　根据请求回来的数据对象，判断它对应的state,并赋值给当前的state
        mState = checkData(data);

        // 根据最新的state，刷新page
        loadPage();
    }

    /**
     * 对请求回来的数据对象进行判断，得到它对应的state
     *
     * @param data
     * @return
     */
    private PageState checkData(Object data) {

        PageState state;
        if (data == PageState.STATE_LOADING) {
            // 正在请求数据
            state = PageState.STATE_LOADING;
        } else if (data == PageState.STATE_LOADED){
            // 请求回来有数据，设置state为Loaded
            state = PageState.STATE_LOADED;
        }else{
            //　获取数据失败，设置state为error
            state = PageState.STATE_ERROR;
        }
        return state;
    }

    public void refreshPage(Object o) {
        if (o == null) {
            //　说明没有数据，那么对应的state应该是error
            mState = PageState.STATE_LOADED;
        } else {
            //　说明请求回来的有数据，那么对应的state应该是success
            mState = PageState.STATE_LOADED;
        }
        loadPage();
    }

    public void loadNewView(View view){
        removeAllViews();
        addView(view, mParams);
    }

    // -----------------抽象函数----------------------
    /**
     * 每个子界面的成功的View不一样，所以应由每个子界面自己去实现
     *
     * @return
     */
    public abstract View loadView();

    /**
     * 每个子界面请求数据和解析数据的过程都不一样，而我只关心每个界面请求回来的数据的对象，那么
     * 每个子界面请求数据和解析的过程应该由每个子界面自己实现
     *
     * @return
     */
    public abstract Object loadData();




}
