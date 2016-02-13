package com.daoofdev.weatherday;
/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 *
 * Copyright (c) 2016 Dao of Development, LLC.
 *
 * This program is licensed under the terms and conditions of the
 * Mozilla Public License, version 2.0. The full text of the
 * Mozilla Public License is at https://www.mozilla.org/MPL/2.0/
 *
 * File:    ForecastWeatherDataTest.java
 * Project: WeatherDay
 *
 * Created by Lilli Szafranski on 2/11/16.
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

import android.test.AndroidTestCase;

import com.daoofdev.weatherday.WeatherData.CurrentWeatherData;
import com.daoofdev.weatherday.WeatherData.WeatherItem;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ForecastWeatherDataTest extends AndroidTestCase
{
    private final static String TAG = "WeatherDay:WeatherMapWrapperTest";

    Gson gson = new Gson();

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

    public final void testGoodJson() {
        // TODO: Write tests for forecast data objects
//        String goodJson = "{\"coord\":{\"lon\":-122.09,\"lat\":37.39},\n" +
//                "\"sys\":{\"type\":3,\"id\":168940,\"message\":0.0297,\"country\":\"US\",\"sunrise\":1427723751,\"sunset\":1427768967},\n" +
//                "\"weather\":[{\"id\":800,\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01n\"}],\n" +
//                "\"base\":\"stations\",\n" +
//                "\"main\":{\"temp\":285.68,\"humidity\":74,\"pressure\":1016.8,\"temp_min\":284.82,\"temp_max\":286.48,\"sea_level\":1034.11,\"grnd_level\": 1024.65},\n" +
//                "\"wind\":{\"speed\":0.96,\"deg\":285.001},\n" +
//                "\"clouds\":{\"all\":0},\n" +
//                "\"dt\":1427700245,\n" +
//                "\"id\":5,\n" +
//                "\"name\":\"Mountain View\",\n" +
//                "\"cod\":200}";
//
//        CurrentWeatherData data = gson.fromJson(goodJson, CurrentWeatherData.class);
//        WeatherItem item = data.getWeatherItems().get(0);
//
//        assertNotNull(data);
//        assertNotNull(item);
//
//        assertNotNull(data.getClouds());
//        assertNotNull(data.getCoordinate());
//        assertNotNull(data.getSystem());
//        assertNotNull(data.getWind());
//        assertNotNull(data.getMainData());
//        assertNotNull(data.getCod());
//        assertNotNull(data.getDt());
//        assertNotNull(data.getId());
//        assertNotNull(data.getName());
//        assertNotNull(data.getWeatherItems());
//
//        assertEquals(data.getCoordinate().getLon(), -122.09);
//        assertEquals(data.getCoordinate().getLat(), 37.39);
//
//        assertEquals(data.getSystem().getMessage(), 0.0297);
//        assertEquals(data.getSystem().getCountry(), "US");
//        assertEquals(data.getSystem().getSunrise(), Long.valueOf(1427723751));
//        assertEquals(data.getSystem().getSunset(), Long.valueOf(1427768967));
//
//        assertEquals(item.getId(), Integer.valueOf(800));
//        assertEquals(item.getMain(), "Clear");
//        assertEquals(item.getDescription(), "Sky is Clear");
//        assertEquals(item.getIconIdStr(), "01n");
//
//        assertEquals(data.getMainData().getTempRaw(), 285.68);
//        assertEquals(data.getMainData().getHumidity(), Integer.valueOf(74));
//        assertEquals(data.getMainData().getPressure(), 1016.8);
//        assertEquals(data.getMainData().getTempMinRaw(), 284.82);
//        assertEquals(data.getMainData().getTempMaxRaw(), 286.48);
//        assertEquals(data.getMainData().getSeaLevel(), 1034.11);
//        assertEquals(data.getMainData().getGroundLevel(), 1024.65);
//
//
//        assertEquals(data.getWind().getSpeed(), 0.96);
//        assertEquals(data.getWind().getDeg(), 285.001);
//
//        assertEquals(data.getClouds().getAll(), Integer.valueOf(0));
//
//        assertEquals(data.getDt(), Long.valueOf(1427700245));
//        assertEquals(data.getId(), Integer.valueOf(5));
//        assertEquals(data.getName(), "Mountain View");
//        assertEquals(data.getCod(), Integer.valueOf(200));
    }

    public final void testPartialJson() {
//        String incompleteJson = "{\"coord\":{\"lon\":-122.09,\"lat\":37.39},\n" +
//                "\"sys\":{\"type\":3,\"id\":168940,\"message\":0.0297,\"country\":\"US\",\"sunrise\":1427723751,\"sunset\":1427768967},\n" +
//                "\"wind\":{\"speed\":0.96,\"deg\":285.001},\n" +
//                "\"clouds\":{\"all\":0},\n" +
//                "\"id\":5,\n" +
//                "\"name\":\"Mountain View\",\n" +
//                "\"cod\":200}";
//
//        CurrentWeatherData data = gson.fromJson(incompleteJson, CurrentWeatherData.class);
//
//        assertNotNull(data);
//
//        assertNotNull(data.getClouds());
//        assertNotNull(data.getCoordinate());
//        assertNotNull(data.getSystem());
//        assertNotNull(data.getWind());
//        assertNotNull(data.getCod());
//        assertNotNull(data.getId());
//        assertNotNull(data.getName());
//        assertEquals(data.getDt(), Long.valueOf(0));
//        assertNull(data.getMainData());
//        assertNull(data.getWeatherItems());
    }

    public final void testInvalidJson() {
//        String invalidJson = "{\"coord\":{\"lon\"" +
//                "\"name\":\"Mountain View\",\n" +
//                "\"cod\":200}";
//
//        CurrentWeatherData data = null;
//        Boolean caughtException = false;
//        try {
//            data = gson.fromJson(invalidJson, CurrentWeatherData.class);
//        } catch (JsonSyntaxException e) {
//            caughtException = true;
//        }
//
//        assertNull(data);
//        assertTrue(caughtException);
    }

    public final void testValidButUnrepresentativeJson() {
//        String otherJson = "{\"menu\": {\n" +
//                "  \"value\": \"File\",\n" +
//                "  \"popup\": {\n" +
//                "    \"menuitem\": [\n" +
//                "      {\"value\": \"New\", \"onclick\": \"CreateNewDoc()\"},\n" +
//                "      {\"value\": \"Open\", \"onclick\": \"OpenDoc()\"},\n" +
//                "      {\"value\": \"Close\", \"onclick\": \"CloseDoc()\"}\n" +
//                "    ]\n" +
//                "  }\n" +
//                "}}";
//
//        CurrentWeatherData data = gson.fromJson(otherJson, CurrentWeatherData.class);
//
//        assertNotNull(data);
//
//        assertNull(data.getClouds());
//        assertNull(data.getCoordinate());
//        assertNull(data.getSystem());
//        assertNull(data.getWind());
//        assertNull(data.getWeatherItems());
//        assertNull(data.getName());
//        assertNull(data.getMainData());
//        assertEquals(data.getDt(), Long.valueOf(0));
//        assertEquals(data.getCod(), Integer.valueOf(0));
//        assertEquals(data.getId(), Integer.valueOf(0));
    }

    public final void testJsonWithWrongDataTypes() {
//        String goodJson = "{\"coord\":{\"lon\":-122,\"lat\":37},\n" +
//                "\"sys\":{\"type\":3.0,\"id\":168940.0,\"message\":0,\"country\":3,\"sunrise\":142,\"sunset\":142},\n" +
//                "\"weather\":[{\"id\":\"800\",\"main\":\"Clear\",\"description\":\"Sky is Clear\",\"icon\":\"01n\"}],\n" +
//                "\"base\":\"stations\",\n" +
//                "\"main\":{\"temp\":285.68,\"humidity\":74,\"pressure\":1016.8,\"temp_min\":284.82,\"temp_max\":286.48,\"sea_level\":1034.11,\"grnd_level\": 1024.65},\n" +
//                "\"wind\":{\"speed\":0.96,\"deg\":285.001},\n" +
//                "\"clouds\":{\"all\":0},\n" +
//                "\"dt\":1427700245,\n" +
//                "\"id\":5,\n" +
//                "\"name\":\"Mountain View\",\n" +
//                "\"cod\":200}";
//
//        CurrentWeatherData data = gson.fromJson(goodJson, CurrentWeatherData.class);
//        WeatherItem item = data.getWeatherItems().get(0);
//
//        assertNotNull(data);
//        assertNotNull(item);
//
//        assertNotNull(data.getClouds());
//        assertNotNull(data.getCoordinate());
//        assertNotNull(data.getSystem());
//        assertNotNull(data.getWind());
//        assertNotNull(data.getMainData());
//        assertNotNull(data.getCod());
//        assertNotNull(data.getDt());
//        assertNotNull(data.getId());
//        assertNotNull(data.getName());
//        assertNotNull(data.getWeatherItems());
//
//        assertEquals(data.getCoordinate().getLon(), (double) -122);
//        assertEquals(data.getCoordinate().getLat(), (double) 37);
//
//        assertEquals(data.getSystem().getMessage(), (double) 0);
//        assertEquals(data.getSystem().getCountry(), "3");
//        assertEquals(data.getSystem().getSunrise(), Long.valueOf(142));
//        assertEquals(data.getSystem().getSunset(), Long.valueOf(142));
//
//        assertEquals(item.getId(), Integer.valueOf(800));

    }

    public final void testInitializationWithObjectOtherThanJsonStrings() {
//        assertTrue("Implement test case", false);
    }

    public final void testTemperatureConversion() {
//        assertTrue("Implement test case", false);
    }

}
