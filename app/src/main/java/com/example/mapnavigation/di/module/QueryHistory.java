package com.example.mapnavigation.di.module;

import org.litepal.crud.DataSupport;

/**
 * 历史查询信息表
 * Created by zzg on 17-4-27.
 */

public class QueryHistory extends DataSupport {
    private int id;
    private String content;
    private String address;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
