package com.daoofdev.weatherday;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    DataFetcher.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataFetcher extends AsyncTask<String, String, Throwable>
{
    public interface DataFetcherListener
    {
        void onDidFetchData(Object userInfo, byte[] data);

        void onRequestDidFail(Object userInfo, Throwable error);
    }

    private final static String TAG = "WeatherDay:DataFetcher";

    DataFetcherListener   mListener;
    Object                mUserInfo;

    byte[] mData;


    DataFetcher(Object userInfo, DataFetcherListener listener) {
        mUserInfo = userInfo;
        mListener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Throwable doInBackground(String... params) {

        String urlStr = params[0];

        Log.d(TAG, "Fetching data from URL: " + urlStr);

        try {
            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setDoInput(true);

            httpURLConnection.connect();

            int response = httpURLConnection.getResponseCode();

            // TODO: Check the response code for errors

            InputStream inputStream = httpURLConnection.getInputStream();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(1024);
            byte[] buffer = new byte[1024];

            int bytesRead;

            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
            }

            mData = byteArrayOutputStream.toByteArray();

            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
            inputStream.close();

        } catch (Exception e) {
            return e;
        }

        return null;
    }

//        @Override
//        protected void onProgressUpdate(String... params) {
//            Log.d(TAG, Util.getMethodName());
//
//            super.onProgressUpdate(params);
//
//            String newData = params[0];
//
//            jsonString += newData;
//        }

    protected void onPostExecute(Throwable result) {
        Log.d(TAG, Util.getMethodName());

        if (result != null) {
            if (mListener != null) mListener.onRequestDidFail(mUserInfo, result);
        } else {
            if (mListener != null) mListener.onDidFetchData(mUserInfo, mData);
        }
    }
}

