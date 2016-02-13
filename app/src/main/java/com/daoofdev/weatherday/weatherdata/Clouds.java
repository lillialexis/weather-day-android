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

public class Clouds {
    private final static String TAG = "WeatherDay:Clouds";
    @SerializedName("all")
    private Integer mAll = 0;

    public Clouds() {
    }

    public Integer getAll() {
        return mAll;
    }
}
