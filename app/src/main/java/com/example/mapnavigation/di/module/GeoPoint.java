package com.example.mapnavigation.di.module;

import org.litepal.crud.DataSupport;

/**
 * 位置信息表
 * Created by zzg on 17-4-27.
 */

public class GeoPoint extends DataSupport{

    private int id;
    private double latitude;
    private double longitude;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
