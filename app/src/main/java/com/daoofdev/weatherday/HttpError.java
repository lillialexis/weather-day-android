package com.daoofdev.weatherday;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    DataFetcherTest.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

public class HttpError extends Throwable {
    private final static String TAG = "WeatherDay:HttpError";

    private Integer mHttpErrorCode;

    public HttpError(int mHttpErrorCode) {
        this.mHttpErrorCode = mHttpErrorCode;
    }

    public HttpError(Integer httpErrorCode, Throwable cause) {
        super(cause);

        this.mHttpErrorCode = httpErrorCode;
    }

    public HttpError(String detailMessage, Integer httpErrorCode) {
        super(detailMessage);

        this.mHttpErrorCode = httpErrorCode;
    }

    public HttpError(String detailMessage, Integer httpErrorCode, Throwable cause) {
        super(detailMessage, cause);

        this.mHttpErrorCode = httpErrorCode;
    }

    public Integer getHttpErrorCode() {
        return mHttpErrorCode;
    }

    public void setHttpErrorCode(Integer mHttpErrorCode) {
        this.mHttpErrorCode = mHttpErrorCode;
    }
}
