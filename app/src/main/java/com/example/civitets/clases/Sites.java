package com.example.civitets.clases;

import java.io.Serializable;

public class Sites implements Serializable {

    public Sites(){
    }

    private double user_lat;
    private double user_lng;
    private String name;

    public Sites(double user_lat, double user_lng, String name) {
        this.user_lat = user_lat;
        this.user_lng = user_lng;
        this.name = name;
    }

    public double getUser_lat() {
        return user_lat;
    }

    public void setUser_lat(double user_lat) {
        this.user_lat = user_lat;
    }

    public double getUser_lng() {
        return user_lng;
    }

    public void setUser_lng(double user_lng) {
        this.user_lng = user_lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
