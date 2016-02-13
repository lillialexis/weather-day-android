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
import android.graphics.Bitmap;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daoofdev.weatherday.WeatherData.Clouds;
import com.daoofdev.weatherday.WeatherData.Constants;
import com.daoofdev.weatherday.WeatherData.CurrentWeatherData;
import com.daoofdev.weatherday.WeatherData.ForecastItem;
import com.daoofdev.weatherday.WeatherData.ForecastWeatherData;
import com.daoofdev.weatherday.WeatherData.MainData;
import com.daoofdev.weatherday.WeatherData.System;
import com.daoofdev.weatherday.WeatherData.Temperature;
import com.daoofdev.weatherday.WeatherData.WeatherItem;
import com.daoofdev.weatherday.WeatherData.Wind;

public class MainActivity extends AppCompatActivity
{
    private final static String TAG = "WeatherDay:MainActivity";

    private CurrentWeatherData mCurrentWeatherData = null;
    private ForecastWeatherData mForecastWeatherData = null;

    private TextView  mCurrentlyInLabel;
    private TextView  mCurrentTempLabel;
    private TextView  mCurrentMainDescriptionLabel;
    private TextView  mCurrentDetailedDescriptionLabel;
    private TextView  mCurrentWindSpeedLabel;
    private TextView  mCurrentWindDirectionLabel;
    private TextView  mCurrentCloudsLabel;
    private TextView  mCurrentHumidityLabel;
    private TextView  mForecastTempLabel;
    private TextView  mForecastHighTempLabel;
    private TextView  mForecastLowTempLabel;
    private TextView  mForecastMainDescriptionLabel;
    private TextView  mForecastDetailedDescriptionLabel;
    private TextView  mForecastRainLabel;
    private TextView  mForecastWindSpeedLabel;
    private TextView  mForecastWindDirectionLabel;
    private TextView  mForecastCloudsLabel;
    private TextView  mForecastHumidityLabel;
    private TextView  mForecastPressureLabel;
    private TextView  mSunriseLabel;
    private TextView  mSunsetLabel;
    private ImageView mCurrentWeatherIcon;

    Constants.DepthUnits       mDepthUnits = Constants.DepthUnits.IN;
    Constants.TemperatureUnits mTempUnits  = Constants.TemperatureUnits.FAHRENHEIT;
    Constants.SpeedUnits       mSpeedUnits = Constants.SpeedUnits.MILES_PER_HOUR;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCurrentlyInLabel                 = (TextView)findViewById(R.id.currently_in_label);
        mCurrentTempLabel                 = (TextView)findViewById(R.id.current_temp_label);
        mCurrentMainDescriptionLabel      = (TextView)findViewById(R.id.current_main_description_label);
        mCurrentDetailedDescriptionLabel  = (TextView)findViewById(R.id.current_detailed_description_label);
        mCurrentWindSpeedLabel            = (TextView)findViewById(R.id.current_wind_speed_label);
        mCurrentWindDirectionLabel        = (TextView)findViewById(R.id.current_wind_direction_label);
        mCurrentCloudsLabel               = (TextView)findViewById(R.id.current_clouds_label);
        mCurrentHumidityLabel             = (TextView)findViewById(R.id.current_humidity_label);
        mForecastTempLabel                = (TextView)findViewById(R.id.forecast_temp_label);
        mForecastHighTempLabel            = (TextView)findViewById(R.id.forecast_high_temp_label);
        mForecastLowTempLabel             = (TextView)findViewById(R.id.forecast_low_temp_label);
        mForecastMainDescriptionLabel     = (TextView)findViewById(R.id.forecast_main_description_label);
        mForecastDetailedDescriptionLabel = (TextView)findViewById(R.id.forecast_detailed_description_label);
        mForecastRainLabel                = (TextView)findViewById(R.id.forecast_rain_label);
        mForecastWindSpeedLabel           = (TextView)findViewById(R.id.forecast_wind_speed_label);
        mForecastWindDirectionLabel       = (TextView)findViewById(R.id.forecast_wind_direction_label);
        mForecastCloudsLabel              = (TextView)findViewById(R.id.forecast_clouds_label);
        mForecastHumidityLabel            = (TextView)findViewById(R.id.forecast_humidity_label);
        mForecastPressureLabel            = (TextView)findViewById(R.id.forecast_pressure_label);
        mSunriseLabel                     = (TextView)findViewById(R.id.sunrise_label);
        mSunsetLabel                      = (TextView)findViewById(R.id.sunset_label);

