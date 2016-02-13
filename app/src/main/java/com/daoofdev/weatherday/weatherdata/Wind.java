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

public class Wind {
    private final static String TAG = "WeatherDay:Wind";
    @SerializedName("speed")
    private Double mSpeed = 0.0;

    @SerializedName("deg")
    private Double mDeg   = 0.0;

    public Wind() {
    }

    public Double getDeg() {
        return mDeg;
    }

    public Double getSpeed() {
        return mSpeed;
    }
}
