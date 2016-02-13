package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    Formatter.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/13/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.TimeZone;

public class Formatter {
    private final static String TAG = "WeatherDay:Formatter";

    static String prettyTime(Long time) {
        Date date = new Date(time * 1000);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h.mm");

        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        return simpleDateFormat.format(date);
    }

    static String prettyDirection(Double directionDegrees) {
        if (directionDegrees < 0.0) directionDegrees = 360.0 + directionDegrees;
        if (directionDegrees >= 360.0 - 11.25) directionDegrees = 0.0 - (360.0 - directionDegrees);

        ArrayList<String> directions = new ArrayList<String>(
            Arrays.asList("N", "NNE", "NE", "NEE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"));

        Double testValue = 11.25;
        for (int i = 0; i < 16; i++) {
            if (directionDegrees < testValue)
                return directions.get(i);

                testValue += 22.5;
        }

        return "";
    }
}
