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
import com.daoofdev.weatherday.WeatherData.ForecastItem;
import com.daoofdev.weatherday.WeatherData.ForecastWeatherData;
import com.daoofdev.weatherday.WeatherData.WeatherItem;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = "WeatherDay:MainActivity";

    private CurrentWeatherData mCurrentWeatherData = null;
    private ForecastWeatherData mForecastWeatherData = null;

    private ImageView mIconImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mIconImageView = (ImageView)findViewById(R.id.weather_icon);
    }

    private WeatherMapWrapper.CurrentWeatherFetcherListener currentWeatherFetcherListener = new WeatherMapWrapper.CurrentWeatherFetcherListener() {
        @Override
        public void onCurrentWeatherFetchSucceeded(CurrentWeatherData data) {
            Log.d(TAG, Util.getMethodName());

            //Toast.makeText(MainActivity.this, "Updating current weather...", Toast.LENGTH_SHORT).show();

            mCurrentWeatherData = data;

            for (WeatherItem item : mCurrentWeatherData.getWeatherItems())
                downloadIcon(item);

        }

        @Override
        public void onCurrentWeatherFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            //Toast.makeText(MainActivity.this, "Error getting the current weather.", Toast.LENGTH_SHORT).show();

            mCurrentWeatherData = null;
        }
    };

    private WeatherMapWrapper.ForecastWeatherFetcherListener forecastWeatherFetcherListener = new WeatherMapWrapper.ForecastWeatherFetcherListener() {
        @Override
        public void onForecastWeatherFetchSucceeded(ForecastWeatherData data) {
            Log.d(TAG, Util.getMethodName());

            //Toast.makeText(MainActivity.this, "Updating forecast weather...", Toast.LENGTH_SHORT).show();

            mForecastWeatherData = data;

            for (ForecastItem forecastItem : mForecastWeatherData.getList())
                for (WeatherItem weatherItem : forecastItem.getWeatherItems())
                    downloadIcon(weatherItem);

        }

        @Override
        public void onForecastWeatherFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            //Toast.makeText(MainActivity.this, "Error getting the forecast weather.", Toast.LENGTH_SHORT).show();

            mForecastWeatherData = null;
        }
    };

    private WeatherMapWrapper.IconFetcherListener iconFetcherListener = new WeatherMapWrapper.IconFetcherListener() {
        @Override
        public void onIconFetchSucceeded(WeatherItem item) {
            Log.d(TAG, Util.getMethodName());

            refreshUI();
        }

        @Override
        public void onIconFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            //Toast.makeText(MainActivity.this, "Error getting the weather item's icon.", Toast.LENGTH_SHORT).show();
        }
    };

    private void refreshUI() {
        //mIconImageView.setImageBitmap(mCurrentWeatherData.getWeatherItems().get(0).getIconImage());
    }

    private void downloadIcon(WeatherItem item) {
        if (item != null)
            WeatherMapWrapper.fetchIconForWeatherData(item, iconFetcherListener);
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

        if (location != null && WeatherMapWrapper.canConnectToOpenWeather()) {
            WeatherMapWrapper.fetchCurrentWeatherForLocation(location, currentWeatherFetcherListener);
            WeatherMapWrapper.fetchForecastWeatherForLocation(location, 1, forecastWeatherFetcherListener);
        }
    }
}
