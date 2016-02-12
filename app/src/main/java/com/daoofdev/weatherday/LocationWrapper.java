package com.daoofdev.weatherday;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    LocationWrapper.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.Arrays;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/* Singleton class to encapsulate mLoc services to use anywhere in the application when needed. */
public class LocationWrapper
{
    private final static String TAG = "WeatherDay:LocationManager";

    private static LocationWrapper ourInstance = new LocationWrapper();

    private static Location loc;
    private static double   lat;
    private static double   lon;

    /* The minimum distance between location updates, in meters */
    private static final long MIN_DISTANCE_DELTA_BETWEEN_UPDATES = 10;

    /* The minimum time interval between location updates, in milliseconds */
    private static final long MIN_TIME_INTERVAL_BETWEEN_UPDATES = 1;

    private static LocationManager locationManager = (LocationManager)WeatherDayApplication.getContext()
                                                                                           .getSystemService(Context.LOCATION_SERVICE);

    private static LocationListener locationListener = new LocationListener()
    {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    private static String getPreferredProvider() {
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) return LocationManager.GPS_PROVIDER;
        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) return LocationManager.NETWORK_PROVIDER;

        return null;
    }

    public static void startGettingLocation() {
//        String preferredProvider = getPreferredProvider();
//        if (preferredProvider == null) /* Then neither provider is configured */
//            return; // TODO: Return some kind of error status or something
//
//        locationManager.requestLocationUpdates(preferredProvider,
//                                                MIN_TIME_INTERVAL_BETWEEN_UPDATES,
//                                                MIN_DISTANCE_DELTA_BETWEEN_UPDATES, locationListener);
    }

    public static void stopGettingLocation() {
//        locationManager.removeUpdates(locationListener);
    }

    /**
     * Returns a Location indicating the data from the last known
     * location fix obtained from the given provider.
     *
     * <p> This can be done
     * without starting the provider.  Note that this location could
     * be out-of-date, for example if the device was turned off and
     * moved to another location.
     *
     * <p> If the both providers are currently disabled, or permission isn't
     * granted, null is returned.
     *
     * @return the last known location for the preferred provider, or null
     *
     * @throws SecurityException if no suitable permission is present
     */
    public static Location getLastKnownLocation() {
        String preferredProvider = getPreferredProvider();
        if (preferredProvider == null) /* Then neither provider is configured */
            return null;

        if (ActivityCompat.checkSelfPermission(WeatherDayApplication.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(WeatherDayApplication.getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) !=
                            PackageManager.PERMISSION_GRANTED)
            return null;

        return locationManager.getLastKnownLocation(getPreferredProvider());
    }

    /**
     * Function to check if GPS or wifi are enabled
     *
     * @return boolean
     * */
    public static boolean canGetLocation() {
        return (getPreferredProvider() != null);
    }


}
