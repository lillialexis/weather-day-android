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
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private final static String TAG = "WeatherDay:MainActivity";

    private final static String SHARED_PREFS_KEY = "com.daoofdev.weatherday.SHARED_PREFS_KEY";
    private final static String UNITS_PREFS_KEY = "UNITS_PREFS_KEY";

    private CurrentWeatherData mCurrentWeatherData = null;
    private ForecastWeatherData mForecastWeatherData = null;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private ImageView mBackgroundImage;

    private View mCurrentWeatherLayout;
    private View mForecastWeatherLayout;

    private TextView  mCurrentlyInLabel;
    private TextView  mCurrentTempLabel;
    private TextView  mCurrentMainDescriptionLabel;
    private TextView  mCurrentDetailedDescriptionLabel;
    private TextView  mCurrentWindSpeedLabel;
    private TextView  mCurrentWindDirectionLabel;
    private TextView  mCurrentCloudsLabel;
    private TextView  mCurrentHumidityLabel;
    private TextView  mCurrentPressureLabel;
    private TextView  mForecastTodayLabel;
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
    private ImageView mForecastWeatherIcon;

    private TextView  mRefreshingLabel;

    private RadioGroup  mUnitsRadioGroup;
    private RadioButton mImperialRadioButton;
    private RadioButton mMetricRadioButton;

    private Units mUnits;
    private Constants.DepthUnits       mDepthUnits = Constants.DepthUnits.IN;
    private Constants.TemperatureUnits mTempUnits  = Constants.TemperatureUnits.FAHRENHEIT;
    private Constants.SpeedUnits       mSpeedUnits = Constants.SpeedUnits.MILES_PER_HOUR;

    private boolean mForecastWeatherDataCallbackReceived = true;
    private boolean mCurrentWeatherDataCallbackReceived  = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSwipeRefreshLayout    = (SwipeRefreshLayout)findViewById(R.id.swipe_container);

        mCurrentWeatherLayout  = findViewById(R.id.current_weather_relative_layout);
        mForecastWeatherLayout = findViewById(R.id.forecast_weather_relative_layout);

        mBackgroundImage = (ImageView)findViewById(R.id.background_image);

        mCurrentlyInLabel                 = (TextView)findViewById(R.id.currently_in_label);
        mCurrentTempLabel                 = (TextView)findViewById(R.id.current_temp_label);
        mCurrentMainDescriptionLabel      = (TextView)findViewById(R.id.current_main_description_label);
        mCurrentDetailedDescriptionLabel  = (TextView)findViewById(R.id.current_detailed_description_label);
        mCurrentWindSpeedLabel            = (TextView)findViewById(R.id.current_wind_speed_label);
        mCurrentWindDirectionLabel        = (TextView)findViewById(R.id.current_wind_direction_label);
        mCurrentCloudsLabel               = (TextView)findViewById(R.id.current_clouds_label);
        mCurrentHumidityLabel             = (TextView)findViewById(R.id.current_humidity_label);
        mCurrentPressureLabel             = (TextView)findViewById(R.id.current_pressure_label);
        mForecastTodayLabel               = (TextView)findViewById(R.id.forecast_today_label);
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

        mCurrentWeatherIcon  = (ImageView)findViewById(R.id.current_weather_icon);
        mForecastWeatherIcon = (ImageView)findViewById(R.id.forecast_weather_icon);

        mRefreshingLabel     = (TextView)findViewById(R.id.refreshing_label);

        mUnitsRadioGroup     = (RadioGroup)  findViewById(R.id.units_radio_group);
        mImperialRadioButton = (RadioButton) findViewById(R.id.imperial_units_radio);
        mMetricRadioButton   = (RadioButton) findViewById(R.id.metric_units_radio);

        mSwipeRefreshLayout.setOnRefreshListener(this);

        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                                                    android.R.color.holo_green_light,
                                                    android.R.color.holo_orange_light,
                                                    android.R.color.holo_red_light);

        Units units = loadUnits();
        if (units == Units.IMPERIAL) {
            mImperialRadioButton.setChecked(true);
            mMetricRadioButton.setChecked(false);
        } else {
            mImperialRadioButton.setChecked(false);
            mMetricRadioButton.setChecked(true);
        }
        updateUnits(units);
    }

    private WeatherMapWrapper.CurrentWeatherFetcherListener currentWeatherFetcherListener = new WeatherMapWrapper.CurrentWeatherFetcherListener() {
        @Override
        public void onCurrentWeatherFetchSucceeded(CurrentWeatherData data) {
            Log.d(TAG, Util.getMethodName());

            //Toast.makeText(MainActivity.this, "Updating current weather...", Toast.LENGTH_SHORT).show();

            mCurrentWeatherData = data;

            for (WeatherItem item : mCurrentWeatherData.getWeatherItems())
                downloadIcon(item);

            mCurrentWeatherDataCallbackReceived = true;
            updateUserInterface();
        }

        @Override
        public void onCurrentWeatherFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            Toast.makeText(MainActivity.this, "Error getting the current weather.", Toast.LENGTH_SHORT).show();

            mCurrentWeatherData = null;

            mCurrentWeatherDataCallbackReceived = true;
            updateUserInterface();
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

            mForecastWeatherDataCallbackReceived = true;
            updateUserInterface();
        }

        @Override
        public void onForecastWeatherFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            Toast.makeText(MainActivity.this, "Error getting the forecast weather.", Toast.LENGTH_SHORT).show();

            mForecastWeatherData = null;

            mForecastWeatherDataCallbackReceived = true;
            updateUserInterface();
        }
    };

    private WeatherMapWrapper.IconFetcherListener iconFetcherListener = new WeatherMapWrapper.IconFetcherListener() {
        @Override
        public void onIconFetchSucceeded(WeatherItem item) {
            Log.d(TAG, Util.getMethodName());

            updateUserInterface();
        }

        @Override
        public void onIconFetchFailed(Throwable error) {
            Log.d(TAG, Util.getMethodName());

            Toast.makeText(MainActivity.this, "Error getting the weather item's icon.", Toast.LENGTH_SHORT).show();
        }
    };

    private enum Units {
        METRIC("METRIC"),
        IMPERIAL("IMPERIAL");
        private final String mIdentifier;
        Units(String identifier) {
            mIdentifier = identifier;
        }
        public String toString() { return mIdentifier; }
        public static Units fromString(String string) { if (string.equals("METRIC")) return METRIC; else return IMPERIAL; }
    }

    private void saveUnits(Units units) {
        SharedPreferences sharedPref = this.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(UNITS_PREFS_KEY, units.toString());
        editor.commit();
    }

    private Units loadUnits() {
        SharedPreferences sharedPref = this.getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE);
        return Units.fromString(sharedPref.getString(UNITS_PREFS_KEY, "IMPERIAL"));
    }

    private void updateUnits(Units units) {
        if (units == Units.METRIC) {
            mDepthUnits = Constants.DepthUnits.CM;
            mSpeedUnits = Constants.SpeedUnits.METERS_PER_SEC;
            mTempUnits  = Constants.TemperatureUnits.CELSIUS;
        } else {
            mDepthUnits = Constants.DepthUnits.IN;
            mSpeedUnits = Constants.SpeedUnits.MILES_PER_HOUR;
            mTempUnits  = Constants.TemperatureUnits.FAHRENHEIT;
        }

        saveUnits(units);
    }

    public void onToggleUnits(View view) {
        if (view == mImperialRadioButton) updateUnits(Units.IMPERIAL);
        else                              updateUnits(Units.METRIC);

        updateUserInterface();
    }

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

    private void updateUserInterface() {
        MainData mainData;
        System system;
        Wind wind;
        Clouds clouds;
        Temperature temperature;

        /* Update the UI components with the current weather data */
        if (mCurrentWeatherData != null) {
            setTextForTextViewAndShow("Currently in %s%s", mCurrentWeatherData.getName(), "", mCurrentlyInLabel);

            if ((mainData = mCurrentWeatherData.getMainData()) != null) {
                setTextForTextViewAndShow("%s°%s", mainData.getPrettyTemp(mTempUnits), mTempUnits.toString(), mCurrentTempLabel);
                setTextForTextViewAndShow("Humidity: %s%s", mainData.getHumidity(), "%", mCurrentHumidityLabel);
                setTextForTextViewAndShow("%s%s", null, "", mCurrentPressureLabel); /* Hide pressure for now */
            }

            if (mCurrentWeatherData.getWeatherItems() != null && mCurrentWeatherData.getWeatherItems().get(0) != null) {
                WeatherItem weatherItem = mCurrentWeatherData.getWeatherItems().get(0);

                setTextForTextViewAndShow("%s%s", weatherItem.getMain(), "", mCurrentMainDescriptionLabel);
                setTextForTextViewAndShow("%s%s", weatherItem.getDescription(), "", mCurrentDetailedDescriptionLabel);

                setImageForImageViewAndShow(weatherItem.getIconImage(), mCurrentWeatherIcon);
            }

            if ((wind = mCurrentWeatherData.getWind()) != null) {
                setTextForTextViewAndShow("Wind: %s %s", wind.getPrettySpeed(mSpeedUnits), mSpeedUnits.toString(), mCurrentWindSpeedLabel);
                setTextForTextViewAndShow(" %s%s", wind.getPrettyDirection(), "", mCurrentWindDirectionLabel);
            }

            if ((clouds = mCurrentWeatherData.getClouds()) != null) {
                setTextForTextViewAndShow("Clouds: %s%s", clouds.getAll(), "%", mCurrentCloudsLabel);
            }

            if ((system = mCurrentWeatherData.getSystem()) != null) {
                setTextForTextViewAndShow("Sunrise: %s %s", system.getPrettySunrise(), "AM", mSunriseLabel);
                setTextForTextViewAndShow("Sunset: %s %s", system.getPrettySunset(), "PM", mSunsetLabel);
            }
        }

        /* Update the UI components with the forecast weather data */
        if (mForecastWeatherData != null) {
            setTextForTextViewAndShow("Today:%s%s", "", "", mForecastTodayLabel);

            if (mForecastWeatherData.getList() != null && mForecastWeatherData.getList().get(0) != null) {
                ForecastItem forecastItem = mForecastWeatherData.getList().get(0);

                if ((temperature = forecastItem.getTemperature()) != null) {
                    setTextForTextViewAndShow("%s°%s", temperature.getPrettyDay(mTempUnits), mTempUnits.toString(), mForecastTempLabel);
                    setTextForTextViewAndShow("High: %s°%s", temperature.getPrettyMax(mTempUnits), mTempUnits.toString(), mForecastHighTempLabel);
                    setTextForTextViewAndShow("Low: %s°%s", temperature.getPrettyMin(mTempUnits), mTempUnits.toString(), mForecastLowTempLabel);
                }

                setTextForTextViewAndShow("Humidity: %s%s", forecastItem.getHumidity(), "%", mForecastHumidityLabel);

                if (forecastItem.getWeatherItems() != null && forecastItem.getWeatherItems().get(0) != null) {
                    WeatherItem weatherItem = forecastItem.getWeatherItems().get(0);

                    setTextForTextViewAndShow("%s%s", weatherItem.getMain(), "", mForecastMainDescriptionLabel);
                    setTextForTextViewAndShow("%s%s", weatherItem.getDescription(), "", mForecastDetailedDescriptionLabel);

                    setImageForImageViewAndShow(weatherItem.getIconImage(), mForecastWeatherIcon);
                }

                setTextForTextViewAndShow("Wind: %s %s", forecastItem.getPrettySpeed(mSpeedUnits), mSpeedUnits.toString(), mForecastWindSpeedLabel);
                setTextForTextViewAndShow(" %s%s", forecastItem.getPrettyDirection(), "", mForecastWindDirectionLabel);
                setTextForTextViewAndShow("Clouds: %s%s", forecastItem.getClouds(), "%", mForecastCloudsLabel);
                setTextForTextViewAndShow("Rain: %s %s", forecastItem.getPrettyRain(mDepthUnits), mDepthUnits.toString(), mForecastRainLabel);

                setTextForTextViewAndShow("%s%s", null, "", mForecastPressureLabel); /* Hide pressure for now */

            }
        }

        if (mCurrentWeatherDataCallbackReceived && mForecastWeatherDataCallbackReceived) {
            mSwipeRefreshLayout.setRefreshing(false);

            if (mCurrentWeatherData == null && mForecastWeatherData == null) { /* Then there was an error */
                mRefreshingLabel.setText("There was an error retrieving weather data.");

            } else {
                mRefreshingLabel.setVisibility(View.GONE);

                mCurrentWeatherLayout.setVisibility(View.VISIBLE);
                mForecastWeatherLayout.setVisibility(View.VISIBLE);
                mUnitsRadioGroup.setVisibility(View.VISIBLE);
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

        LocationWrapper.startGettingLocation();

        /* Do initial refresh w animated spinner */
        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() {
                 mSwipeRefreshLayout.setRefreshing(true);
            }
        });

        onRefresh();
    }

    @Override
    protected void onStop() {
        super.onStop();

        LocationWrapper.stopGettingLocation();
    }

    @Override
    public void onRefresh() {
        mCurrentWeatherData = null;
        mForecastWeatherData = null;

        mRefreshingLabel.setText("Refreshing...");
        mRefreshingLabel.setVisibility(View.VISIBLE);

        mCurrentWeatherLayout.setVisibility(View.GONE);
        mForecastWeatherLayout.setVisibility(View.GONE);
        mUnitsRadioGroup.setVisibility(View.GONE);

        if (ActivityCompat.checkSelfPermission(WeatherDayApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(WeatherDayApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 9);
        }

        Location location = LocationWrapper.getLastKnownLocation();
        if (location == null) {
            errorRefreshing("Can't get the current weather: Location unknown.");
            return;
        }

        Log.d(TAG, String.format("Lat: %f Lon: %f", location.getLatitude(), location.getLongitude()));

        //Toast.makeText(this, "Getting the current weather...", Toast.LENGTH_SHORT).show();

        if (!WeatherMapWrapper.canConnectToOpenWeather()) {
            errorRefreshing("Can't get the current weather: Can't connect to server.");
            return;
        }

        mCurrentWeatherDataCallbackReceived = false;
        mForecastWeatherDataCallbackReceived = false;

        WeatherMapWrapper.fetchCurrentWeatherForLocation(location, currentWeatherFetcherListener);
        WeatherMapWrapper.fetchForecastWeatherForLocation(location, 1, forecastWeatherFetcherListener);
    }

    private void errorRefreshing(String message) {
        Log.e(TAG, message);

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

        mCurrentWeatherDataCallbackReceived = true;
        mForecastWeatherDataCallbackReceived = true;

        mSwipeRefreshLayout.post(new Runnable() {
            @Override public void run() { /* Because Android is stupid... */
                 mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        updateUserInterface();
    }
}
