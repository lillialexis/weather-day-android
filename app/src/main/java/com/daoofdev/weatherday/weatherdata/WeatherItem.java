package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development.
 *
 * All rights reserved.
 *
 * ${FILE_NAME}
 * WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import android.graphics.Bitmap;

import com.google.gson.annotations.SerializedName;

public class WeatherItem {
    private final static String TAG = "WeatherDay:WeatherItem";

    @SerializedName("id")
    private Integer mId = 0;

    @SerializedName("main")
    private String mMain = null;

    @SerializedName("description")
    private String mDescription = null;

    @SerializedName("icon")
    private String mIconIdStr = null;

    private Bitmap mIconImage = null;

    public WeatherItem() {
    }

    public String getDescription() {
        return mDescription;
    }

    public String getIconIdStr() {
        return mIconIdStr;
    }

    public Integer getId() {
        return mId;
    }

    public String getMain() {
        return mMain;
    }

    public Bitmap getIconImage() {
        return mIconImage;
    }

    public void setIconImage(Bitmap iconImage) {
        mIconImage = iconImage;
    }
}
