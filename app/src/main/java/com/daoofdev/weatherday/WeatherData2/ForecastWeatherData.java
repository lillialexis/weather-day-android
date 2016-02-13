package com.daoofdev.weatherday.WeatherData2;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    ForecastWeatherData.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ForecastWeatherData {
    private final static String TAG = "WeatherDay:ForecastWeatherData";

    @SerializedName("city")
    private City mCity = null;

    @SerializedName("cod")
    private String mCod = null;

    @SerializedName("message")
    private Double mMessage = 0.0;

    @SerializedName("cnt")
    private Integer mCount = 0;

    @SerializedName("list")
    private ArrayList<ForecastItem> mList = null;

    public ForecastWeatherData() {
    }

    public City getCity() {
        return mCity;
    }

    public Integer getCount() {
        return mCount;
    }

    public String getCod() {
        return mCod;
    }

    public ArrayList<ForecastItem> getList() {
        return mList;
    }

    public Double getMessage() {
        return mMessage;
    }
}
