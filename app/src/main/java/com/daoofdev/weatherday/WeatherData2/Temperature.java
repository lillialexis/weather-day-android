package com.daoofdev.weatherday.WeatherData2;
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

import java.text.DecimalFormat;

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

    public Double getDayRaw() {
        return mDay;
    }

    public Double getEveRaw() {
        return mEve;
    }

    public Double getMaxRaw() {
        return mMax;
    }

    public Double getMinRaw() {
        return mMin;
    }

    public Double getMornRaw() {
        return mMorn;
    }

    public Double getNightRaw() {
        return mNight;
    }

    public Double getDay(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mDay, units);
    }

    public String getPrettyDay(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getDay(units));
    }

    public Double getEve(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mEve, units);
    }

    public String getPrettyEve(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getEve(units));
    }

    public Double getMax(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mMax, units);
    }

    public String getPrettyMax(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getMax(units));
    }

    public Double getMin(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mMin, units);
    }

    public String getPrettyMin(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getMin(units));
    }

    public Double getMorn(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mMorn, units);
    }

    public String getPrettyMorn(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getMorn(units));
    }

    public Double getNight(Constants.TemperatureUnits units) {
        return Constants.convertedTemp(mNight, units);
    }

    public String getPrettyNight(Constants.TemperatureUnits units) {
        return new DecimalFormat("#.##").format(getNight(units));
    }
}
