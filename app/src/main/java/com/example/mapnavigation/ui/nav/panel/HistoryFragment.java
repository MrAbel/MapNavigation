package com.example.mapnavigation.ui.nav.panel;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapnavigation.R;
import com.example.mapnavigation.adapter.RouteAdapter;
import com.example.mapnavigation.base.BaseFragment;
import com.example.mapnavigation.base.BaseListFragment;
import com.example.mapnavigation.data.db.RouteBean;
import com.example.mapnavigation.di.module.RouteHistory;
import com.example.mapnavigation.ui.ContentPage;
import com.example.mapnavigation.ui.nav.panel.foot.WalkRouteFragment;
import com.example.mapnavigation.utils.AppUtils;

import org.litepal.crud.DataSupport;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zzg on 17-4-16.
 */

public class HistoryFragment extends BaseFragment {


    private View view;
    private RecyclerView recyclerView;
    private List<RouteHistory> list = new ArrayList<RouteHistory>();

    public static HistoryFragment newInstance() {
        HistoryFragment fragment = new HistoryFragment();
        return fragment;
    }

    @Override
    public View getLoadedView() {
        view = View.inflate(getActivity(), R.layout.ptr_listview, null);
        init();
        return view;
    }

    private void init() {
        recyclerView = (RecyclerView) view.findViewById(R.id.pull_refresh_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        initdata();
        recyclerView.setLayoutManager(layoutManager);
        RouteAdapter adapter = new RouteAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    private void initdata() {
        list = DataSupport.findAll(RouteHistory.class);
    }

    @Override
    public Object getData() {
        return ContentPage.PageState.STATE_LOADED;
    }
}
