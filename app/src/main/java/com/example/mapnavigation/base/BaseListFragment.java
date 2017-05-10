package com.example.mapnavigation.base;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.mapnavigation.R;
import com.example.mapnavigation.data.db.RouteBean;
import com.example.mapnavigation.utils.Constants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zzg on 17-4-17.
 */

/**
 * Created by zs on 2015/12/21.
 */
public abstract class BaseListFragment<T> extends BaseFragment implements
        PullToRefreshBase.OnRefreshListener<ListView> {

    // 下拉刷新列表部件
    public PullToRefreshListView mRefreshListView;
    // ListView部件
    public ListView mListView;
    public ArrayList<T> mList = new ArrayList<>();
    public MyAdapter mAdapter;
    public  Handler mHandler;
    public Context mContext;
    protected FragmentManager mFragmentManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
        mFragmentManager = getChildFragmentManager();
        init();
    }

    @Override
    public View getLoadedView() {
        setPullRefreshView();
        mHandler = new Handler();
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
        return mRefreshListView;
    }


    public void setList(ArrayList<T> list) {
        mList = list;
    }

    /**
     * 列表适配器
     *
     */
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Object getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return setView(position, convertView, parent);
        }
    }


    /**
     * 加载该Fragment时做些初始化工作
     */
    private void init(){
        // 初始化视图控件
        initView();
        // 注册监听器
        regiesterListener();
    }

    /**
     * 初始化视图控件
     */
    private void initView(){
        // 获取RefreshListView的引用
        mRefreshListView = (PullToRefreshListView) View.inflate(mContext,
                R.layout.ptr_listview, null);

        // 获取ListView的引用
        mListView = mRefreshListView.getRefreshableView();
        // 设置ListView的样式
        mListView.setDividerHeight(0);
        mListView.setSelector(android.R.color.transparent);
        mListView.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
    }

    /**
     * 注册监听器
     */
    private void regiesterListener(){
        //　为RefreshListView设置监听器
        mRefreshListView.setOnRefreshListener(this);
    }

    /**
     * 根据数据的多少调整是否需要加载更多，因为在数据不够一页的时候，加载更多有bug
     */
    private void setPullRefreshView() {
        if(mList.size()>= Constants.MAX_ITEM_LOAD_MORE){
            mRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        }else{
            mRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        }
    }

    // --------PullToRefreshBase.OnRefreshListener<ListView>----------
    @Override
    public void onRefresh(PullToRefreshBase<ListView> refreshView) {
        // 下拉刷新
        if (mRefreshListView.getCurrentMode() == PullToRefreshBase.Mode.PULL_FROM_START) {
            setRefresh();// 下拉刷新
        }else {// 上拉加载更多
            // 加载更多
            loadMore();
        }
    }

    protected abstract View setView(int position, View convertView, ViewGroup parent);
    protected abstract void setRefresh();
    protected abstract void loadMore();
}
