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
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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

        String url = BASE_URL + METHOD_QUERY + LAT_QUERY + location.getLatitude() + LON_QUERY + location.getLatitude() + API_QUERY + WEATHER_MAP_API_KEY;
        new WeatherFetcher(listener).execute(url);
    }

    private static class WeatherFetcher extends AsyncTask<String, String, Throwable>
    {
        WeatherMapWrapperListener mListener;
        InputStream inputStream = null;

        String jsonString = "";

        WeatherFetcher(WeatherMapWrapperListener listener) {
            mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Throwable doInBackground(String... params) {

            String urlStr = params[0];

            Log.d(TAG, "Fetching weather data: " + urlStr);

            try {
                URL url = new URL(urlStr);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setDoInput(true);

                httpURLConnection.connect();

                int response = httpURLConnection.getResponseCode();

                Log.d(TAG, "The response is: " + response);

                inputStream = httpURLConnection.getInputStream();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
                byte[] buffer = new byte[1024];

                int bytesRead;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, bytesRead);

                    publishProgress(byteArrayOutputStream.toString("UTF-8"));
                    byteArrayOutputStream.reset();
                }
            } catch (Exception e) {
                return e;
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... params) {
            Log.d(TAG, Util.getMethodName());

            super.onProgressUpdate(params);

            String newData = params[0];

            jsonString += newData;
        }

        protected void onPostExecute(Throwable result) {
            Log.d(TAG, Util.getMethodName());

            if (result != null) {
                if (mListener != null) mListener.onWeatherDataFailed(result);
            } else if (jsonString != null) {
                Log.d(TAG, "Weather data: " + jsonString);

                Gson gson = new Gson();
                CurrentWeatherData weatherData = gson.fromJson(jsonString, CurrentWeatherData.class);

                if (mListener != null) mListener.onWeatherDataReceived(weatherData);
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    if (mListener != null) mListener.onWeatherDataFailed(e);
                }
            }
        }
    }
}
