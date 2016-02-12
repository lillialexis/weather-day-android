package com.daoofdev.weatherday;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    WeatherMapInterface.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class WeatherMapInterface
{
    private final static String TAG = "WeatherDay:WeatherMapInterface";

    private final static String WEATHER_MAP_API_KEY = "bd70ff217db31d2f80c85e6e09b85dbf";

    private static WeatherMapInterface ourInstance = new WeatherMapInterface();

    public static WeatherMapInterface getInstance() {
        return ourInstance;
    }

    private WeatherMapInterface() {
    }
}
