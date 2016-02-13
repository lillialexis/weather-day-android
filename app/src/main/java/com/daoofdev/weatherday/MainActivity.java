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

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = "WeatherDay:MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (ActivityCompat.checkSelfPermission(WeatherDayApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(WeatherDayApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 9);
        }

        Location location = LocationWrapper.getLastKnownLocation();

        if (location != null)
            Log.d(TAG, String.format("Lat: %f Lon: %f", location.getLatitude(), location.getLongitude()));
        else
            Log.d(TAG, "Location is null");

        if (location != null && WeatherMapWrapper.canConnectToOpenWeather())
            WeatherMapWrapper.fetchCurrentWeatherForLocation(location, new WeatherMapWrapper.WeatherFetcherListener()
            {
                @Override
                public void onWeatherFetchSucceeded(WeatherData data) {
                    Log.d(TAG, Util.getMethodName());
                }

                @Override
                public void onWeatherFetchFailed(Throwable error) {
                    Log.d(TAG, Util.getMethodName());
                }
            });
    }


}
