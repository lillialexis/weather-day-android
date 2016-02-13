package com.daoofdev.weatherday.WeatherData2;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    ForecastItem.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;
import java.util.*;

public class ForecastItem {
    private final static String TAG = "WeatherDay:ForecastItem";

    @SerializedName("dt")
    private Long mDt = 0L;

    @SerializedName("pressure")
    private Double mPressure = 0.0;

    @SerializedName("humidity")
    private Integer mHumidity = 0;

    @SerializedName("speed")
    private Double mSpeed = 0.0;

    @SerializedName("deg")
    private Double mDeg = 0.0;

    @SerializedName("clouds")
    private Integer mClouds = 0;

    @SerializedName("rain")
    private Double mRain = 0.0;

    @SerializedName("temp")
    private Temperature mTemperature = null;

    @SerializedName("weather")
    private ArrayList<WeatherItem> mWeatherItems = null;

    public ForecastItem() {
    }

    public Integer getClouds() {
        return mClouds;
    }

    public Double getDeg() {
        return mDeg;
    }

    public Long getDt() {
        return mDt;
    }

    public Integer getHumidity() {
        return mHumidity;
    }

    public ArrayList<WeatherItem> getWeatherItems() {
        return mWeatherItems;
    }

    public Double getPressure() {
        return mPressure;
    }

    public Double getRainRaw() {
        return mRain;
    }

    public Double getRain(Constants.DepthUnits units) {
        return Constants.convertDepth(mRain, units);
    }

    public String getPrettyRain(Constants.DepthUnits units) {
        return new DecimalFormat("#.##").format(getRain(units));
    }

    public Double getSpeedRaw() {
        return mSpeed;
    }

    public Double getSpeed(Constants.SpeedUnits units) {
        return Constants.convertedSpeed(mSpeed, units);
    }

    public String getPrettySpeed(Constants.SpeedUnits units) {
        return new DecimalFormat("#.##").format(getSpeed(units));
    }

    public String getPrettyDirection() {
        return Formatter.prettyDirection(mDeg);
    }

    public Temperature getTemperature() {
        return mTemperature;
    }
}
