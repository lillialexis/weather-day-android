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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.daoofdev.weatherday.WeatherData2.CurrentWeatherData;
import com.daoofdev.weatherday.WeatherData2.ForecastWeatherData;
import com.daoofdev.weatherday.WeatherData2.WeatherItem;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@SuppressLint("LongLogTag")
public class WeatherMapWrapper
{
    private final static String TAG = "WeatherDay:WeatherMapWrapper";

    private final static String WEATHER_MAP_API_KEY = "bd70ff217db31d2f80c85e6e09b85dbf";

    private static final String BASE_URL = "http://api.openweathermap.org/";

    private static final String GET_CURRENT_WEATHER_METHOD_QUERY  = "data/2.5/weather?";
    private static final String GET_FORECAST_WEATHER_METHOD_QUERY = "data/2.5/forecast/daily?";
    private static final String GET_ICON_METHOD_QUERY             = "img/w/";

    private static final String LAT_QUERY = "lat=";
    private static final String LON_QUERY = "&lon=";
    private static final String CNT_QUERY = "&cnt=";
    private static final String API_QUERY = "&appid=";

    private static final String METHOD_KEY_GET_CURRENT_WEATHER  = "METHOD_KEY_GET_CURRENT_WEATHER";
    private static final String METHOD_KEY_GET_FORECAST_WEATHER = "METHOD_KEY_GET_FORECAST_WEATHER";
    private static final String METHOD_KEY_GET_ICON             = "METHOD_KEY_GET_ICON";

    private static final String USER_INFO_METHOD_KEY_KEY   = "USER_INFO_METHOD_KEY_KEY";
    private static final String USER_INFO_LISTENER_KEY     = "USER_INFO_LISTENER_KEY";
    private static final String USER_INFO_WEATHER_ITEM_KEY = "USER_INFO_WEATHER_ITEM_KEY";

    public interface CurrentWeatherFetcherListener
    {
        void onCurrentWeatherFetchSucceeded(CurrentWeatherData data);

        void onCurrentWeatherFetchFailed(Throwable error);
    }

    public interface ForecastWeatherFetcherListener
    {
        void onForecastWeatherFetchSucceeded(ForecastWeatherData data);

        void onForecastWeatherFetchFailed(Throwable error);
    }

    public interface IconFetcherListener
    {
        void onIconFetchSucceeded(WeatherItem item);

        void onIconFetchFailed(Throwable error);
    }

