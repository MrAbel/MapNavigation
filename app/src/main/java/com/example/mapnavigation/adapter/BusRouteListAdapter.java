package com.example.mapnavigation.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.example.mapnavigation.R;
import com.example.mapnavigation.ui.activtiy.BusRouteDetailActivity;
import com.example.mapnavigation.utils.AMapUtils;

import java.util.List;

/**
 * Created by zzg on 17-5-10.
 */

public class BusRouteListAdapter extends RecyclerView.Adapter<BusRouteListAdapter.ViewHolder>
        implements View.OnClickListener{

    private List<BusPath> mBusPathList;
    private BusRouteResult mBusRouteResult;
    private ViewHolder mViewHolder;
    private Context mContext;


    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        TextView title;
        TextView des;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            title = (TextView) itemView.findViewById(R.id.bus_path_title);
            des = (TextView) itemView.findViewById(R.id.bus_path_des);
        }
    }

    public BusRouteListAdapter(List<BusPath> list, BusRouteResult result) {
        mBusPathList = list;
        mBusRouteResult = result;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_bus_result, parent, false);
        mViewHolder  = new ViewHolder(view);
        mViewHolder.view.setOnClickListener(this);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BusPath busPath = mBusPathList.get(position);
        holder.title.setText(AMapUtils.getBusPathTitle(busPath));
        holder.des.setText(AMapUtils.getBusPathDes(busPath));
    }

    @Override
    public int getItemCount() {
        return mBusPathList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_bus_route:
                int position = mViewHolder.getAdapterPosition();
                BusPath busPath = mBusPathList.get(position);
                Intent intent = new Intent(mContext.getApplicationContext(),
                        BusRouteDetailActivity.class);
                intent.putExtra("bus_path", busPath);
                intent.putExtra("bus_result", mBusRouteResult);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
                break;
        }
    }

}

