package com.daoofdev.weatherday.WeatherData2;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    CurrentWeatherData.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentWeatherData
{
    private final static String TAG = "WeatherDay:CurrentWeatherData";

    @SerializedName("coord")
    private Coordinate mCoordinate = null;

    @SerializedName("weather")
    private ArrayList<WeatherItem> mWeatherItems = null;

    @SerializedName("main")
    private MainData mMainData = null;

    @SerializedName("wind")
    private Wind mWind     = null;

    @SerializedName("clouds")
    private Clouds mClouds = null;

    @SerializedName("dt")
    private Long mDt    = 0L;

    @SerializedName("sys")
    private System mSystem = null;

    @SerializedName("id")
    private Integer mId    = 0;

    @SerializedName("name")
    private String mName   = null;

    @SerializedName("cod")
    private Integer mCod   = 0;

    public CurrentWeatherData() {
    }

    public Clouds getClouds() {
        return mClouds;
    }

    public Integer getCod() {
        return mCod;
    }

    public Coordinate getCoordinate() {
        return mCoordinate;
    }

    public Long getDt() {
        return mDt;
    }

    public Integer getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public System getSystem() {
        return mSystem;
    }

    public ArrayList<WeatherItem> getWeatherItems() {
        return mWeatherItems;
    }

    public MainData getMainData() {
        return mMainData;
    }

    public Wind getWind() {
        return mWind;
    }
}
