package com.daoofdev.weatherday.WeatherData2;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    FormatterTest.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import android.test.AndroidTestCase;

public class FormatterTest extends AndroidTestCase {
    private final static String TAG = "WeatherDay:FormatterTest";

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

    public final void testPrettyTime() {
        //assertTrue("Implement test case", false);

        // TODO: Test various formats and stuff
    }

    public final void testPrettyDirection() {

        assertEquals("N",   Formatter.prettyDirection(0.0));
        assertEquals("N",   Formatter.prettyDirection(360.0));
        assertEquals("N",   Formatter.prettyDirection(355.0));
        assertEquals("N",   Formatter.prettyDirection(348.75));
        assertEquals("NNW", Formatter.prettyDirection(348.74));
        assertEquals("N",   Formatter.prettyDirection(348.76));
        assertEquals("N",   Formatter.prettyDirection(-11.25));
        assertEquals("N",   Formatter.prettyDirection(-11.24));
        assertEquals("NNW", Formatter.prettyDirection(-11.26));
        assertEquals("N",   Formatter.prettyDirection(11.24));
        assertEquals("NNE", Formatter.prettyDirection(11.25));
        assertEquals("NNE", Formatter.prettyDirection(11.26));

        // TODO: Test all of these
//        assertEquals("NE",  Formatter.prettyDirection());
//        assertEquals("NEE", Formatter.prettyDirection());
//        assertEquals("E",   Formatter.prettyDirection());
//        assertEquals("ESE", Formatter.prettyDirection());
//        assertEquals("SE",, Formatter.prettyDirection());
//        assertEquals("SSE", Formatter.prettyDirection());
//        assertEquals("S",   Formatter.prettyDirection());
//        assertEquals("SSW", Formatter.prettyDirection());
//        assertEquals("SW",  Formatter.prettyDirection());
//        assertEquals("WSW", Formatter.prettyDirection());
//        assertEquals("W",   Formatter.prettyDirection());
//        assertEquals("WNW", Formatter.prettyDirection());
//        assertEquals("NW",  Formatter.prettyDirection());
//        assertEquals("NNW", Formatter.prettyDirection());


    }

}
