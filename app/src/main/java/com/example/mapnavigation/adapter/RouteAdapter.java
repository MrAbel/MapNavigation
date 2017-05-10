package com.example.mapnavigation.adapter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mapnavigation.R;
import com.example.mapnavigation.data.db.RouteBean;
import com.example.mapnavigation.di.module.RouteHistory;
import com.example.mapnavigation.ui.nav.NavFragment;
import com.example.mapnavigation.utils.Constants;
import com.example.mapnavigation.utils.ToastUtils;

import java.util.List;

/**
 * Created by zzg on 17-4-18.
 */

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder>
    implements View.OnClickListener{

    private List<RouteHistory> mRouteHistory;
    private ViewHolder mViewHolder;


    /**
     * 内部类
     */
    static class ViewHolder extends RecyclerView.ViewHolder{
        View routeView;
        TextView startSite;
        TextView endSite;


        public ViewHolder(View itemView) {
            super(itemView);
            routeView = itemView;
            startSite = (TextView) itemView.findViewById(R.id.start);
            endSite = (TextView) itemView.findViewById(R.id.end);
        }
    }

    /**
     * 构造函数初始化列表数据
     * @param routeHistoryList
     */
    public RouteAdapter(List<RouteHistory> routeHistoryList){
        mRouteHistory = routeHistoryList;
    }

    /**
     * 创建
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RouteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_route, parent, false);
        mViewHolder = new ViewHolder(view);
        mViewHolder.routeView.setOnClickListener(this);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RouteAdapter.ViewHolder holder, int position) {
        RouteHistory routeHistory = mRouteHistory.get(position);
        holder.startSite.setText(routeHistory.getBeginSite());
        holder.endSite.setText(routeHistory.getEndSite());
    }

    @Override
    public int getItemCount() {
        return mRouteHistory.size();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.list_item:
                int position = mViewHolder.getAdapterPosition();
                RouteHistory routeHistory = mRouteHistory.get(position);
                Handler handler = NavFragment.getmHandler();
                Bundle bundle = new Bundle();
                bundle.putString("start", routeHistory.getBeginSite().toString());
                bundle.putString("end", routeHistory.getEndSite().toString());
                Message msg = new Message();
                msg.setData(bundle);
                msg.what = Constants.SEARCH_HISTORY;
                handler.sendMessage(msg);
            default:
                break;
        }


    }
}
