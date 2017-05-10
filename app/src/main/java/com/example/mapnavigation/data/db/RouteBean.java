package com.example.mapnavigation.data.db;

/**
 * Created by zzg on 17-4-17.
 */

public class RouteBean {
    private String starPosition;
    private String endPosition;
    private int cat;
    private int id;

    public RouteBean(){
        starPosition = "长沙理工大学";
        endPosition = "湖南大学";
        cat = 1;
    }
    public RouteBean(String begin, String end, int cat){
        starPosition = begin;
        endPosition = end;
        this.cat = cat;
    }
    public String getStarPosition() {
        return starPosition;
    }

    public void setStarPosition(String starPosition) {
        this.starPosition = starPosition;
    }

    public String getEndPosition() {
        return endPosition;
    }

    public void setEndPosition(String endPosition) {
        this.endPosition = endPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCat() {
        return cat;
    }

    public void setCat(int cat) {
        this.cat = cat;
    }
}
