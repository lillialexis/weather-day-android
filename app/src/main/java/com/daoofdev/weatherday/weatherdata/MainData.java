package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    MainData.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;

import java.text.DecimalFormat;

public class MainData {
    private final static String TAG = "WeatherDay:MainData";

    @SerializedName("temp")
    private Double mTemp = 0.0;

    @SerializedName("pressure")
    private Double mPressure = 0.0;

    @SerializedName("humidity")
    private Integer mHumidity = 0;

    @SerializedName("temp_min")
    private Double mTempMin = 0.0;

    @SerializedName("temp_max")
    private Double mTempMax = 0.0;

    @SerializedName("sea_level")
    private Double mSeaLevel = 0.0;

    @SerializedName("grnd_level")
    private Double mGroundLevel = 0.0;

    public MainData() {
    }

    public Double getGroundLevel() {
        return mGroundLevel;
    }

    public Integer getHumidity() {
        return mHumidity;
    }

    public Double getPressure() {
        return mPressure;
    }

    public Double getSeaLevel() {
        return mSeaLevel;
    }

    public Double getTempRaw() {
        return mTemp;
    }

    public Double getTempMaxRaw() {
        return mTempMax;
    }

    public Double getTempMinRaw() {
        return mTempMin;
    }

    public Double getTemp(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mTemp, units);
    }

    public String getPrettyTemp(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getTemp(units));
    }

    public Double getTempMax(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mTempMax, units);
    }

    public String getPrettyTempMax(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getTempMax(units));
    }

    public Double getTempMin(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mTempMin, units);
    }

    public String getPrettyTempMin(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getTempMin(units));
    }

}
