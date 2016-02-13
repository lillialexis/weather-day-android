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
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

@SuppressLint("LongLogTag")
public class WeatherMapWrapper
{
    private final static String TAG = "WeatherDay:WeatherMapInterface";

    private final static String WEATHER_MAP_API_KEY = "bd70ff217db31d2f80c85e6e09b85dbf";

    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/";

    private static final String METHOD_QUERY = "weather?";

    private static final String LAT_QUERY = "lat=";
    private static final String LON_QUERY = "&lon=";
    private static final String API_QUERY = "&appid=";

    private static final String METHOD_KEY_GET_WEATHER = "METHOD_KEY_GET_WEATHER";
    private static final String METHOD_KEY_GET_ICON    = "METHOD_KEY_GET_ICON";

    private static final String USER_INFO_METHOD_KEY_KEY = "USER_INFO_METHOD_KEY_KEY";
    private static final String USER_INFO_LISTENER_KEY   = "USER_INFO_LISTENER_KEY";

    public interface WeatherMapWrapperListener
    {
        void onWeatherDataReceived(CurrentWeatherData data);

        void onWeatherDataFailed(Throwable error);
    }

    public static Boolean canConnectToOpenWeather() {
        Log.d(TAG, Util.getMethodName());

        ConnectivityManager connectivityManager = (ConnectivityManager)WeatherDayApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        return (networkInfo != null && networkInfo.isConnected());
    }

    public static void getWeatherDataForLocation(Location location, WeatherMapWrapperListener listener) {
        Log.d(TAG, Util.getMethodName());

        /* Create the url for this request */
        String url = BASE_URL + METHOD_QUERY + LAT_QUERY + location.getLatitude() + LON_QUERY + location.getLatitude() + API_QUERY + WEATHER_MAP_API_KEY;

        /* Create a HashMap to store my method key and the delegate */
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put(USER_INFO_METHOD_KEY_KEY, METHOD_KEY_GET_WEATHER);
        userInfo.put(USER_INFO_LISTENER_KEY, listener);

        /* Create a new DataFetcher Object, passing in a HashMap containing the specific method and specific delegate, then invoke the URL */
        new DataFetcher(userInfo, dataFetcherListener).execute(url);
    }

    private static DataFetcher.DataFetcherListener dataFetcherListener = new DataFetcher.DataFetcherListener()
    {
        @Override
        public void onDidFetchData(Object userInfo, byte[] data) {
            String methodKey = (String)((HashMap)userInfo).get(USER_INFO_METHOD_KEY_KEY);

            switch (methodKey) {

                /* In this case, it's a json string representing the current weather data */
                case METHOD_KEY_GET_WEATHER:
                    /* Get our listener from the userInfo object */
                    WeatherMapWrapperListener listener = (WeatherMapWrapperListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    try {
                        String jsonString = new String(data, "UTF-8");

                        Log.d(TAG, "Weather data: " + jsonString);

                        Gson gson = new Gson();
                        CurrentWeatherData weatherData = gson.fromJson(jsonString, CurrentWeatherData.class);

                        if (listener != null)
                            listener.onWeatherDataReceived(weatherData);

                    }
                    catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                        /* Something went wrong. Call our listener. */
                        if (listener != null)
                            listener.onWeatherDataFailed(e);
                    }

                    break;

                /* In this case, it's an icon file for the weather data object. */
                case METHOD_KEY_GET_ICON:
                    break;
                default: break;
            }
        }

        @Override
        public void onRequestDidFail(Object userInfo, Throwable error) {
            String methodKey = (String)((HashMap)userInfo).get(USER_INFO_METHOD_KEY_KEY);

            switch (methodKey) {

                /* In this case, call the listener's onWeatherDataFailed method. */
                case METHOD_KEY_GET_WEATHER:
                    WeatherMapWrapperListener listener = (WeatherMapWrapperListener)((HashMap)userInfo).get(USER_INFO_LISTENER_KEY);

                    if (listener != null)
                        listener.onWeatherDataFailed(error);

                    break;

                case METHOD_KEY_GET_ICON:
                    break;

                default: break;
            }
        }
    };
}
