package com.example.mapnavigation.di.module;

import org.litepal.crud.DataSupport;

/**
 * 历史路线表
 * Created by zzg on 17-4-27.
 */

public class RouteHistory extends DataSupport {
    private int id;
    private String beginSite;
    private String endSite;
    private int type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeginSite() {
        return beginSite;
    }

    public void setBeginSite(String beginSite) {
        this.beginSite = beginSite;
    }

    public String getEndSite() {
        return endSite;
    }

    public void setEndSite(String endSite) {
        this.endSite = endSite;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
