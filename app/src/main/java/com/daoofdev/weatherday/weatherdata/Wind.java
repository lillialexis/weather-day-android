package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    Wind.java
 * Project: WeatherDay
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

    public Double getSpeedRaw() {
        return mSpeed;
    }

    public Double getSpeed(Constants.SpeedUnits units) {
        return Constants.convertedSpeed(mSpeed, units);
    }

    public String getPrettyDirection() {
        return Constants.prettyDirection(mDeg);
    }

}
