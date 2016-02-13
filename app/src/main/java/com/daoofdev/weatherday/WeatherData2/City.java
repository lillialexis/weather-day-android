package com.daoofdev.weatherday.WeatherData2;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    City.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;

public class City {
    private final static String TAG = "WeatherDay:City";

    @SerializedName("id")
    private Integer mId = 0;

    @SerializedName("name")
    private String mName = null;

    @SerializedName("coord")
    private Coordinate mCoordinate = null;

    @SerializedName("country")
    private String mCountry = null;

    @SerializedName("population")
    private Integer mPopulation = 0;

    public City() {
    }

    public Coordinate getCoordinate() {
        return mCoordinate;
    }

    public String getCountry() {
        return mCountry;
    }

    public Integer getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public Integer getPopulation() {
        return mPopulation;
    }
}