        mCurrentWeatherIcon = (ImageView)findViewById(R.id.weather_icon);
    }

    private WeatherMapWrapper.CurrentWeatherFetcherListener currentWeatherFetcherListener = new WeatherMapWrapper.CurrentWeatherFetcherListener() {
        @Override
        public void onCurrentWeatherFetchSucceeded(CurrentWeatherData data) {
            Log.d(TAG, Util.getMethodName());

            Toast.makeText(MainActivity.this, "Updating current weather...", Toast.LENGTH_SHORT).show();

            mCurrentWeatherData = data;

            for (WeatherItem item : mCurrentWeatherData.getWeatherItems())
                downloadIcon(item);

            refreshUI();
        }

        @Override
        public void onCurrentWeatherFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            Toast.makeText(MainActivity.this, "Error getting the current weather.", Toast.LENGTH_SHORT).show();

            mCurrentWeatherData = null;
        }
    };

    private WeatherMapWrapper.ForecastWeatherFetcherListener forecastWeatherFetcherListener = new WeatherMapWrapper.ForecastWeatherFetcherListener() {
        @Override
        public void onForecastWeatherFetchSucceeded(ForecastWeatherData data) {
            Log.d(TAG, Util.getMethodName());

            Toast.makeText(MainActivity.this, "Updating forecast weather...", Toast.LENGTH_SHORT).show();

            mForecastWeatherData = data;

            for (ForecastItem forecastItem : mForecastWeatherData.getList())
                for (WeatherItem weatherItem : forecastItem.getWeatherItems())
                    downloadIcon(weatherItem);

            refreshUI();
        }

        @Override
        public void onForecastWeatherFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            Toast.makeText(MainActivity.this, "Error getting the forecast weather.", Toast.LENGTH_SHORT).show();

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

            Toast.makeText(MainActivity.this, "Error getting the weather item's icon.", Toast.LENGTH_SHORT).show();
        }
    };

    private void setTextForTextViewAndShow(String formatter, Object value, String unitStr, TextView textView) {
        if (value != null) {
            textView.setText(String.format(formatter, value.toString(), unitStr));
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setText("");
            textView.setVisibility(View.GONE);
        }
    }

    private void setImageForImageViewAndShow(Bitmap bitmap, ImageView imageView) {
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }
    }

    private void refreshUI() {

        MainData mainData;
        System system;
        Wind wind;
        Clouds clouds;
        Temperature temperature;

        if (mCurrentWeatherData != null) {
            setTextForTextViewAndShow("Currently in %s%s", mCurrentWeatherData.getName(), "", mCurrentlyInLabel);

            if ((mainData = mCurrentWeatherData.getMainData()) != null) {
                setTextForTextViewAndShow("%s°%s", mainData.getTemp(mTempUnits), mTempUnits.toString(), mCurrentTempLabel);
                setTextForTextViewAndShow("%s%s Humidity", mainData.getHumidity(), "%", mCurrentHumidityLabel);
            }

            if (mCurrentWeatherData.getWeatherItems() != null && mCurrentWeatherData.getWeatherItems().get(0) != null) {
                WeatherItem weatherItem = mCurrentWeatherData.getWeatherItems().get(0);

                setTextForTextViewAndShow("%s%s", weatherItem.getMain(), "", mCurrentMainDescriptionLabel);
                setTextForTextViewAndShow("%s%s", weatherItem.getDescription(), "", mCurrentDetailedDescriptionLabel);

                setImageForImageViewAndShow(weatherItem.getIconImage(), mCurrentWeatherIcon);
            }

            if ((wind = mCurrentWeatherData.getWind()) != null) {
                setTextForTextViewAndShow("Wind: %s %s", wind.getSpeed(mSpeedUnits), mSpeedUnits.toString(), mCurrentWindSpeedLabel);
                setTextForTextViewAndShow("%s%s", wind.getPrettyDirection(), "", mCurrentWindDirectionLabel);
            }

            if ((clouds = mCurrentWeatherData.getClouds()) != null) {
                setTextForTextViewAndShow("Clouds: %s%s", clouds.getAll(), "%", mCurrentCloudsLabel);
            }

            if ((system = mCurrentWeatherData.getSystem()) != null) {
                setTextForTextViewAndShow("Sunrise: %s %s", system.getSunrise(), "AM", mSunriseLabel);
                setTextForTextViewAndShow("Sunset: %s %s", system.getSunset(), "PM", mSunsetLabel);
            }
        }

        if (mForecastWeatherData != null) {
            if (mForecastWeatherData.getList() != null && mForecastWeatherData.getList().get(0) != null) {
                ForecastItem forecastItem = mForecastWeatherData.getList().get(0);

                if ((temperature = forecastItem.getTemperature()) != null) {
                    setTextForTextViewAndShow("%s°%s", temperature.getDay(mTempUnits), mTempUnits.toString(), mForecastTempLabel);
                    setTextForTextViewAndShow("High: %s°%s", temperature.getMax(mTempUnits), mTempUnits.toString(), mForecastHighTempLabel);
                    setTextForTextViewAndShow("Low: %s°%s", temperature.getMin(mTempUnits), mTempUnits.toString(), mForecastLowTempLabel);
                }

                setTextForTextViewAndShow("%s%s Humidity", forecastItem.getHumidity(), "%", mForecastHumidityLabel);

                if (forecastItem.getWeatherItems() != null && forecastItem.getWeatherItems().get(0) != null) {
                    WeatherItem weatherItem = forecastItem.getWeatherItems().get(0);

                    setTextForTextViewAndShow("%s%s", weatherItem.getMain(), "", mForecastMainDescriptionLabel);
                    setTextForTextViewAndShow("%s%s", weatherItem.getDescription(), "", mForecastDetailedDescriptionLabel);
                }

                setTextForTextViewAndShow("Wind: %s %s", forecastItem.getSpeed(mSpeedUnits), mSpeedUnits.toString(), mForecastWindSpeedLabel);
                setTextForTextViewAndShow("%s%s", forecastItem.getPrettyDirection(), "", mForecastWindDirectionLabel);
                setTextForTextViewAndShow("Clouds: %s%s", forecastItem.getClouds(), "%", mForecastCloudsLabel);
                setTextForTextViewAndShow("Clouds: %s%s", forecastItem.getRain(mDepthUnits), mDepthUnits.toString(), mForecastRainLabel);

                setTextForTextViewAndShow("%s%s", null, "", mForecastPressureLabel);

            }
        }
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
