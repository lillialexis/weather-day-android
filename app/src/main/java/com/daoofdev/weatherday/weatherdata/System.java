package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    System.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.ConstructorConstructor;

public class System {
    private final static String TAG = "WeatherDay:System";
    @SerializedName("message")
    private Double mMessage = 0.0;

    @SerializedName("country")
    private String mCountry = null;

    @SerializedName("sunrise")
    private Long mSunrise   = 0L;

    @SerializedName("sunset")
    private Long mSunset    = 0L;

    public System() {
    }

    public String getCountry() {
        return mCountry;
    }

    public Double getMessage() {
        return mMessage;
    }

    public Long getSunrise() {
        return mSunrise;
    }

    public Long getSunset() {
        return mSunset;
    }

    public String getPrettySunrise() {
        return Constants.prettyTime(mSunrise);
    }

    public String getPrettySunset() {
        return Constants.prettyTime(mSunset);
    }
}
