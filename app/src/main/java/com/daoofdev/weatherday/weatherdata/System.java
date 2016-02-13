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
}
