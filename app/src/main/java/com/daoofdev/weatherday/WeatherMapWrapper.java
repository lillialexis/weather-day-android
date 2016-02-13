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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.daoofdev.weatherday.WeatherData.CurrentWeatherData;
import com.daoofdev.weatherday.WeatherData.WeatherItem;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@SuppressLint("LongLogTag")
public class WeatherMapWrapper
{
    private final static String TAG = "WeatherDay:WeatherMapInterface";

    private final static String WEATHER_MAP_API_KEY = "bd70ff217db31d2f80c85e6e09b85dbf";

    private static final String BASE_URL = "http://api.openweathermap.org/";

    private static final String GET_WEATHER_METHOD_QUERY = "data/2.5/weather?";
    private static final String GET_ICON_METHOD_QUERY    = "img/w/";

    private static final String LAT_QUERY = "lat=";
    private static final String LON_QUERY = "&lon=";
    private static final String API_QUERY = "&appid=";

    private static final String METHOD_KEY_GET_WEATHER = "METHOD_KEY_GET_WEATHER";
    private static final String METHOD_KEY_GET_ICON    = "METHOD_KEY_GET_ICON";

    private static final String USER_INFO_METHOD_KEY_KEY   = "USER_INFO_METHOD_KEY_KEY";
    private static final String USER_INFO_LISTENER_KEY     = "USER_INFO_LISTENER_KEY";
    private static final String USER_INFO_WEATHER_ITEM_KEY = "USER_INFO_WEATHER_ITEM_KEY";

    public interface WeatherFetcherListener
    {
        void onWeatherFetchSucceeded(CurrentWeatherData data);

        void onWeatherFetchFailed(Throwable error);
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
    public static void fetchCurrentWeatherForLocation(Location location, WeatherFetcherListener listener) throws IllegalArgumentException {
        Log.d(TAG, Util.getMethodName());

        if (location == null) throw new IllegalArgumentException("Location can't be null");

        /* Create the url for this request. */
        String url = BASE_URL + GET_WEATHER_METHOD_QUERY +
                LAT_QUERY + location.getLatitude() +
                LON_QUERY + location.getLatitude() +
                API_QUERY + WEATHER_MAP_API_KEY;

        /* Create a HashMap to store my method key and the delegate. */
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put(USER_INFO_METHOD_KEY_KEY, METHOD_KEY_GET_WEATHER);
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
                case METHOD_KEY_GET_WEATHER:

                    /* Get our listener from the userInfo object. */
                    WeatherFetcherListener weatherFetcherListener = (WeatherFetcherListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    /* Decode the bytes into a json string, then deserialize into a WeatherData object. */
                    try {
                        String jsonString = new String(data, "UTF-8");

                        Log.d(TAG, "Weather data: " + jsonString);

                        Gson gson = new Gson();
                        CurrentWeatherData weatherData = gson.fromJson(jsonString, CurrentWeatherData.class);

                        /* Success. Call our listener. */
                        if (weatherFetcherListener != null)
                            weatherFetcherListener.onWeatherFetchSucceeded(weatherData);
                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                        /* Something went wrong. Call our listener. */
                        if (weatherFetcherListener != null)
                            weatherFetcherListener.onWeatherFetchFailed(e);
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

                /* In this case, call the listener's onWeatherFetchFailed method. */
                case METHOD_KEY_GET_WEATHER:

                    /* Get our listener from the userInfo object, and call it. */
                    WeatherFetcherListener weatherFetcherListener = (WeatherFetcherListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    if (weatherFetcherListener != null)
                        weatherFetcherListener.onWeatherFetchFailed(error);

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