    /**
     * Tells whether the WeatherMapWrapper can connect to the OpenWeatherMap API (though I really only check if the network is available).
     * @return True, if the wrapper can connect to the OpenWeatherMap API, false otherwise.
     */
    public static Boolean canConnectToOpenWeather() {
        Log.d(TAG, Util.getMethodName());

        ConnectivityManager connectivityManager = (ConnectivityManager)WeatherDayApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    /**
     * Get's the CurrentWeatherData object from the OpenWeatherMap API for the provided location.
     * @param location The location for which the weather is fetched. Can't be null.
     * @param listener The listener notified when the fetch failed or succeeded.
     * @throws IllegalArgumentException if location is null.
     */
    public static void fetchCurrentWeatherForLocation(Location location, CurrentWeatherFetcherListener listener) throws IllegalArgumentException {
        Log.d(TAG, Util.getMethodName());

        if (location == null) throw new IllegalArgumentException("Location can't be null");

        /* Create the url for this request. */
        String url = BASE_URL + GET_CURRENT_WEATHER_METHOD_QUERY +
                LAT_QUERY + location.getLatitude() +
                LON_QUERY + location.getLongitude() +
                API_QUERY + WEATHER_MAP_API_KEY;

        /* Create a HashMap to store my method key and the delegate. */
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put(USER_INFO_METHOD_KEY_KEY, METHOD_KEY_GET_CURRENT_WEATHER);
        userInfo.put(USER_INFO_LISTENER_KEY, listener);

        /* Create a new DataFetcher Object, passing in a HashMap containing the specific method and specific delegate, then invoke the URL. */
        new DataFetcher(userInfo, dataFetcherListener).execute(url);
    }

    /**
     * Get's the ForecastWeatherData object from the OpenWeatherMap API for the provided location.
     * @param location The location for which the weather is fetched. Can't be null.
     * @param count The number of days you wanted fetched
     * @param listener The listener notified when the fetch failed or succeeded.
     * @throws IllegalArgumentException if location is null.
     */
    public static void fetchForecastWeatherForLocation(Location location, Integer count, ForecastWeatherFetcherListener listener) throws IllegalArgumentException {
        Log.d(TAG, Util.getMethodName());

        if (location == null) throw new IllegalArgumentException("Location can't be null");

        /* Create the url for this request. */
        String url = BASE_URL + GET_FORECAST_WEATHER_METHOD_QUERY +
                LAT_QUERY + location.getLatitude() +
                LON_QUERY + location.getLongitude() +
                CNT_QUERY + count.toString() +
                API_QUERY + WEATHER_MAP_API_KEY;

        /* Create a HashMap to store my method key and the delegate. */
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put(USER_INFO_METHOD_KEY_KEY, METHOD_KEY_GET_FORECAST_WEATHER);
        userInfo.put(USER_INFO_LISTENER_KEY, listener);

        /* Create a new DataFetcher Object, passing in a HashMap containing the specific method and specific delegate, then invoke the URL. */
        new DataFetcher(userInfo, dataFetcherListener).execute(url);
    }

    /**
     * Get's the icon file for the CurrentWeatherData object, from the OpenWeatherMap API.
     * @param weatherItem The weather item object for which the icon file is being downloaded
     * @param listener    The listener notified when the fetch failed or succeeded.
     * @throws IllegalArgumentException if location is null.
     */
    public static void fetchIconForWeatherData(WeatherItem weatherItem, IconFetcherListener listener) throws IllegalArgumentException {
        Log.d(TAG, Util.getMethodName());

        if (weatherItem == null) throw new IllegalArgumentException("Weather Item can't be null");

        /* Create the url for this request. */
        String url = BASE_URL + GET_ICON_METHOD_QUERY +
                weatherItem.getIconIdStr();

        /* Create a HashMap to store my method key, WeatherItem, and the delegate. */
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put(USER_INFO_METHOD_KEY_KEY, METHOD_KEY_GET_ICON);
        userInfo.put(USER_INFO_WEATHER_ITEM_KEY, weatherItem);
        userInfo.put(USER_INFO_LISTENER_KEY, listener);

        /* Create a new DataFetcher Object, passing in a HashMap containing the stuffs, then invoke the URL. */
        new DataFetcher(userInfo, dataFetcherListener).execute(url);
    }

    private static DataFetcher.DataFetcherListener dataFetcherListener = new DataFetcher.DataFetcherListener()
    {
        @Override
        public void onDidFetchData(Object userInfo, byte[] data) {
            String methodKey = (String)((HashMap)userInfo).get(USER_INFO_METHOD_KEY_KEY);

            switch (methodKey) {

                /* In this case, it's a json string representing the current weather data. */
                case METHOD_KEY_GET_CURRENT_WEATHER:

                    /* Get our listener from the userInfo object. */
                    CurrentWeatherFetcherListener currentWeatherFetcherListener = (CurrentWeatherFetcherListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    /* Decode the bytes into a json string, then deserialize into a WeatherData object. */
                    try {
                        String jsonString = new String(data, "UTF-8");

                        Log.d(TAG, "Current weather data: " + jsonString);

                        Gson gson = new Gson();
                        CurrentWeatherData weatherData = gson.fromJson(jsonString, CurrentWeatherData.class);

                        /* Success. Call our listener. */
                        if (currentWeatherFetcherListener != null)
                            currentWeatherFetcherListener.onCurrentWeatherFetchSucceeded(weatherData);
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                        /* Something went wrong. Call our listener. */
                        if (currentWeatherFetcherListener != null)
                            currentWeatherFetcherListener.onCurrentWeatherFetchFailed(e);
                    }

                    break;

                /* In this case, it's a json string representing the current weather data. */
                case METHOD_KEY_GET_FORECAST_WEATHER:

                    /* Get our listener from the userInfo object. */
                    ForecastWeatherFetcherListener forecastWeatherFetcherListener = (ForecastWeatherFetcherListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    /* Decode the bytes into a json string, then deserialize into a WeatherData object. */
                    try {
                        String jsonString = new String(data, "UTF-8");

                        Log.d(TAG, "Forecast weather data: " + jsonString);

                        Gson gson = new Gson();
                        ForecastWeatherData weatherData = gson.fromJson(jsonString, ForecastWeatherData.class);

                        /* Success. Call our listener. */
                        if (forecastWeatherFetcherListener != null)
                            forecastWeatherFetcherListener.onForecastWeatherFetchSucceeded(weatherData);
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                        /* Something went wrong. Call our listener. */
                        if (forecastWeatherFetcherListener != null)
                            forecastWeatherFetcherListener.onForecastWeatherFetchFailed(e);
                    }

                    break;

                /* In this case, it's an icon file for the weather data object. */
                case METHOD_KEY_GET_ICON:

                    /* Get our WeatherItem and listener from the userInfo object. */
                    WeatherItem weatherItem = (WeatherItem)((HashMap)userInfo).get(USER_INFO_WEATHER_ITEM_KEY);
                    IconFetcherListener iconFetcherListener = (IconFetcherListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    weatherItem.setIconImage(BitmapFactory.decodeByteArray(data, 0, data.length));

                    /* Success. Call our listener. */
                    if (iconFetcherListener != null)
                        iconFetcherListener.onIconFetchSucceeded(weatherItem);

                    break;

                default: break;
            }
        }

        @Override
        public void onRequestDidFail(Object userInfo, Throwable error) {
            String methodKey = (String)((HashMap)userInfo).get(USER_INFO_METHOD_KEY_KEY);

            switch (methodKey) {

                /* In this case, call the listener's onCurrentWeatherFetchFailed method. */
                case METHOD_KEY_GET_CURRENT_WEATHER:

                    /* Get our listener from the userInfo object, and call it. */
                    CurrentWeatherFetcherListener currentWeatherFetcherListener = (CurrentWeatherFetcherListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    if (currentWeatherFetcherListener != null)
                        currentWeatherFetcherListener.onCurrentWeatherFetchFailed(error);

                    break;

                /* In this case, call the listener's onForecastWeatherFetchFailed method. */
                case METHOD_KEY_GET_FORECAST_WEATHER:

                    /* Get our listener from the userInfo object, and call it. */
                    ForecastWeatherFetcherListener forecastWeatherFetcherListener = (ForecastWeatherFetcherListener) ((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    if (forecastWeatherFetcherListener != null)
                        forecastWeatherFetcherListener.onForecastWeatherFetchFailed(error);

                    break;

                /* In this case, call the listener's onIconFetchFailed method. */
                case METHOD_KEY_GET_ICON:

                    /* Get our listener from the userInfo object, and call it. */
                    IconFetcherListener iconFetcherListener = (IconFetcherListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    if (iconFetcherListener != null)
                        iconFetcherListener.onIconFetchFailed(error);

                    break;

                default: break;
            }
        }
    };
}
