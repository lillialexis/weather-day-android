package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development.
 *
 * All rights reserved.
 *
 * ${FILE_NAME}
 * WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;

public class Coordinate {
    private final static String TAG = "WeatherDay:Coordinate";

    @SerializedName("lat")
    private Double mLat = 0.0;

    @SerializedName("lon")
    private Double mLon = 0.0;

    public Coordinate() {
    }

    public Double getLat() {
        return mLat;
    }

    public Double getLon() {
        return mLon;
    }
}
