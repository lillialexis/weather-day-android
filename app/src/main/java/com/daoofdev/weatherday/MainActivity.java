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
import android.widget.ImageView;
import android.widget.Toast;

import com.daoofdev.weatherday.WeatherData.CurrentWeatherData;
import com.daoofdev.weatherday.WeatherData.WeatherItem;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = "WeatherDay:MainActivity";

    private ImageView mIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIconImageView = (ImageView)findViewById(R.id.weather_icon);
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

        if (location == null) {
            Log.d(TAG, "Location is null");

            Toast.makeText(this, "Can't get the current weather: Location unknown.", Toast.LENGTH_SHORT).show();


        } else {
            Log.d(TAG, String.format("Lat: %f Lon: %f", location.getLatitude(), location.getLongitude()));
        }

        Toast.makeText(this, "Getting the current weather...", Toast.LENGTH_SHORT).show();

        if (location != null && WeatherMapWrapper.canConnectToOpenWeather())
            WeatherMapWrapper.fetchCurrentWeatherForLocation(location, new WeatherMapWrapper.WeatherFetcherListener()
            {
                @Override
                public void onCurrentWeatherFetchSucceeded(CurrentWeatherData data) {
                    Log.d(TAG, Util.getMethodName());

                    Toast.makeText(MainActivity.this, "Updating...", Toast.LENGTH_SHORT).show();

                    WeatherItem item = data.getWeatherItems().get(0);

                    if (item != null)
                        WeatherMapWrapper.fetchIconForWeatherData(item, new WeatherMapWrapper.IconFetcherListener()
                        {
                            @Override
                            public void onIconFetchSucceeded(WeatherItem item) {
                                Log.d(TAG, Util.getMethodName());

                                mIconImageView.setImageBitmap(item.getIconImage());
                            }

                            @Override
                            public void onIconFetchFailed(Throwable error) {
                                Log.d(TAG, Util.getMethodName());

                                Toast.makeText(MainActivity.this, "Error getting the current weather.", Toast.LENGTH_SHORT).show();
                            }
                        });
                }

                @Override
                public void onCurrentWeatherFetchFailed(Throwable error) {
                    Log.d(TAG, Util.getMethodName());

                    Toast.makeText(MainActivity.this, "Error getting the current weather.", Toast.LENGTH_SHORT).show();
                }
            });
    }


}
