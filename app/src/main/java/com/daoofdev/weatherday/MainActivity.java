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

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //            if (ActivityCompat
    //                    .checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
    //                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
    //                // TODO: Consider calling
    //                //    ActivityCompat#requestPermissions
    //                // here to request the missing permissions, and then overriding
    //                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
    //                //                                          int[] grantResults)
    //                // to handle the case where the user grants the permission. See the documentation
    //                // for ActivityCompat#requestPermissions for more details.
    //                return TODO;
    //            }

//    /**
//     * Function to show settings alert dialog On pressing Settings button will
//     * lauch Settings Options
//     * */
//    public void showSettingsAlert() {
//        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
//
//        // Setting Dialog Title
//        alertDialog.setTitle("GPS is settings");
//
//        // Setting Dialog Message
//        alertDialog
//                .setMessage("GPS is not enabled. Do you want to go to settings menu?");
//
//        // On pressing Settings button
//        alertDialog.setPositiveButton("Settings",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        Intent intent = new Intent(
//                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        mContext.startActivity(intent);
//                    }
//                });
//
//        // on pressing cancel button
//        alertDialog.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//
//        // Showing Alert Message
//        alertDialog.show();
//    }

}
