package com.daoofdev.weatherday.WeatherData2;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    Clouds.java
 * Project: WeatherDay
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
