package com.daoofdev.weatherday;

/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    CurrentWeatherData.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CurrentWeatherData
{
    private final static String TAG = "HVACAndroid:CurrentWeatherData";

    @SerializedName("coord")
    private Coordinate mCoordinate = null;

    @SerializedName("weather")
    private ArrayList<WeatherItem> mWeatherItems = null;

    @SerializedName("wind")
    private Wind mWind     = null;

    @SerializedName("clouds")
    private Clouds mClouds = null;

    @SerializedName("dt")
    private Integer mDt    = 0;

    @SerializedName("sys")
    private System mSystem = null;

    @SerializedName("id")
    private Integer mId    = 0;

    @SerializedName("name")
    private String mName   = null;

    @SerializedName("cod")
    private Integer mCod   = 0;

    public class Coordinate
    {
        @SerializedName("lat")
        private Double mLat = 0.0;

        @SerializedName("lon")
        private Double mLon = 0.0;

        public Coordinate() {
        }

        public Double getLat() {
            return mLat;
        }

        public Double getLon() {
            return mLon;
        }
    }

    public class WeatherItem
    {
        @SerializedName("id")
        private Integer mId          = 0;

        @SerializedName("main")
        private String  mMain        = null;

        @SerializedName("description")
        private String  mDescription = null;

        @SerializedName("icon")
        private String  mIcon        = null;

        public WeatherItem() {
        }

        public String getDescription() {
            return mDescription;
        }

        public String getIcon() {
            return mIcon;
        }

        public Integer getId() {
            return mId;
        }

        public String getMain() {
            return mMain;
        }
    }

    public class MainData
    {

        @SerializedName("temp")
        private Double mTemp       = 0.0;

        @SerializedName("pressure")
        private Double mPressure   = 0.0;

        @SerializedName("humidity")
        private Double mHumidity   = 0.0;

        @SerializedName("temp_min")
        private Double mTempMin    = 0.0;

        @SerializedName("temp_max")
        private Double mTempMax    = 0.0;

        @SerializedName("sea_level")
        private Double mSeaLevel   = 0.0;

        @SerializedName("grnd_level")
        private Double mGrndLevel  = 0.0;

        public MainData() {
        }

        public Double getGrndLevel() {
            return mGrndLevel;
        }

        public Double getHumidity() {
            return mHumidity;
        }

        public Double getPressure() {
            return mPressure;
        }

        public Double getSeaLevel() {
            return mSeaLevel;
        }

        public Double getTemp() {
            return mTemp;
        }

        public Double getTempMax() {
            return mTempMax;
        }

        public Double getTempMin() {
            return mTempMin;
        }
    }

    public class Wind
    {
        @SerializedName("speed")
        private Double mSpeed = 0.0;

        @SerializedName("deg")
        private Double mDeg   = 0.0;

        public Wind() {
        }

        public Double getDeg() {
            return mDeg;
        }

        public Double getSpeed() {
            return mSpeed;
        }
    }

    public class Clouds
    {
        @SerializedName("all")
        private Double mAll = 0.0;

        public Clouds() {
        }

        public Double getAll() {
            return mAll;
        }
    }

    public class System
    {
        @SerializedName("message")
        private Double mMessage = 0.0;

        @SerializedName("country")
        private String mCountry = null;

        @SerializedName("sunrise")
        private Long mSunrise   = 0L;

        @SerializedName("sunset")
        private Long mSunset    = 0L;

        public System() {
        }

        public String getCountry() {
            return mCountry;
        }

        public Double getMessage() {
            return mMessage;
        }

        public Long getSunrise() {
            return mSunrise;
        }

        public Long getSunset() {
            return mSunset;
        }
    }

    public CurrentWeatherData() {
    }

    public Clouds getClouds() {
        return mClouds;
    }

    public Integer getCod() {
        return mCod;
    }

    public Coordinate getCoordinate() {
        return mCoordinate;
    }

    public Integer getDt() {
        return mDt;
    }

    public Integer getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public System getSystem() {
        return mSystem;
    }

    public ArrayList<WeatherItem> getWeatherItems() {
        return mWeatherItems;
    }

    public Wind getWind() {
        return mWind;
    }
}
