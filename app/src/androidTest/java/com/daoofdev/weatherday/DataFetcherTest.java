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

import android.annotation.SuppressLint;
import android.location.Location;
import android.test.AndroidTestCase;
import android.util.Log;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Semaphore;

@SuppressLint("LongLogTag")
public class DataFetcherTest extends AndroidTestCase
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

    // TODO - Use this site for testing: https://httpbin.org
    public final void testFetchingText() {
        assertTrue("Implement test case", false);
    }

    public final void testFetchingImages() {
        assertTrue("Implement test case", false);
    }

    public final void testFetchingSomeOtherDataTypes() {
        assertTrue("Implement test case", false);
    }

    public final void testNullParams() {
        DataFetcher dataFetcher = new DataFetcher(null, null);
    }

    public final void testVariousInvalidUrls() throws InterruptedException {
        ArrayList<Object> funnyUrls = new ArrayList<Object>(
            Arrays.asList("12345", "", null, "http://daoofdev.com/no_real"));

        final Semaphore semaphore = new Semaphore(0);
        DataFetcher.DataFetcherListener listener = new DataFetcher.DataFetcherListener() {
            @Override
            public void onDidFetchData(Object userInfo, byte[] data) {
                assertTrue("Probably shouldn't be here", false);
                semaphore.release();
            }

            @Override
            public void onRequestDidFail(Object userInfo, Throwable error) {
                assertNotNull("Null error... uh oh...", error);
                semaphore.release();
            }
        };

        for (Object url : funnyUrls) {
            new DataFetcher(null, listener).execute((String)url);
            semaphore.acquire();
        }
    }

    /* Test the various pre-defined HTTP codes found in HttpURLConnection, by going to a server that
       serves up those codes. */
    public final void testReceivingHttpURLConnectionHtmlCodes() throws InterruptedException {
        ArrayList<Integer> codes = new ArrayList<Integer>(
            Arrays.asList(HttpURLConnection.HTTP_ACCEPTED, HttpURLConnection.HTTP_BAD_GATEWAY, HttpURLConnection.HTTP_BAD_METHOD,
                    HttpURLConnection.HTTP_BAD_REQUEST, HttpURLConnection.HTTP_CLIENT_TIMEOUT, HttpURLConnection.HTTP_CONFLICT,
                    HttpURLConnection.HTTP_CREATED, HttpURLConnection.HTTP_ENTITY_TOO_LARGE, HttpURLConnection.HTTP_FORBIDDEN,
                    HttpURLConnection.HTTP_GATEWAY_TIMEOUT, HttpURLConnection.HTTP_GONE, HttpURLConnection.HTTP_INTERNAL_ERROR,
                    HttpURLConnection.HTTP_LENGTH_REQUIRED, HttpURLConnection.HTTP_MOVED_PERM, HttpURLConnection.HTTP_MOVED_TEMP,
                    HttpURLConnection.HTTP_MULT_CHOICE, HttpURLConnection.HTTP_NOT_ACCEPTABLE, HttpURLConnection.HTTP_NOT_AUTHORITATIVE,
                    HttpURLConnection.HTTP_NOT_FOUND, HttpURLConnection.HTTP_NOT_IMPLEMENTED, HttpURLConnection.HTTP_NOT_MODIFIED,
                    HttpURLConnection.HTTP_NO_CONTENT, HttpURLConnection.HTTP_OK, HttpURLConnection.HTTP_PARTIAL,
                    HttpURLConnection.HTTP_PAYMENT_REQUIRED, HttpURLConnection.HTTP_PRECON_FAILED, HttpURLConnection.HTTP_PROXY_AUTH,
                    HttpURLConnection.HTTP_REQ_TOO_LONG, HttpURLConnection.HTTP_RESET, HttpURLConnection.HTTP_SEE_OTHER,
                    HttpURLConnection.HTTP_UNAUTHORIZED, HttpURLConnection.HTTP_UNAVAILABLE, HttpURLConnection.HTTP_UNSUPPORTED_TYPE,
                    HttpURLConnection.HTTP_USE_PROXY, HttpURLConnection.HTTP_VERSION));

        final Semaphore semaphore = new Semaphore(0);
        String testBaseUrl = "http://httpstat.us/";

        DataFetcher.DataFetcherListener listener = new DataFetcher.DataFetcherListener() {
            @Override
            public void onDidFetchData(Object userInfo, byte[] data) {
                Integer expectedCode = (Integer)userInfo;
                assertTrue("Got success when expecting an error.", expectedCode < 400);
                semaphore.release();
            }

            @Override
            public void onRequestDidFail(Object userInfo, Throwable error) {
                Integer expectedCode = (Integer)userInfo;

                if (error.getClass() == HttpError.class)
                    assertEquals("Error code wasn't what we expected.", expectedCode, ((HttpError)error).getHttpErrorCode());
                else
                    Log.e(TAG, "Got an error other than an HttpError", error);

                semaphore.release();
            }
        };

        for (Integer code : codes) {
            new DataFetcher(code, listener).execute(testBaseUrl + code.toString());
            semaphore.acquire();
        }
    }

    /* Test some other HTTP codes found in HttpURLConnection, by going to a server that
       serves up those codes. */
    public final void testReceivingNonHttpURLConnectionHtmlCodes() throws InterruptedException {
        ArrayList<Integer> codes = new ArrayList<Integer>(
            Arrays.asList(418, 524));

        final Semaphore semaphore = new Semaphore(0);
        String testBaseUrl = "http://httpstat.us/";

        DataFetcher.DataFetcherListener listener = new DataFetcher.DataFetcherListener() {
            @Override
            public void onDidFetchData(Object userInfo, byte[] data) {
                Integer expectedCode = (Integer)userInfo;
                assertTrue("Got success when expecting an error.", expectedCode < 400);
                semaphore.release();
            }

            @Override
            public void onRequestDidFail(Object userInfo, Throwable error) {
                Integer expectedCode = (Integer)userInfo;

                if (error.getClass() == HttpError.class)
                    assertEquals("Error code wasn't what we expected.", expectedCode, ((HttpError)error).getHttpErrorCode());
                else
                    Log.e(TAG, "Got an error other than an HttpError", error);

                semaphore.release();
            }
        };

        for (Integer code : codes) {
            new DataFetcher(code, listener).execute(testBaseUrl + code.toString());
            semaphore.acquire();
        }
    }

    public final void testVariousSizedData() throws InterruptedException {
        ArrayList<Integer> sizes = new ArrayList<Integer>(
            Arrays.asList(256, 1024, 2048, 10000));

        final Semaphore semaphore = new Semaphore(0);
        String testBaseUrl = "https://httpbin.org/bytes/";

        DataFetcher.DataFetcherListener listener = new DataFetcher.DataFetcherListener() {
            @Override
            public void onDidFetchData(Object userInfo, byte[] data) {
                Integer expectedSize = (Integer)userInfo;
                assertEquals("Got different sized data than expecting", expectedSize.intValue(), data.length);
                semaphore.release();
            }

            @Override
            public void onRequestDidFail(Object userInfo, Throwable error) {
                assertFalse("Got an error when not expecting one.", true);
                semaphore.release();
            }
        };

        for (Integer size : sizes) {
            new DataFetcher(size, listener).execute(testBaseUrl + size.toString());
            semaphore.acquire();
        }
    }

    public final void testVariousOpenWeatherMapAPICalls() {
        assertTrue("Implement test case", false);
    }

    public final void testSlowNetworkConditions() { // TODO: Probably don't need separate test cases for this... just need to test on device under various conditions
        assertTrue("Implement test case", false);
    }

    public final void testChangingNetworkConditions() {
        assertTrue("Implement test case", false);
    }

    public final void testUnavailableNetworkThings() {
        assertTrue("Implement test case", false);
    }
}
