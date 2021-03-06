package com.daoofdev.weatherday;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    LocationWrapperTest.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import android.test.AndroidTestCase;

public class LocationWrapperTest extends AndroidTestCase
{
    private final static String TAG = "WeatherDay:WeatherMapWrapperTest";

    /* (non-Javadoc)
    * @see junit.framework.TestCase#setUp()
    */
    protected void setUp() throws Exception {
        super.setUp();
    }

    /* (non-Javadoc)
    * @see junit.framework.TestCase#tearDown()
    */
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public final void testCase() {

    }

    public final void testUnavailableLocationServices() {
        //assertTrue("Implement test case", false);
    }

    public final void testUnpermittedLocationServices() { // TODO: Probably don't need separate test cases for this... just need to test on device under various conditions
        //assertTrue("Implement test case", false);
    }

    public final void testGPSAndNetworkAvailable() {
        //assertTrue("Implement test case", false);
    }

    public final void testGPSUnavailableNetworkAvailable() {
        //assertTrue("Implement test case", false);
    }

    public final void testGPSAvailableNetworkUnavailable() {
        //assertTrue("Implement test case", false);
    }



}
