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
import java.net.URLConnection;
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

            if (response >= 400) /* Error codes start at 400. */
                return processErrorCode(response);

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

    protected void onPostExecute(Throwable result) {
        Log.d(TAG, Util.getMethodName());

        if (result != null) {
            if (mListener != null) mListener.onRequestDidFail(mUserInfo, result);
        } else {
            if (mListener != null) mListener.onDidFetchData(mUserInfo, mData);
        }
    }

    private Throwable processErrorCode(int errorCode) {
        String message = "An unknown HTTP error has occurred: " + Integer.toString(errorCode);

        switch (errorCode) {
            case HttpURLConnection.HTTP_BAD_REQUEST	     :  message = "An HTTP error has occurred: 400 - Bad Request";                   break;
            case HttpURLConnection.HTTP_UNAUTHORIZED	 :  message = "An HTTP error has occurred: 401 - Unauthorized";                  break;
            case HttpURLConnection.HTTP_PAYMENT_REQUIRED :  message = "An HTTP error has occurred: 402 - Payment required";              break;
            case HttpURLConnection.HTTP_FORBIDDEN	     :  message = "An HTTP error has occurred: 403 - Forbidden";                     break;
            case HttpURLConnection.HTTP_NOT_FOUND	     :  message = "An HTTP error has occurred: 404 - Not found";                     break;
            case HttpURLConnection.HTTP_BAD_METHOD	     :  message = "An HTTP error has occurred: 405 - Bad Method";                    break;
            case HttpURLConnection.HTTP_NOT_ACCEPTABLE   :  message = "An HTTP error has occurred: 406 - Not acceptable";                break;
            case HttpURLConnection.HTTP_PROXY_AUTH	     :  message = "An HTTP error has occurred: 407 - Proxy authentication required"; break;
            case HttpURLConnection.HTTP_CLIENT_TIMEOUT   :  message = "An HTTP error has occurred: 408 - Client Timeout";                break;
            case HttpURLConnection.HTTP_CONFLICT	     :  message = "An HTTP error has occurred: 409 - Conflict";                      break;
            case HttpURLConnection.HTTP_GONE	         :  message = "An HTTP error has occurred: 410 - Gone";                          break;
            case HttpURLConnection.HTTP_LENGTH_REQUIRED  :  message = "An HTTP error has occurred: 411 - Length required";               break;
            case HttpURLConnection.HTTP_PRECON_FAILED	 :  message = "An HTTP error has occurred: 412 - Precondition failed";           break;
            case HttpURLConnection.HTTP_ENTITY_TOO_LARGE :  message = "An HTTP error has occurred: 413 - Entity too large";              break;
            case HttpURLConnection.HTTP_REQ_TOO_LONG	 :  message = "An HTTP error has occurred: 414 - Request too long";              break;
            case HttpURLConnection.HTTP_UNSUPPORTED_TYPE :  message = "An HTTP error has occurred: 415 - Unsupported type";              break;
            case HttpURLConnection.HTTP_INTERNAL_ERROR   :  message = "An HTTP error has occurred: 500 - Internal error";                break;
            case HttpURLConnection.HTTP_NOT_IMPLEMENTED  :  message = "An HTTP error has occurred: 501 - Not implemented";               break;
            case HttpURLConnection.HTTP_BAD_GATEWAY	     :  message = "An HTTP error has occurred: 502 - Bad Gateway";                   break;
            case HttpURLConnection.HTTP_UNAVAILABLE	     :  message = "An HTTP error has occurred: 503 - Unavailable";                   break;
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT  :  message = "An HTTP error has occurred: 504 - Gateway timeout";               break;
            case HttpURLConnection.HTTP_VERSION	         :  message = "An HTTP error has occurred: 505 - Version not supported";         break;
        }

        return new HttpError(message, errorCode);
    }
}

