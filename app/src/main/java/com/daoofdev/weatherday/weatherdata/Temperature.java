package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    Temperature.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;

public class Temperature {
    private final static String TAG = "WeatherDay:Temperature";

    @SerializedName("day")
    private Double mDay = 0.0;

    @SerializedName("min")
    private Double mMin = 0.0;

    @SerializedName("max")
    private Double mMax = 0.0;

    @SerializedName("night")
    private Double mNight = 0.0;

    @SerializedName("eve")
    private Double mEve = 0.0;

    @SerializedName("morn")
    private Double mMorn = 0.0;

    public Temperature() {
    }

    public Double getDay() {
        return mDay;
    }

    public Double getEve() {
        return mEve;
    }

    public Double getMax() {
        return mMax;
    }

    public Double getMin() {
        return mMin;
    }

    public Double getMorn() {
        return mMorn;
    }

    public Double getNight() {
        return mNight;
    }
}
