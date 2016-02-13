package com.daoofdev.weatherday.WeatherData;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    Constants.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/12/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


public class Constants {
    private final static String TAG = "WeatherDay:Constants";

//    public static String tus(TemperatureUnits units) {
//        switch (units) {
//            case KELVIN: return "K";
//            case CELSIUS: return "C";
//            case FAHRENHEIT: return "F";
//        }
//        return "";
//    }
//
//    public static String sus(SpeedUnits units) {
//        switch (units) {
//            case METERS_PER_SEC: return "MPS";
//            case MILES_PER_HOUR: return "MPH";
//        }
//        return "";
//    }
//
//    public static String dus(DepthUnits units) {
//        switch (units) {
//            case CM: return "CM";
//            case IN: return "IN";
//        }
//        return "";
//    }

    public enum DepthUnits
    {
        CM("CM"),
        IN("IN");

        private final String mIdentifier;

        DepthUnits(String identifier) {
            mIdentifier = identifier;
        }

        public final String toString() {
            return mIdentifier;
        }
    }

    public enum TemperatureUnits
    {
        KELVIN("K"),
        CELSIUS("C"),
        FAHRENHEIT("F");

        private final String mIdentifier;

        TemperatureUnits(String identifier) {
            mIdentifier = identifier;
        }

        public final String toString() {
            return mIdentifier;
        }
    }

    public enum SpeedUnits
    {
        METERS_PER_SEC("MPS"),
        MILES_PER_HOUR("MPS");

        private final String mIdentifier;

        SpeedUnits(String identifier) {
            mIdentifier = identifier;
        }

        public final String toString() {
            return mIdentifier;
        }
    }

    /* No need to make an API call w the units. I can easily convert in-place. */
    static Double convertDepth(Double rawCm, Constants.DepthUnits units) {
        if (units == DepthUnits.IN) return rawCm * 0.394;

        return rawCm;
    }

    static Double convertedTemp(Double rawKelvin, Constants.TemperatureUnits units) {
        switch (units) {
            case KELVIN:
                return rawKelvin;
            case CELSIUS:
                return rawKelvin - 273.0;
            case FAHRENHEIT:
                return (((rawKelvin - 273.0) * 9) / 5) + 32;
        }

        return rawKelvin;
    }

    static Double convertedSpeed(Double rawMPS, Constants.SpeedUnits units) {
        if (units == SpeedUnits.MILES_PER_HOUR) return rawMPS * 2.237;

        return rawMPS;
    }

    static String prettyTime(Long time) {

        return "TODO";
    }

    static String prettyDirection(Double directionDegrees) {
        return "TODO";
    }
}
